package com.wecarry.oss.sdk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author miaosc
 * @date 2020/10/2 0002
 */
@Component
@ConfigurationProperties(prefix = "wecarry.oss")
public class OssSdkProperties {


    /**
     * 默认本机,端口80
     */
    public static final String DEFAULT_HOST = "http://localhost";

    /**
     * app唯一key
     */
    @NotNull
    private String appKey;

    /**
     * APP密钥
     */
    @NotNull
    private String appSecret;

    /**
     * SDK API调用的访问地址, eg: http:localhost:8090
     */
    private String host = DEFAULT_HOST;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
