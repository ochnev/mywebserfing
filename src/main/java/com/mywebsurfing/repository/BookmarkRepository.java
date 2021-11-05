package com.mywebsurfing.repository;

import com.mywebsurfing.entity.Bookmark;
import com.mywebsurfing.entity.Folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Bookmark findByUrlAndFolder(String url, Folder folder);
}
