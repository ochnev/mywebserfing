package com.mywebsurfing.service;

import com.mywebsurfing.entity.AppUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataExportServiceImpl implements DataExportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String exportData(AppUser user) {
        List<String> bookmarkRows = exportBookmarks(user);
        List<String> linkCollectionRows = exportLinkCollections(user);
        return String.join("\n", bookmarkRows) + "\n" + String.join("\n", linkCollectionRows);
    }

    private List<String> exportBookmarks(AppUser user) {
        String queryText = "SELECT r.name, f.name, b.url, b.title " +
                "FROM realm r JOIN folder f ON f.realm_id = r.id " +
                "JOIN bookmark b ON b.folder_id = f.id " +
                "WHERE r.app_user_id = ? " +
                "ORDER BY r.id ASC";
        Object[] args = new Object[] { user.getId() };
        int[] argTypes = new int[] { Types.BIGINT };

        List<String> rows = jdbcTemplate.query(queryText, args, argTypes,
                        (rs, rowNum) -> {
                            String realmName = rs.getString(1);
                            String folderName = rs.getString(2);
                            String linkUrl = rs.getString(3);
                            String linkTitle = rs.getString(4);
                            return String.join(";", Arrays.asList(realmName, folderName, linkUrl, linkTitle));
                        })
                .stream().collect(Collectors.toList());
        return rows;
    }

    private List<String> exportLinkCollections(AppUser user) {
        String queryText = "SELECT r.name, lc.url, lc.name " +
                "FROM realm r JOIN link_collection lc ON lc.realm_id = r.id " +
                "WHERE r.app_user_id = ? " +
                "ORDER BY r.id ASC";
        Object[] args = new Object[] { user.getId() };
        int[] argTypes = new int[] { Types.BIGINT };

        List<String> rows = jdbcTemplate.query(queryText, args, argTypes,
                        (rs, rowNum) -> {
                            String realmName = rs.getString(1);
                            String lcUrl = rs.getString(2);
                            String lcName = rs.getString(3);
                            return String.join(";", Arrays.asList(realmName, "", lcUrl, lcName));
                        })
                .stream().collect(Collectors.toList());
        return rows;
    }

}
