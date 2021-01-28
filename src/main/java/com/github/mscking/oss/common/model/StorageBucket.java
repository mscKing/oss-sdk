package com.github.mscking.oss.common.model;

import com.github.mscking.oss.common.constant.AccessControlEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * oss桶
 *
 * @author miaosc
 * @date 11/2/2019
 */
public class StorageBucket implements Serializable {

    private static final long serialVersionUID = 5869550542793961404L;
    /**
     * bucket名称
     */
    private String bucketName;

    /**
     * 描述
     */
    private String description;

    /**
     * 访问权限
     */
    private AccessControlEnum accessControl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccessControlEnum getAccessControl() {
        return accessControl;
    }

    public void setAccessControl(AccessControlEnum accessControl) {
        this.accessControl = accessControl;
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
