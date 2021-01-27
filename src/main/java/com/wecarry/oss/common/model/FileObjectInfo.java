package com.wecarry.oss.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author miaosc
 * @date 11/2/2019
 */
@Getter
@Setter
@ToString
public class FileObjectInfo implements Serializable {

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
}
