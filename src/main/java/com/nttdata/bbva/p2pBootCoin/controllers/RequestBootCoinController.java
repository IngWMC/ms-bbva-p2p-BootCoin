package com.nttdata.bbva.p2pBootCoin.controllers;

import com.nttdata.bbva.p2pBootCoin.models.AcceptRequest;
import com.nttdata.bbva.p2pBootCoin.models.RequestBootCoin;
import com.nttdata.bbva.p2pBootCoin.services.IRequestBootCoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/1.0.0/requests-boot-coin")
public class RequestBootCoinController {
    private static final Logger logger = LoggerFactory.getLogger(RequestBootCoinController.class);
    @Autowired
    private IRequestBootCoinService service;

    @GetMapping
    public ResponseEntity<List<RequestBootCoin>> findAll(){
        logger.info("Inicio RequestBootCoinController ::: findAll");
        List<RequestBootCoin> bootCoins = service.findAll();
        logger.info("Fin RequestBootCoinController ::: findAll");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bootCoins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestBootCoin> findById(@PathVariable("id") String id){
        logger.info("Inicio RequestBootCoinController ::: findById ::: " + id);
        RequestBootCoin bootCoin = service.findById(id);
        logger.info("Fin RequestBootCoinController ::: findById");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bootCoin);
    }

    @PostMapping
    public ResponseEntity<RequestBootCoin> insert(@Valid @RequestBody RequestBootCoin obj){
        logger.info("Inicio RequestBootCoinController ::: insert ::: " + obj);
        RequestBootCoin bootCoin = service.insert(obj);
        logger.info("Fin RequestBootCoinController ::: insert");
        return new ResponseEntity<>(bootCoin, HttpStatus.CREATED);
    }

    @PostMapping("/accept")
    public ResponseEntity<RequestBootCoin> acceptRequest(@Valid @RequestBody AcceptRequest obj){
        logger.info("Inicio RequestBootCoinController ::: acceptRequest ::: " + obj);
        RequestBootCoin bootCoin = service.acceptRequest(obj);
        logger.info("Fin RequestBootCoinController ::: acceptRequest");
        return new ResponseEntity<>(bootCoin, HttpStatus.CREATED);
    }

}
