package com.mywebsurfing.repository;

import com.mywebsurfing.entity.LinkCollection;
import com.mywebsurfing.entity.Realm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkCollectionRepository extends JpaRepository<LinkCollection, Long> {
    LinkCollection findByUrlAndRealm(String url, Realm realm);
}
