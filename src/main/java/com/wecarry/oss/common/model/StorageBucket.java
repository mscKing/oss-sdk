package com.wecarry.oss.common.model;

import com.wecarry.oss.common.constant.AccessControlEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * oss桶
 *
 * @author miaosc
 * @date 11/2/2019
 */
@Setter
@Getter
@ToString
public class StorageBucket implements Serializable {

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
}
