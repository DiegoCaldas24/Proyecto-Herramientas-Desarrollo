package com.ERPMuni.ERPmuni.service;

import com.ERPMuni.ERPmuni.model.UserModel;
import com.ERPMuni.ERPmuni.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<UserModel> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
