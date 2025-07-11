package com.github.mscKing.oss.sdk.test;

import com.github.mscking.oss.common.constant.AccessControlEnum;
import com.github.mscking.oss.common.model.BytesRecord;
import com.github.mscking.oss.common.model.FileObjectInfo;
import com.github.mscking.oss.common.model.StorageBucket;
import com.github.mscking.oss.rpc.model.FileReadRequest;
import com.github.mscking.oss.rpc.model.FileWriteRequest;
import com.github.mscking.oss.sdk.service.OssStorageService;
import org.apache.cxf.helpers.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author miaosc
 * @date 2021/2/25 0025
 */
@SpringBootTest(classes = AppTest.class)
public class OssSDKTest {

    final String bucket = "test002-bucket";
    @Autowired
    OssStorageService ossStorageService;

    @Test
    public void testSdk() {
        List<StorageBucket> storageBuckets = ossStorageService.listAllBuckets();
        assert !storageBuckets.isEmpty();
    }

    @Test
    public void testUploadFile() throws IOException {
        String bucketName = bucket;
        File file = getFile();
        //创建文件
        FileObjectInfo emptyFile = ossStorageService.createEmptyFile(bucketName, file.getName());
        FileInputStream fileInputStream = new FileInputStream(file);
        //上传分片
        FileWriteRequest fileWriteRequest = new FileWriteRequest();
        fileWriteRequest.setFileId(emptyFile.getFileId());
        fileWriteRequest.setOffset(0);
        fileWriteRequest.setBucketName(bucketName);
        BytesRecord bytesRecord = ossStorageService.appendFile(fileWriteRequest, fileInputStream);
        assert bytesRecord.getCurrentSize() == file.length();
        String s = ossStorageService.buildDownloadUrl(bucketName, emptyFile.getFileId());
        System.out.println(s);
        //下载文件
        File tempFile = new File("testTemp."+emptyFile.getFileType());
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        FileReadRequest fileReadRequest = new FileReadRequest(bucket, emptyFile.getFileId());
        ossStorageService.readFile(fileReadRequest, inputStream -> IOUtils.copy(inputStream, fileOutputStream));
        fileOutputStream.close();
        assert tempFile.length() == file.length();
        //删除文件
        tempFile.delete();
        ossStorageService.deleteFile(fileReadRequest);
        ossStorageService.deleteFilesBatch(Collections.singleton(fileReadRequest));
        FileObjectInfo fileInfo = ossStorageService.findFileInfo(fileReadRequest);
        assert fileInfo==null;
    }

    @Test
    public void testUploadFileDirectly() throws IOException {
        String bucketName = bucket;
        File file = getFile();
        FileInputStream fileInputStream = new FileInputStream(file);
        //上传整个文件
        FileObjectInfo fileObjectInfo = ossStorageService.writeFileDirectly(bucketName, file.getName(), fileInputStream);
        assert fileObjectInfo.getSize() == file.length();
        String s = ossStorageService.buildDownloadUrl(bucketName, fileObjectInfo.getFileId());
        System.out.println(s);
        //下载文件
        File tempFile = new File("testTemp2."+fileObjectInfo.getFileType());
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        FileReadRequest fileReadRequest = new FileReadRequest(bucketName, fileObjectInfo.getFileId());
        ossStorageService.readFile(fileReadRequest, inputStream -> IOUtils.copy(inputStream, fileOutputStream));
        fileOutputStream.close();
        assert tempFile.length() == file.length();
        //删除文件
        tempFile.delete();
        ossStorageService.deleteFile(fileReadRequest);
        ossStorageService.deleteFilesBatch(Collections.singleton(fileReadRequest));
        FileObjectInfo fileInfo = ossStorageService.findFileInfo(fileReadRequest);
        assert fileInfo==null;
    }

    @Test
    public void testCreateBucket() {
        String bucketName = "newbucket01";
        boolean checkBuckExist = ossStorageService.checkBuckExist(bucketName);
        assert !checkBuckExist;
        StorageBucket storageBucket = ossStorageService.createStorageBucket(bucketName, "test", AccessControlEnum.PRIVATE_READ_WRITE);
        assert storageBucket != null;

        StorageBucket storageBucket1 = ossStorageService.setStorageBucketACL(bucketName, AccessControlEnum.PRIVATE_READ_WRITE);
        assert Objects.equals(storageBucket1.getAccessControl(),AccessControlEnum.PRIVATE_READ_WRITE);

        assert ossStorageService.countBucketFiles(bucketName) == 0;
        ossStorageService.deleteBucket(bucketName);
    }

    @Test
    public void testBuildUrl() {
        String bucketName = "test002-bucket";
        String fileId = "0000017C43E874F873729E0E";
        String url = ossStorageService.buildDownloadUrl(bucketName, fileId);
        System.out.println(url);
        String full = String.format("%s?%s",url,ossStorageService.buildSignParam(bucketName, fileId));
        System.out.println(full);
    }

    /**
     * 拿文件
     *
     * @return
     */
    private File getFile() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("test001.png");
        File file = classPathResource.getFile();
        System.out.println(file.getAbsolutePath());
        return file;
    }

}
