package com.mywebsurfing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataImportReport {
    private Integer realms;
    private Integer folders;
    private Integer bookmarks;
}
