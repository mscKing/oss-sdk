package com.github.mscking.oss.common.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author miaosc
 * @date 11/2/2019
 */

public class FileObjectInfo implements Serializable {

    private static final long serialVersionUID = -8429981279298936626L;
    /**
     * 文件id
     */
    private String fileId;

    /**
     * 关联的appKey
     */
    private String appKey;

    /**
     * bucket名称
     */
    private String bucketName;

    /**
     * 重新生成的文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 大小(bytes)
     */
    private Long size;

    /**
     * 访问的url,如果文件所属桶不能公共读,则需要在url后面带上签名等参数
     */
    private String url;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
