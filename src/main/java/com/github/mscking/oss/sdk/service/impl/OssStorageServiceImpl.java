package com.github.mscking.oss.sdk.service.impl;

import com.github.mscking.oss.common.constant.AccessControlEnum;
import com.github.mscking.oss.common.handler.DownloadHandler;
import com.github.mscking.oss.common.model.BytesRecord;
import com.github.mscking.oss.common.model.FileObjectInfo;
import com.github.mscking.oss.common.model.StorageBucket;
import com.github.mscking.oss.common.util.HmacUtil;
import com.github.mscking.oss.common.util.RandomUtil;
import com.github.mscking.oss.rpc.StorageClient;
import com.github.mscking.oss.rpc.constant.OssParamConstant;
import com.github.mscking.oss.rpc.model.FileOperationRequest;
import com.github.mscking.oss.rpc.model.FileReadRequest;
import com.github.mscking.oss.rpc.model.FileWriteRequest;
import com.github.mscking.oss.rpc.util.ParamSignUtil;
import com.github.mscking.oss.sdk.service.OssStorageService;
import jakarta.activation.DataHandler;
import org.apache.cxf.attachment.AttachmentDataSource;
import org.apache.cxf.attachment.ByteDataSource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

/**
 * 存储代理
 *
 * @author miaosc
 * @date 2020/10/4 0004
 */
public class OssStorageServiceImpl implements OssStorageService {

    private StorageClient proxy;

    private String host;

    private HmacUtil hmacUtil;

    private CloseableHttpClient httpClient;

    private Logger log = LoggerFactory.getLogger(getClass());

    public static final int BUF_SIZE = 2 * 1024 * 1024;

    @Override
    public List<StorageBucket> listAllBuckets() {
        return proxy.listAllBuckets();
    }

    @Override
    public boolean checkBuckExist(String bucketName) {
        return proxy.checkBuckExist(bucketName);
    }

    @Override
    public StorageBucket createStorageBucket(String bucketName, String description, AccessControlEnum accessControl) {
        return proxy.createStorageBucket(bucketName, description, accessControl);
    }

    @Override
    public StorageBucket setStorageBucketACL(String bucketName, AccessControlEnum accessControl) {
        return proxy.setStorageBucketACL(bucketName, accessControl);
    }

    @Override
    public void deleteBucket(String bucketName) {
        proxy.deleteBucket(bucketName);
    }

    @Override
    public void readFile(FileReadRequest fileReadRequest, DownloadHandler downloadHandler) throws IOException {
        Assert.isTrue(fileReadRequest != null, "参数不能为null");
        Assert.isTrue(fileReadRequest.getBucketName() != null, "bucketName不能为null");
        Assert.isTrue(fileReadRequest.getFileId() != null, "文件id不能为null");
        //封装访问地址和凭证
        HttpUriRequest httpUriRequest = buildAccessURLParams(fileReadRequest);
        try {
            //执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(httpUriRequest);
            StatusLine statusLine = httpResponse.getStatusLine();
            //成功
            if (statusLine.getStatusCode() == HttpStatus.SC_OK || statusLine.getStatusCode() == HttpStatus.SC_PARTIAL_CONTENT) {
                HttpEntity httpEntity = httpResponse.getEntity();
                try {
                    //执行回调
                    downloadHandler.read(httpEntity.getContent());
                    httpResponse.close();
                    return;
                } catch (Exception e) {
                    log.info("回调函数读取文件输入流出现异常", e);
                    throw e;
                }
            }
            throw new IOException("httpStatusCode=" + statusLine.getStatusCode());
        } finally {
            httpUriRequest.abort();
        }
    }

    @Override
    public FileObjectInfo createEmptyFile(String bucketName, String fileName) {
        return proxy.createEmptyFile(bucketName, fileName);
    }

