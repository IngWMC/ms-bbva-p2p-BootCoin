package com.nttdata.bbva.p2pBootCoin.controllers;

import com.nttdata.bbva.p2pBootCoin.models.User;
import com.nttdata.bbva.p2pBootCoin.services.IUserService;
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
@RequestMapping("api/1.0.0/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService service;

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        logger.info("Inicio UserController ::: findAll");
        List<User> users = service.findAll();
        logger.info("Fin UserController ::: findAll");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id){
        logger.info("Inicio UserController ::: findById ::: " + id);
        User user = service.findById(id);
        logger.info("Fin UserController ::: findById");
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user);
    }

    @PostMapping
    public ResponseEntity<User> insert(@Valid @RequestBody User obj){
        logger.info("Inicio UserController ::: insert ::: " + obj);
        User user = service.insert(obj);
        logger.info("Fin UserController ::: insert");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User obj){
        logger.info("Inicio UserController ::: update ::: " + obj);
        User user = service.update(obj);
        logger.info("Fin UserController ::: update");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        logger.info("Inicio UserController ::: delete ::: " + id);
        service.delete(id);
        logger.info("Fin UserController ::: delete");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
