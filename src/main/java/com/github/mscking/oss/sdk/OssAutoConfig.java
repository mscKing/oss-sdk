package com.github.mscking.oss.sdk;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * 自动配置类
 *
 * @author miaosc
 * @date 11/27/2019
 */
@Configuration
@ComponentScan
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class OssAutoConfig {

}
