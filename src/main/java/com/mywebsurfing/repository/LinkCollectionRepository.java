package com.mywebsurfing.repository;

import com.mywebsurfing.entity.Folder;
import com.mywebsurfing.entity.Realm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkCollectionRepository extends JpaRepository<Folder, Long> {
    Folder findByNameAndRealm(String name, Realm realm);
}
