package com.nttdata.bbva.p2pBootCoin.services.impl;

import com.nttdata.bbva.p2pBootCoin.exceptions.BadRequestException;
import com.nttdata.bbva.p2pBootCoin.models.*;
import com.nttdata.bbva.p2pBootCoin.repositories.IRequestBootCoinRepository;
import com.nttdata.bbva.p2pBootCoin.services.IRequestBootCoinService;
import com.nttdata.bbva.p2pBootCoin.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RequestBootCoinServiceImpl implements IRequestBootCoinService {
    private static final Logger logger = LoggerFactory.getLogger(RequestBootCoinServiceImpl.class);
    @Autowired
    private IRequestBootCoinRepository repo;
    @Autowired
    private IUserService userService;
    @Autowired
    private KafkaTemplate<String, EventP2P> produder;
    private final String topicOperationP2P = "operationP2P";

    @Override
    public RequestBootCoin insert(RequestBootCoin obj) {
        if (obj.getId() == null || obj.getId().isEmpty()) {
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            obj.setId(id);
        }
        return repo.save(obj);
    }

    @Override
    public RequestBootCoin update(RequestBootCoin obj) {
        return null;
    }

    @Override
    public List<RequestBootCoin> findAll() {
        return repo.findAll();
    }

    @Override
    public RequestBootCoin findById(String id) {
        return repo.findById(id);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public RequestBootCoin acceptRequest(AcceptRequest obj) {
        logger.info("Inicio RequestBootCoinServiceImpl ::: acceptRequest");
        try {
            User user = userService.findById(obj.getUserIdAccept());
            RequestBootCoin bootCoin = this.findById(obj.getRequestBootCoinId());
            logger.info("PASOOOOO");
            if (user == null) {
                throw new BadRequestException("El campo userIdAccept no es válido.");
            } else if (bootCoin == null) {
                throw new BadRequestException("El campo requestBootCoinId no es válido.");
            } else if (user.getId().equals(bootCoin.getUserId())) {
                throw new BadRequestException("El usuario no puede acceptar su solicitud.");
            } else if (bootCoin.getUserIdAccept() != null) {
                throw new BadRequestException("La solicitud ya fue aceptada.");
            } else {
                EventP2PRequest eventRequest = EventP2PRequest.builder()
                        .openAccountId(user.getBankAccountNumber())
                        .operationType("D")
                        .amount(bootCoin.getAmount()).build();
                EventP2P event = EventP2P.builder()
                        .id(UUID.randomUUID().toString())
                        .date(new Date())
                        .eventRequest(eventRequest)
                        .build();
                this.produder.send(topicOperationP2P, event);

                bootCoin.setUserIdAccept(user.getId());
                return repo.save(bootCoin);
            }
        } catch (Exception ex) {
            logger.info("Error RequestBootCoinServiceImpl ::: acceptRequest ::: {}", ex.toString());
            logger.info("Fin RequestBootCoinServiceImpl ::: acceptRequest");
            throw ex;
        }
    }
}
