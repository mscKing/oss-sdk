package com.wecarry.oss.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 简单的视图对象
 * @author miaosc
 * @date 2020/9/23 0023
 */
@Getter
@Setter
@ToString
public class SimpleVO implements Serializable{

    /**
     * 主键
     */
    protected Object objId;

    /**
     * 名称
     */
    protected String name;

    public SimpleVO(Object objId, String name) {
        this.objId = objId;
        this.name = name;
    }

    public SimpleVO() {
    }

}
