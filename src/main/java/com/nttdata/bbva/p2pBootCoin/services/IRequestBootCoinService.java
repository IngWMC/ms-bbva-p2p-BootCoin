package com.nttdata.bbva.p2pBootCoin.services;

import com.nttdata.bbva.p2pBootCoin.models.AcceptRequest;
import com.nttdata.bbva.p2pBootCoin.models.RequestBootCoin;

public interface IRequestBootCoinService extends ICRUD<RequestBootCoin, String> {
    RequestBootCoin acceptRequest(AcceptRequest obj);
}
