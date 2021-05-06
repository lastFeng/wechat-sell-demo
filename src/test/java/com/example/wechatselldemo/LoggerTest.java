package com.example.wechatselldemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : guoweifeng
 * @date : 2021/5/6
 */
@SpringBootTest
@Slf4j
public class LoggerTest {

    @Test
    public void logTest() {
        String name = "Test";
        String password = "Password";
        log.debug("debug...");
        log.info("name: {}, password: {}", name, password);
        log.error("error...");
    }
}
