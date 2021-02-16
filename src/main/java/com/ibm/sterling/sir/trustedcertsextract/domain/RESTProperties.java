package com.ibm.sterling.sir.trustedcertsextract.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Setter
public class RESTProperties {

    @Value("${trustedcerts.rest.path}")
    private String URI;

    @Value("${trustedcerts.rest.host}")
    private String hostAndPort;

    @Value("${trustedcerts.rest.username}")
    private String username;

    @Value("${trustedcerts.rest.password}")
    private String password;

}
