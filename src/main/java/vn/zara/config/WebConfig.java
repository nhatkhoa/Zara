// Copyright (c) 2015 KMS Technology, Inc.
package vn.zara.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@Profile("!utest")
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private CommonConfig commonConfig;

    @Bean
    public Validator getValidator() {
        return commonConfig.validator();
    }
}
