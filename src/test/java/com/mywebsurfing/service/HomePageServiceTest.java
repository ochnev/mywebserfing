package com.mywebsurfing.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({ MockitoExtension.class, SpringExtension.class})
@ActiveProfiles("test")
class HomePageServiceTest {

//    @MockBean
    @Autowired
    private HomePageService service;

    @Test
    public void test() {
        assertNotNull(service);
    }

}
