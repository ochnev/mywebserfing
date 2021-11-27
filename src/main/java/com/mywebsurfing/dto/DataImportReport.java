package com.mywebsurfing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataImportReport {
    private int realms;
    private int folders;
    private int linkCollections;
    private int bookmarks;
}
