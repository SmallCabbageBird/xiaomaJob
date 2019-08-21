package com.xiaoma.job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class JobApplicationTests {

    @Autowired
    private StringRedisTemplate template;

    @Cacheable(value = "user")
    public String get() {
        String id = "123";
        template.opsForValue().set(id,"张三");
        System.out.println("进入实现类获取数据！");
        return "张三";
    }

    @Test
    public void test(){
        System.out.println(get());
        System.out.println(get());
    }


    public String get(String id) {
        System.out.println("进入实现类获取数据！");
        String username = template.opsForValue().get(id);
        return username;
    }



}
