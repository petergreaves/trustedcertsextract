package com.ibm.sterling.sir.trustedcertsextract.config;

import com.ibm.sterling.sir.trustedcertsextract.domain.RESTProperties;
import com.ibm.sterling.sir.trustedcertsextract.domain.TrustedCert;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TCEExtractPropertiesConfig {

    @Bean
    public RESTProperties getRestProperties(){

        return new RESTProperties();
    }

}
