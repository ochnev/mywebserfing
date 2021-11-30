package com.mywebsurfing.repository;

import com.mywebsurfing.entity.Folder;
import com.mywebsurfing.entity.Realm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByRealm(Realm realm);
    Folder findByNameAndRealm(String name, Realm realm);
}
