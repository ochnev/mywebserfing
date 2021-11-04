package com.mywebsurfing.repository;

import com.mywebsurfing.entity.Realm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealmRepository extends JpaRepository<Realm, Long> {
}
