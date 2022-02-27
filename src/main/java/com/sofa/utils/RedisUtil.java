package com.sofa.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,Object>redisTemplate;

    /**
     * 存值
     * @param key
     * @param value
     * @return
     */
    public boolean setValue(String key,Object value){
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("向redis中存入值异常-->{}",e.getMessage());
            return false;
        }
    }

    /**
     * 设置过期时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setValueTime(String key,Object value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
            }else{
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("设置缓存过期过期时间异常-->{}",e.getMessage());
            return false;
        }

    }

    /**
     * 取值
     * @param key
     * @return
     */
    public Object getValue(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key删除缓存
     * @param keys
     */
    public void delKey(String... keys){
        if(keys!=null && keys.length>0){
            if(keys.length==1){
                redisTemplate.delete(keys[0]);
            }else {
                for (String s : keys) {
                    redisTemplate.delete(s);
                }
            }
        }
    }

    /**
     * 判断值是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        try {
           return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis值不存在-->{}",e.getMessage());
            return false;
        }
    }

    /**
     * 获取键的过期时间
     * 0表示永久有效
     * >0表示还剩多少分钟
     * @param key
     * @return
     */
    public Long isExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.MINUTES);
    }

    /**
     * 给key加过期时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key,long time){
        try {
            if(time >0){
                redisTemplate.expire(key,time,TimeUnit.MINUTES);
            }
            return true;
        } catch (Exception e) {
            log.error("给旧的缓存设置新的过期时间异常-->{}",e.getMessage());
            return false;
        }
    }
}
