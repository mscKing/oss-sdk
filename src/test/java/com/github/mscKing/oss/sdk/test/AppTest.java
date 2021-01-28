package com.github.mscKing.oss.sdk.test;

import com.github.mscking.oss.sdk.OssAutoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author miaosc
 * @date 2020/10/2 0002
 */
@Import(value = {OssAutoConfig.class})
@SpringBootApplication
public class AppTest {
    public static void main(String[] args) {
        SpringApplication.run(AppTest.class, args);
    }
}