    @Override
    public BytesRecord appendFile(FileWriteRequest fileWriteRequest, InputStream inputStream) throws IOException {
        //是否可以直接上传
        if (canUploadDirectly(inputStream.available())) {
            AttachmentDataSource attachmentDataSource = new AttachmentDataSource(ContentType.APPLICATION_OCTET_STREAM.getMimeType(), inputStream);
            return proxy.appendFile(fileWriteRequest, new DataHandler(attachmentDataSource));
        }
        //2M的buf容器
        byte[] buf = new byte[BUF_SIZE];
        //分片上传
        return uploadFileByChunk(inputStream, buf, fileWriteRequest);
    }

    @Override
    public FileObjectInfo writeFileDirectly(String bucketName, String fileName, InputStream inputStream) throws IOException {
        //是否可以直接上传
        if (canUploadDirectly(inputStream.available())) {
            return writeSmallFile(bucketName, fileName, inputStream);
        }
        //大文件处理
        FileObjectInfo fileObjectInfo = proxy.createEmptyFile(bucketName, fileName);
        FileWriteRequest fileWriteRequest = new FileWriteRequest();
        fileWriteRequest.setBucketName(fileObjectInfo.getBucketName());
        fileWriteRequest.setFileId(fileObjectInfo.getFileId());
        //2M的buf容器
        byte[] buf = new byte[BUF_SIZE];
        //分片上传
        return uploadFileByChunk(inputStream, buf, fileWriteRequest, fileObjectInfo);
    }


    @Override
    public FileObjectInfo findFileInfo(FileOperationRequest fileOperationRequest) {
        return proxy.findFileInfo(fileOperationRequest);
    }

    @Override
    public void deleteFile(FileOperationRequest fileOperationRequest) {
        proxy.deleteFile(fileOperationRequest);
    }

    @Override
    public void deleteFilesBatch(Collection<FileOperationRequest> fileOperationRequests) {
        proxy.deleteFilesBatch(fileOperationRequests);
    }

    @Override
    public String buildSignParam(String bucketName, String fileId) {
        StringBuilder stringBuilder = new StringBuilder(128);
        long timestamp = System.currentTimeMillis();
        String random = RandomUtil.generate8String();
        //构建签名
        String sign = hmacUtil.mac2Hex(ParamSignUtil.buildParamString(bucketName, fileId, random, timestamp).getBytes(StandardCharsets.UTF_8));
        stringBuilder.append("random=").append(random).append("&timestamp=").append(timestamp).append("&sign=").append(sign);
        return stringBuilder.toString();
    }

    @Override
    public String buildDownloadUrl(String bucketName, String fileId) {
        return createUrl(bucketName, fileId);
    }

    @Override
    public long countBucketFiles(String bucketName) {
        Assert.isTrue(bucketName != null, "bucketName不能为null");
        return proxy.countBucketFiles(bucketName);
    }


