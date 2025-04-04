package com.example.FlowerShop.interfaces;

import com.example.FlowerShop.model.AppUser;
import com.example.FlowerShop.service.AppService;

import java.util.Optional;

public interface AppUserService extends AppService<AppUser> {

    enum ROLES {
        ADMINISTRATOR,
        MANAGER,
        USER
    }
    Optional<AppUser> findByUsername(String username);
    AppUser getCurrentUser();
    void setCurrentUser(AppUser user);
    AppUser updateUser(AppUser user);
}

