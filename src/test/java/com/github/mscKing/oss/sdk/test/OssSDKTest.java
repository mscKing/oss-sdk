package com.github.mscKing.oss.sdk.test;

import com.github.mscking.oss.common.model.StorageBucket;
import com.github.mscking.oss.sdk.service.OssStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author miaosc
 * @date 2021/2/25 0025
 */
@SpringBootTest(classes = AppTest.class)
@RunWith(value = SpringRunner.class)
public class OssSDKTest {

    @Autowired
    OssStorageService ossStorageService;

    @Test
    public void testSdk(){
        List<StorageBucket> storageBuckets = ossStorageService.listAllBuckets();
        assert !storageBuckets.isEmpty();
    }

}
