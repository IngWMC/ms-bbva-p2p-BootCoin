package com.nttdata.bbva.p2pBootCoin.services.impl;

import com.nttdata.bbva.p2pBootCoin.exceptions.BadRequestException;
import com.nttdata.bbva.p2pBootCoin.models.OpenAccount;
import com.nttdata.bbva.p2pBootCoin.models.User;
import com.nttdata.bbva.p2pBootCoin.repositories.IUserRepository;
import com.nttdata.bbva.p2pBootCoin.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private IUserRepository repo;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public User insert(User obj) {
        if (obj.getId() == null || obj.getId().isEmpty()) {
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            obj.setId(id);

            long total;
            List<User> users = this.findAll();
            // Validar si el identificationDocument ya está registrado
            total = users.stream()
                    .filter(user -> user.getIdentificationDocument().equals(obj.getIdentificationDocument()))
                    .count();

            if (total > 0) {
                throw new BadRequestException("El campo identificationDocument no es válido.");
            }

            // Validar cuenta bancaria (único y que la cuenta se válido)
            OpenAccount openAccount = this.findOpenAccountById(obj.getBankAccountNumber());
            if (openAccount == null) {
                throw new BadRequestException("El bankAccountNumber no es válido.");
            }

            total = users.stream()
                    .filter(user -> user.getBankAccountNumber().equals(obj.getBankAccountNumber()))
                    .count();

            if (total > 0) {
                throw new BadRequestException("El bankAccountNumber no es válido.");
            }
        }
        return repo.save(obj);
    }

    @Override
    public User update(User obj) {
        Optional<User> user = this.convertToOptional(this.findById(obj.getId()));
        if (user.isPresent())
            return repo.save(obj);
        else
            throw new BadRequestException("El campo id es requerido.");
    }

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public User findById(String id) {
        return repo.findById(id);
    }

    @Override
    public void delete(String id) {
        repo.delete(id);
    }

    private Optional<User> convertToOptional(User obj) {
        return Optional.of(obj);
    }
    private OpenAccount findOpenAccountById(String id) {
        logger.info("Inicio UserServiceImpl ::: findOpenAccountById");
        OpenAccount openAccount;
        try {
            openAccount = restTemplate.getForObject("http://localhost:7073/api/1.0.0/openaccounts/" + id, OpenAccount.class);
            logger.info("Result UserServiceImpl ::: findOpenAccountById ::: {}", openAccount.toString());
            return openAccount;
        } catch (Exception ex) {
            logger.info("Error UserServiceImpl ::: findOpenAccountById ::: {}", ex.toString());
        } finally {
            logger.info("Fin UserServiceImpl ::: findOpenAccountById");
            return OpenAccount.builder().build();
        }

    }
}
