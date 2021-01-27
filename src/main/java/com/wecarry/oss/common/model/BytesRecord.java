package com.wecarry.oss.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 字节写入记录
 *
 * @author miaosc
 * @date 10/24/2019
 */
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class BytesRecord implements Serializable {

    /**
     * 此次写入的字节数
     */
    private long writeBytes;

    /**
     * 文件现在的总大小
     */
    private long currentSize;
}
