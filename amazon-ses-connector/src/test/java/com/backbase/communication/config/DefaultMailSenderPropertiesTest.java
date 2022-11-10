package com.backbase.communication.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@EnableConfigurationProperties(value = DefaultMailSenderProperties.class)
public class DefaultMailSenderPropertiesTest {

    @Autowired
    DefaultMailSenderProperties defaultMailSenderProperties;

    @Test
    public void defaultMailSenderPropertiesShouldNotBeNull(){
        Assert.assertEquals("admin", defaultMailSenderProperties.getFromName());
        Assert.assertEquals("admin@test.com", defaultMailSenderProperties.getFromAddress());
    }

}