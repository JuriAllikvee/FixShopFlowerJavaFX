package com.example.FlowerShop.service;

import com.example.FlowerShop.model.AppUser;
import com.example.FlowerShop.repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl {

    public enum ROLES {
        ADMINISTRATOR, MANAGER, USER
    }

    public static AppUser currentUser;

    private final AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser add(AppUser user){
        return appUserRepository.save(user);
    }

    public Optional<AppUser> findByUsername(String username){
        return appUserRepository.findByUsername(username);
    }
}
