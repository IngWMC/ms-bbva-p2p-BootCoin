package com.nttdata.bbva.p2pBootCoin.repositories;

import com.nttdata.bbva.p2pBootCoin.models.RequestBootCoin;

import java.util.List;

public interface IRequestBootCoinRepository {
    RequestBootCoin save(RequestBootCoin obj);

    List<RequestBootCoin> findAll();
    RequestBootCoin findById(String id);
    RequestBootCoin acceptRequest(RequestBootCoin obj);
}
