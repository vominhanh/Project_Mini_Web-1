package com.training.service;

import com.training.entity.AppUserEntity;
import com.training.repository.UserRepository;

public class UserService {

    private final UserRepository
            repository =
            new UserRepository();

    public AppUserEntity findByUsername(
            String username
    ) {

        return repository.findByUsername(
                username
        );
    }
}