package com.github.mscking.oss.common.model;

import java.io.Serializable;

/**
 * 字节写入记录
 *
 * @author miaosc
 * @date 10/24/2019
 */
public class BytesRecord implements Serializable {

    private static final long serialVersionUID = 7476103778606584362L;
    /**
     * 此次写入的字节数
     */
    private long writeBytes;

    /**
     * 文件现在的总大小
     */
    private long currentSize;

    public long getWriteBytes() {
        return writeBytes;
    }

    public BytesRecord setWriteBytes(long writeBytes) {
        this.writeBytes = writeBytes;
        return this;
    }

    public long getCurrentSize() {
        return currentSize;
    }

    public BytesRecord setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
        return this;
    }

    @Override
    public String toString() {
        return "BytesRecord{" +
                "writeBytes=" + writeBytes +
                ", currentSize=" + currentSize +
                '}';
    }
}
