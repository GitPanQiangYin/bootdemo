package com.boot.bootdemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 * @date 2020/7/21 15:39
 */

@Component
public class RedisUtil1 {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 获得最大分数
     * @param key
     * @return
     */
    public Double sMaxScore(String key){
        //获得最后一个对应的member，用于获得最大的分数
        Set<String> sortId = redisTemplate.opsForZSet().reverseRange(key, 0, 0);

        String hkey = sortId.iterator().next();
        //根据member获得最大分值
        Double maxScore = redisTemplate.opsForZSet().score(key, hkey);
        return maxScore;
    }

    /**
     * 分页获取成员
     * @param key
     * @param min 最小分数
     * @param max 最大分数
     * @param pageStart
     * @param pageSize
     * @return
     */
    public List sRangeByScore(String key, Double min, Double max, Long pageStart, Long pageSize){
        // 分页取数据
        Set<String> sets =  redisTemplate.opsForZSet().rangeByScore(key,min,max,(pageStart-1)*pageSize,pageStart*pageSize);
        List list = new ArrayList(sets);

        return list;
    }

    /**
     * 批量获得数据
     * @param key
     * @param hkeys
     * @return
     */
    public List hMultiGet(String key , List hkeys){
        return redisTemplate.opsForHash().multiGet(key, hkeys);
    }


}
