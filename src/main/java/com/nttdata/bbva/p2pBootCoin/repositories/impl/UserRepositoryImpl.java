package com.nttdata.bbva.p2pBootCoin.repositories.impl;

import com.nttdata.bbva.p2pBootCoin.models.User;
import com.nttdata.bbva.p2pBootCoin.repositories.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private static final String KEY = "P2P_USER";

    private final RedisTemplate<String, User> redisTemplate;
    private final HashOperations<String, String, User> hashOperations;

    public UserRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public List<User> findAll() {
        return hashOperations.values(KEY);
    }

    @Override
    public User findById(String id) {
        return hashOperations.get(KEY, id);
    }

    @Override
    public User save(User obj) {
        hashOperations.put(KEY, obj.getId(), obj);
        return obj;
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }
}
