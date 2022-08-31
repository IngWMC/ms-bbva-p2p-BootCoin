package com.nttdata.bbva.p2pBootCoin.repositories.impl;

import com.nttdata.bbva.p2pBootCoin.models.RequestBootCoin;
import com.nttdata.bbva.p2pBootCoin.repositories.IRequestBootCoinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RequestBootCoinRepositoryImpl implements IRequestBootCoinRepository {
    private static final Logger logger = LoggerFactory.getLogger(RequestBootCoinRepositoryImpl.class);
    private static final String KEY = "P2P_REQUEST_BOOT_COIN";

    private final RedisTemplate<String, RequestBootCoin> redisTemplateP2P;
    private final HashOperations<String, String, RequestBootCoin> hashOperations;

    public RequestBootCoinRepositoryImpl(RedisTemplate<String, RequestBootCoin> redisTemplateP2P) {
        this.redisTemplateP2P = redisTemplateP2P;
        this.hashOperations = redisTemplateP2P.opsForHash();
    }

    @Override
    public RequestBootCoin save(RequestBootCoin obj) {
        hashOperations.put(KEY, obj.getId(), obj);
        return obj;
    }

    @Override
    public List<RequestBootCoin> findAll() {
        return hashOperations.values(KEY);
    }

    @Override
    public RequestBootCoin findById(String id) {
        return hashOperations.get(KEY, id);
    }

    @Override
    public RequestBootCoin acceptRequest(RequestBootCoin obj) {
        return this.save(obj);
    }
}
