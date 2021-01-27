package com.wecarry.oss.common.model;

import java.io.Serializable;

/**
 * 简单的视图对象
 * @author miaosc
 * @date 2020/9/23 0023
 */
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

    public Object getObjId() {
        return objId;
    }

    public void setObjId(Object objId) {
        this.objId = objId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SimpleVO{" +
                "objId=" + objId +
                ", name='" + name + '\'' +
                '}';
    }
}