    public void setProxy(StorageClient proxy) {
        this.proxy = proxy;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


    public void setHmacUtil(HmacUtil hmacUtil) {
        this.hmacUtil = hmacUtil;
    }


    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 分隔输入流
     *
     * @param inputStream
     * @param buf
     * @return
     * @throws IOException
     */
    private int splitInputStream(InputStream inputStream, byte[] buf) throws IOException {
        return inputStream.read(buf, 0, buf.length);
    }

    /**
     * 是否可以直接上传
     *
     * @param len
     * @return
     */
    private boolean canUploadDirectly(int len) {
        //小于2M
        return len < BUF_SIZE;
    }

    /**
     * 写入2M以下的小文件
     *
     * @param bucketName
     * @param fileName
     * @param inputStream
     * @return
     * @throws IOException
     */
    private FileObjectInfo writeSmallFile(String bucketName, String fileName, InputStream inputStream) throws IOException {
        AttachmentDataSource attachmentDataSource = new AttachmentDataSource(ContentType.MULTIPART_FORM_DATA.getMimeType(), inputStream);
        return proxy.writeFile(bucketName, fileName, new DataHandler(attachmentDataSource));
    }

    /**
     * 分片上传文件
     *
     * @param inputStream
     * @param buf
     * @param fileWriteRequest
     * @return
     * @throws IOException
     */
    private BytesRecord uploadFileByChunk(InputStream inputStream, byte[] buf, FileWriteRequest fileWriteRequest) throws IOException {
        int len;
        BytesRecord totalBytesRecord = new BytesRecord();
        while ((len = splitInputStream(inputStream, buf)) != -1) {
            BytesRecord bytesRecord;
            if (len == buf.length) {
                ByteDataSource byteArrayDataSource = new ByteDataSource(buf, ContentType.APPLICATION_OCTET_STREAM.getMimeType());
                bytesRecord = proxy.appendFile(fileWriteRequest, new DataHandler(byteArrayDataSource));
            } else {
                ByteDataSource byteArrayDataSource = new ByteDataSource(buf, 0, len);
                byteArrayDataSource.setContentType(ContentType.APPLICATION_OCTET_STREAM.getMimeType());
                bytesRecord = proxy.appendFile(fileWriteRequest, new DataHandler(byteArrayDataSource));
            }
            //更新数据
            fileWriteRequest.setOffset(bytesRecord.getCurrentSize());
            sumBytesRecord(totalBytesRecord, bytesRecord);
            //数据未全部写入
            if (bytesRecord.getWriteBytes() != len) {
                throw new IOException(String.format("数据未全部写入,expect=%d,actual=%d", len, bytesRecord.getWriteBytes()));
            }
        }
        return totalBytesRecord;
    }

    /**
     * 分片上传文件
     *
     * @param inputStream
     * @param buf
     * @param fileWriteRequest
     * @param fileObjectInfo
     * @return
     * @throws IOException
     */
    private FileObjectInfo uploadFileByChunk(InputStream inputStream, byte[] buf, FileWriteRequest fileWriteRequest, FileObjectInfo fileObjectInfo) throws IOException {
        int len;
        while ((len = splitInputStream(inputStream, buf)) != -1) {
            BytesRecord bytesRecord;
            long start = System.currentTimeMillis();
            ByteDataSource byteArrayDataSource = new ByteDataSource(buf, 0, len);
            byteArrayDataSource.setContentType(ContentType.MULTIPART_FORM_DATA.getMimeType());
            bytesRecord = proxy.appendFile(fileWriteRequest, new DataHandler(byteArrayDataSource));
            log.info("上传一片耗时:{} ms", System.currentTimeMillis() - start);
            //更新数据
            fileWriteRequest.setOffset(bytesRecord.getCurrentSize());
            fileObjectInfo.setSize(bytesRecord.getCurrentSize());
            //数据未全部写入
            if (bytesRecord.getWriteBytes() != len) {
                throw new IOException(String.format("数据未全部写入,expect=%d,actual=%d", len, bytesRecord.getWriteBytes()));
            }
        }
        return fileObjectInfo;
    }

    private HttpUriRequest buildAccessURLParams(FileReadRequest fileReadRequest) {
        long offset = fileReadRequest.getOffset();
        long length = fileReadRequest.getLength();
        //封装uri
        HttpUriRequest httpUriRequest = new HttpGet(createUrl(fileReadRequest));
        //填写range
        String range = length == FileReadRequest.READ_TO_END ? String.format("%d-", offset) : String.format("%d-%d", offset, offset + length - 1);
        httpUriRequest.addHeader("Range", range);
        httpUriRequest.addHeader("accept", "application/octet-stream,*/*");
        return httpUriRequest;
    }

    private String createUrl(FileReadRequest fileReadRequest) {
        return createUrl(fileReadRequest.getBucketName(), fileReadRequest.getFileId());
    }

    private String createUrl(String bucketName, String fileId) {
        String uri = String.format(OssParamConstant.OSS_URI_FORMAT, bucketName, fileId);
        return String.format("%s%s", host, uri);
    }

    /**
     * 汇总写入进度
     *
     * @param base
     * @param another
     */
    private void sumBytesRecord(BytesRecord base, BytesRecord another) {
        base.setWriteBytes(base.getWriteBytes() + another.getWriteBytes());
        base.setCurrentSize(another.getCurrentSize());
    }
}
