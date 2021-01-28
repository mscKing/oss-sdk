package com.github.mscking.oss.rpc.model;

/**
 * 文件下载请求对象
 * @author miaosc
 * @date 2020/9/26 0026
 */
public class FileWriteRequest extends FileOperationRequest {

    /**
     * 文件写入起点偏移量,默认0:表示头开头写入文件
     */
    private long offset;

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
