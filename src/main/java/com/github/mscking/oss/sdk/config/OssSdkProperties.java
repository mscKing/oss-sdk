package com.github.mscking.oss.sdk.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


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
     * SDK API调用的访问地址, eg: http://localhost:8090
     */
    private String host = DEFAULT_HOST;

    /**
     * 文件外部访问地址, eg: http://oss.baidu.com
     * 没有就使用host
     */
    private String domain;

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
