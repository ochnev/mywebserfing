package com.mywebsurfing.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DataExportServiceTest extends BaseDataTest {

    @Autowired
    private DataImportService dataImportService;

    @Autowired
    private DataExportService dataExportService;

    @Test
    void testExportData() {
        String csvData = "IT;Blockchain;https://www.npmjs.com/package/create-eth-app;Create Eth App\n" +
                "IT;;https://github.com/crytic/awesome-ethereum-security;Awesome Ethereum Security\n";
        dataImportService.importData(csvData, defaultUser);
        String exportedData = dataExportService.exportData(defaultUser);
        assertNotNull(exportedData);

        String[] dataLines = exportedData.split("\n");
        String[] data1 = dataLines[0].split(";");
        assertEquals("IT", data1[0]);
        assertEquals("Blockchain", data1[1]);
        assertEquals("https://www.npmjs.com/package/create-eth-app", data1[2]);
        assertEquals("Create Eth App", data1[3]);
        String[] data2 = dataLines[1].split(";");
        assertEquals("IT", data2[0]);
        assertEquals("", data2[1]);
        assertEquals("https://github.com/crytic/awesome-ethereum-security", data2[2]);
        assertEquals("Awesome Ethereum Security", data2[3]);
    }

}