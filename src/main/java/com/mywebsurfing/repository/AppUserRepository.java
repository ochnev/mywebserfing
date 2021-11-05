package com.mywebsurfing.repository;

import com.mywebsurfing.entity.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByLogin(String login);
}
