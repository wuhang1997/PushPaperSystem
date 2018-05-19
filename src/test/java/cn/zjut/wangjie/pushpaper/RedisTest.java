package cn.zjut.wangjie.pushpaper;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-19 21:11
 **/
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@Slf4j
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testList(){
        redisTemplate.opsForList().leftPush("test",123);
        List list = redisTemplate.opsForList().range("test",0,10);
        log.info("\n list size:"+list.size());
    }
}
