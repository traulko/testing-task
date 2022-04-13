package com.traulka.app;

import com.traulka.app.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = WebConfig.class)
class PersonDataAppApplicationTests {

    @Test
    void contextLoads() {
    }

}
