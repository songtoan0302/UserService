package com.ptit.userservice.repository;

import com.ptit.userservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisCacheRepository {
    private final RedisTemplate redisTemplate;
    private final String USER_REDIS_KEY = "USER";
    private final long TIME_TO_LIVE = 500000;

    public void save(User user) {
        redisTemplate.opsForHash().put(USER_REDIS_KEY, user.getId(), user);
        redisTemplate.expire(USER_REDIS_KEY, TIME_TO_LIVE, TimeUnit.MILLISECONDS);
    }

    public User findById(int id) {
        User user = (User) redisTemplate.opsForHash().get(USER_REDIS_KEY, id);
        if (Objects.nonNull(user)) {
            this.save(user);
        }
        return user;
    }

    public void deleteUser(int id) {
        redisTemplate.opsForHash().delete(USER_REDIS_KEY, id);
    }

    public List findAll() {
        return redisTemplate.opsForHash().values(USER_REDIS_KEY);
    }
}
