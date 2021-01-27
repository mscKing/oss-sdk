package com.wecarry.oss.sdk.test;

import com.wecarry.oss.common.constant.AccessControlEnum;
import com.wecarry.oss.common.model.FileObjectInfo;
import com.wecarry.oss.common.util.TimePoint;
import com.wecarry.oss.rpc.exception.StorageCallException;
import com.wecarry.oss.rpc.model.FileReadRequest;
import com.wecarry.oss.sdk.service.OssStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.helpers.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author miaosc
 * @date 2020/10/2 0002
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SDKTest {

    @Autowired
    OssStorageService storageClient;

    @Test
    public void testCall() throws IOException, StorageCallException {
        // 数据准备
        String bucketName = "testBucketABD";
        // 调用代理接口的方法调用并返回结果
        boolean exist = storageClient.checkBuckExist(bucketName);
        log.info("是否存在bucket={},result: {}", bucketName, exist);
        // 调用代理接口的方法调用并返回结果
        storageClient.listAllBuckets();
        //创建桶
        if (!exist) {
            storageClient.createStorageBucket(bucketName, "测试bucket", AccessControlEnum.PUBLIC_READ);
        }
        storageClient.setStorageBucketACL(bucketName, AccessControlEnum.PRIVATE_READ_WRITE);
        File file = getFile();
        FileInputStream fileInputStream = new FileInputStream(file);
        //上传文件
        TimePoint timePoint = TimePoint.create();
        log.info("开始上传文件");
        FileObjectInfo fileObjectInfo = storageClient.writeFileDirectly(bucketName, file.getName(), fileInputStream);
        assert fileObjectInfo.getSize() == file.length();
        FileReadRequest fileReadRequest = new FileReadRequest();
        fileReadRequest.setBucketName(bucketName);
        fileReadRequest.setFileId(fileObjectInfo.getFileId());
        long period = timePoint.elapseMillis();
        log.info("上传耗时:{} ms", period);
        fileObjectInfo = storageClient.findFileInfo(fileReadRequest);
        assert fileObjectInfo.getSize() == file.length();
        fileInputStream.close();
        //下载文件
        File tempFile = File.createTempFile(file.getName(), "");
        //删除文件
        tempFile.deleteOnExit();
        final FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        fileReadRequest.setLength(fileObjectInfo.getSize());
        storageClient.readFile(fileReadRequest, ins -> IOUtils.copy(ins, fileOutputStream));
        period = timePoint.elapseMillis();
        log.info("下载耗时:{} ms", period);
        fileOutputStream.close();
        assert tempFile.length() == file.length();
        storageClient.deleteFile(fileReadRequest);
        long fileCount = storageClient.countBucketFiles(bucketName);
        if (fileCount == 0) {
            //删除桶
            storageClient.deleteBucket(bucketName);
            log.info("删除桶:{}", bucketName);
        }
        log.info("call rpc finish");
    }

    @Test
    public void partialDownload() throws IOException {
        //下载文件
        File tempFile = File.createTempFile("aaa", "mp4");
        String bucketName = "testBucketBCD";
        TimePoint timePoint = TimePoint.create();
        //删除文件
        tempFile.deleteOnExit();
        final FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        FileReadRequest fileReadRequest = new FileReadRequest();
        fileReadRequest.setOffset(4);
        fileReadRequest.setBucketName(bucketName);
        fileReadRequest.setFileId("00000175A185C12945183643");
        long length = 12_534_730;
        fileReadRequest.setLength(length);
        storageClient.readFile(fileReadRequest, ins -> IOUtils.copy(ins, fileOutputStream));
        log.info("下载耗时:{} ms", timePoint.elapseMillis());
        fileOutputStream.close();
        assert tempFile.length() == length;
    }

    /**
     * 拿文件
     *
     * @return
     */
    private File getFile() {
        String path = getClass().getClassLoader().getResource("").getPath();
        return new File(path + "test001.png");
//        return new File("D:\\MV\\成龙、金喜善 - 美丽的神话.mkv");
    }
}
