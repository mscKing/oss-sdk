package com.github.mscking.oss.rpc.model;

import java.io.Serializable;

/**
 * @author miaosc
 * @date 2020/9/26 0026
 */
public class FileOperationRequest implements Serializable{

    /**
     * 桶名称
     */
    protected String bucketName;

    /**
     * 文件id
     */
    protected String fileId;

    public FileOperationRequest(String bucketName, String fileId) {
        this.bucketName = bucketName;
        this.fileId = fileId;
    }

    public FileOperationRequest() {
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
