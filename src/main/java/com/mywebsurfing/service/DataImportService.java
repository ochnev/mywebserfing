package com.mywebsurfing.service;

import com.mywebsurfing.dto.DataImportReport;
import com.mywebsurfing.entity.AppUser;

public interface DataImportService {
    DataImportReport importData(String csvFileData, AppUser user);
    DataImportReport importOneLine(String dataLine, AppUser user);
}
