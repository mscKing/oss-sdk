package com.github.mscking.oss.sdk.config;

import com.github.mscking.oss.common.util.HmacUtil;
import com.github.mscking.oss.rpc.StorageClient;
import com.github.mscking.oss.sdk.auth.CertificateInjectInterceptor;
import com.github.mscking.oss.sdk.service.OssStorageService;
import com.github.mscking.oss.sdk.service.impl.OssStorageServiceImpl;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;


/**
 * @author miaosc
 * @date 2020/10/2 0002
 */
@Configuration
public class OssSdkConfig {

    public static final String PUBLISH_URI = "/webServices/storageWebService?wsdl";

    @Autowired
    private OssSdkProperties ossSdkProperties;

    @Bean
    @ConditionalOnMissingBean(value = OssStorageService.class)
    public OssStorageService createStorageClientBean() {
        Assert.isTrue(ossSdkProperties.getAppKey()!=null,"没有配置appKey");
        Assert.isTrue(ossSdkProperties.getAppSecret()!=null,"没有配置appSecret");
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        CertificateInjectInterceptor certificateInjectInterceptor = new CertificateInjectInterceptor(ossSdkProperties.getAppKey(), ossSdkProperties.getAppSecret());
        jaxWsProxyFactoryBean.setAddress(ossSdkProperties.getHost() + PUBLISH_URI);
        jaxWsProxyFactoryBean.getOutInterceptors().add(certificateInjectInterceptor);
        // 设置接口类型
        jaxWsProxyFactoryBean.setServiceClass(StorageClient.class);
        StorageClient storageClient = (StorageClient) jaxWsProxyFactoryBean.create();
        OssStorageServiceImpl ossStorageServiceImpl = new OssStorageServiceImpl();
        ossStorageServiceImpl.setProxy(storageClient);
        ossStorageServiceImpl .setHmacUtil(HmacUtil.buildHmacSHA1(ossSdkProperties.getAppSecret()));
        ossStorageServiceImpl .setHttpClient(httpClientBuilder().build());
        ossStorageServiceImpl .setHost(ossSdkProperties.getHost());
        return ossStorageServiceImpl;
    }


    private HttpClientConnectionManager poolingConnectionManager() {
        // 支持HTTP、HTTPS
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(registry);
        // 连接池最大连接数
        poolingConnectionManager.setMaxTotal(10000);
        // 每个主机的并发
        poolingConnectionManager.setDefaultMaxPerRoute(200);
        return poolingConnectionManager;
    }


    private HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //设置HTTP连接管理器
        httpClientBuilder.setConnectionManager(poolingConnectionManager());
        return httpClientBuilder;
    }

}
