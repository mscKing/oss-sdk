package com.github.mscKing.oss.sdk.test;

import com.github.mscking.oss.common.model.BytesRecord;
import com.github.mscking.oss.common.model.FileObjectInfo;
import com.github.mscking.oss.common.model.StorageBucket;
import com.github.mscking.oss.rpc.model.FileWriteRequest;
import com.github.mscking.oss.sdk.service.OssStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author miaosc
 * @date 2021/2/25 0025
 */
@SpringBootTest(classes = AppTest.class)
public class OssSDKTest {

    @Autowired
    OssStorageService ossStorageService;

    @Test
    public void testSdk(){
        List<StorageBucket> storageBuckets = ossStorageService.listAllBuckets();
        assert !storageBuckets.isEmpty();
    }

    @Test
    public void testUploadFile() throws IOException {
        String bucketName="test002-bucket";
        File file = getFile();
        //创建文件
        FileObjectInfo emptyFile = ossStorageService.createEmptyFile(bucketName, file.getName());
        FileInputStream fileInputStream = new FileInputStream(file);
        //上传分片
        FileWriteRequest fileWriteRequest=new FileWriteRequest();
        fileWriteRequest.setFileId(emptyFile.getFileId());
        fileWriteRequest.setOffset(0);
        fileWriteRequest.setBucketName(bucketName);
        BytesRecord bytesRecord = ossStorageService.appendFile(fileWriteRequest, fileInputStream);
        assert bytesRecord.getCurrentSize() == file.length();
        String s = ossStorageService.buildDownloadUrl(bucketName, emptyFile.getFileId());
        System.out.println(s);
    }

    @Test
    public void testBuildUrl(){
        String bucketName="test002-bucket";
        String fileId="0000017C43E874F873729E0E";
        String s = ossStorageService.buildDownloadUrl(bucketName, fileId);
        System.out.println(s);
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
