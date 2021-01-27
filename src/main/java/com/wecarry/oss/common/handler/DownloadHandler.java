package com.wecarry.oss.common.handler;

import java.io.IOException;
import java.io.InputStream;

/**
 * 下载对象句柄
 *
 * @author miaosc
 * @date 11/2/2019
 */
@FunctionalInterface
public interface DownloadHandler {

    /**
     * 读取数据
     *
     * @param inputStream
     * @throws IOException
     */
    void read(InputStream inputStream) throws IOException;
}
