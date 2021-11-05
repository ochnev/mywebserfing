package com.mywebsurfing.repository;

import com.mywebsurfing.entity.AppUser;
import com.mywebsurfing.entity.Realm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Long> {
    List<Realm> findByAppUser(AppUser appUser);
    Realm findByNameAndAppUser(String name, AppUser appUser);
}
