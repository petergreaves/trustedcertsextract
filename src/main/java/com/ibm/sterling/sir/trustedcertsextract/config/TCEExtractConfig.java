package com.ibm.sterling.sir.trustedcertsextract.config;

import com.ibm.sterling.sir.trustedcertsextract.TrustedcertsextractApplication;
import com.ibm.sterling.sir.trustedcertsextract.builders.CertBuilder;
import com.ibm.sterling.sir.trustedcertsextract.domain.RESTProperties;
import com.ibm.sterling.sir.trustedcertsextract.domain.TrustedCert;
import com.ibm.sterling.sir.trustedcertsextract.writers.TCWriter;
import com.ibm.sterling.sir.trustedcertsextract.writers.TCWriterFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;

@Configuration
@Slf4j
public class TCEExtractConfig {

    private Logger logger = LoggerFactory.getLogger(TCEExtractConfig.class);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    public void setCertBuilder(CertBuilder b){
        this.certBuilder = b;
    }

    private RESTProperties props;
    private TCWriter tcWriterFile;
    private CertBuilder certBuilder;

    @Autowired
    public void setProps(RESTProperties props){
        this.props = props;
    }


    @Autowired
    public void setTcWriter(TCWriter tcf){
        this.tcWriterFile= tcf;
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            String target = props.getHostAndPort()+props.getURI();

            HttpEntity<TrustedCert[]> entity = new HttpEntity<TrustedCert[]>(createHeaders(props.getUsername(),
                    props.getPassword()));
            ResponseEntity<TrustedCert[]> certs=restTemplate.exchange
                    (target, HttpMethod.GET, entity , TrustedCert[].class);

            Arrays.stream(certs.getBody()).forEach(c -> {

                certBuilder.realise(c);
                tcWriterFile.accept(c);

            });
            tcWriterFile.write();
        };
    }


    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }


}
