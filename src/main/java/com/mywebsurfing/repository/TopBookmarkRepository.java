package com.mywebsurfing.repository;

import com.mywebsurfing.entity.TopBookmark;
import com.mywebsurfing.entity.Realm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopBookmarkRepository extends JpaRepository<TopBookmark, Long> {
    TopBookmark findByUrlAndRealm(String url, Realm realm);
}
