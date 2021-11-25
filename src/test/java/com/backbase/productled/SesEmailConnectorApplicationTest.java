package com.backbase.productled;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SesEmailConnectorApplicationTest {

    static {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void shouldLoadContext() {
        assertNotNull(applicationContext);
    }

    @Test
    public void shouldLoadContextWithArgs() {
        SesEmailConnectorApplication.main(new String[]{"--spring.profiles.active=it"});
        assertNotNull(applicationContext);
    }
}