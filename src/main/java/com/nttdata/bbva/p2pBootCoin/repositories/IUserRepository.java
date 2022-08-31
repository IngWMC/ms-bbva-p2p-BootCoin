package com.nttdata.bbva.p2pBootCoin.repositories;

import com.nttdata.bbva.p2pBootCoin.models.User;

import java.util.List;

public interface IUserRepository {
    List<User> findAll();
    User findById(String id);
    User save(User obj);
    void delete(String id);
}
