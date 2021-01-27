package com.wecarry.oss.rpc.model;

/**
 * 文件读取请求对象
 * @author miaosc
 * @date 2020/9/26 0026
 */
public class FileReadRequest extends FileOperationRequest {

    public static final long READ_TO_END = -1L;
    /**
     * 读取位置偏移量,默认0
     */
    private long offset;

    /**
     * 读取长度,默认为-1,表示读取到文件末尾
     */
    private long length = READ_TO_END;

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
