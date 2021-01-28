package com.github.mscking.oss.common.constant;

/**
 * 访问权限
 *
 * @author miaosc
 * @date 10/18/2019
 */
public enum AccessControlEnum {

    /**
     * 公共读,私有写
     */
    PUBLIC_READ,

    /**
     * 私有读写
     */
    PRIVATE_READ_WRITE {
        @Override
        public boolean canRead() {
            return false;
        }
    };

    /**
     * 是否能公共读取
     * @return
     */
    public boolean canRead() {
        return true;
    }

    /**
     * 是否能够共有写
     * @return
     */
    public boolean canWrite() {
        return false;
    }


}
