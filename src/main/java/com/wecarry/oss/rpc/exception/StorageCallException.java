package com.wecarry.oss.rpc.exception;

/**
 *  存储函数调用异常
 * @author miaosc
 * @date 2020/10/2 0002
 */
public class StorageCallException extends Exception {

    public StorageCallException() {
    }

    public StorageCallException(String message) {
        super(message);
    }

    public StorageCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageCallException(Throwable cause) {
        super(cause);
    }

    public StorageCallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
