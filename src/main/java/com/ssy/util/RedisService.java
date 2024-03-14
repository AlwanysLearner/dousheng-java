package com.ssy.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate,StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void addToSet(String key, String... values) {
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        setOps.add(key, values);
    }

    public Set<String> getSetMembers(String key) {
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        return setOps.members(key);
    }

    public boolean isMemberOfSet(String key, String value) {
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        return setOps.isMember(key, value);
    }

    public void insertElementAtHead(String listKey, String value) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        // 将整型转换为字符串进行存储
        listOps.leftPush(listKey, value);
    }

    // 通过下标访问列表中的整型元素
    public Integer getElementByIndex(String listKey, long index) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        if(listOps.size(listKey)<=index){
            return -1;
        }
        String value = listOps.index(listKey, index);
        // 将检索到的字符串转换回整型
        return Integer.parseInt(value);
    }
    public List<String> range(String key, long start, long end) {
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        return listOps.range(key, start, end);
    }
    public void setString(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    // 获取字符串值
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void incrementScore(String key, String element) {
        redisTemplate.opsForZSet().incrementScore(key, element, 1);
    }

    // 获取分数最高的5个元素
    public Set<String> getTopElements(String key,int index) {
        // 注意这里的range是0-based，所以我们取0到4来获取前5个
        return redisTemplate.opsForZSet().reverseRange(key, 0, index);
    }

    // 检查键是否存在
    public boolean keyExists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void recreateList(String key, List<String> values) {
        // 删除已存在的List
        redisTemplate.delete(key);

        // 使用RPUSH重新创建List
        if (!values.isEmpty()) {
            redisTemplate.opsForList().rightPushAll(key, values);
        }
    }

    public void deleteKey(String key){
        redisTemplate.delete(key);
    }


    public void ensureMaxSize(String key,int MAX_SIZE) {
        long currentSize = stringRedisTemplate.opsForZSet().size(key);
        if (currentSize > MAX_SIZE) {
            // 计算需要移除的元素数量
            long start = 0;
            long end = -(MAX_SIZE - currentSize) - 1;
            // 删除索引范围外的元素
            stringRedisTemplate.opsForZSet().removeRange(key, start, end);
        }
    }
}
