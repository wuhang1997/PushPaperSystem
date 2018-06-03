package cn.zjut.wangjie.pushpaper.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by WangJie on 2017/10/13.
 */
@Component
public class RedisTemplateUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 存储字符串
     * @param key string类型的key
     * @param value String类型的value
     */
  /*  public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }*/

    /**
     * 存储对象
     * @param key String类型的key
     * @param value Object类型的value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key,List list){
        redisTemplate.opsForList().leftPushAll(key,list);
    }
    public void set(String key ,Map map){
        redisTemplate.opsForHash().putAll(key,map);
    }
    public void set(String key,Set<Object>set){
        for (Object o:set) {
            redisTemplate.opsForSet().add(key,o);
        }
    }
    /**
     * 存储对象
     * @param key String类型的key
     * @param value Object类型的value
     */
    public void set(String key, Object value,Long timeOut) {
        redisTemplate.opsForValue().set(key, value,timeOut, TimeUnit.SECONDS);
    }

    public  void addToZSet(String key, Object value ,double source){
        redisTemplate.opsForZSet().add(key,value,source);
    }
    /**
     * 根据key获取字符串数据
     * @param key
     * @return
     */
  /*  public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }*/

//    public Object getValue(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }
    /**
     * 根据key获取对象
     * @param key
     * @return
     */
    public Object getValueOfObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public List getList(String key ,long start,long end){

        return redisTemplate.opsForList().range(key,start,end);
    }
    public Map getMap(String key){
        return redisTemplate.opsForHash().entries(key);
    }
    public Set getSet(String key){
        return  redisTemplate.opsForSet().members(key);
    }

    public Set getZSet(String key){
        return redisTemplate.opsForZSet().range(key,0,-1);
    }
    /**
     * 根据key删除缓存信息
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
    /**
     * 查询key是否存在
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public RedisTemplate<String,Object> getRedisTemplate(){
        return redisTemplate;
    }


}
