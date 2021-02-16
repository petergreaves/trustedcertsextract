package com.ibm.sterling.sir.trustedcertsextract;

import com.ibm.sterling.sir.trustedcertsextract.config.TCEExtractConfig;
import com.ibm.sterling.sir.trustedcertsextract.config.TCEExtractPropertiesConfig;
import com.ibm.sterling.sir.trustedcertsextract.domain.RESTProperties;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TCEExtractPropertiesConfig.class)
public class PropsTest {

    @Autowired
    RESTProperties restProps;

    @Test
    void getProperties() {

       assertNotNull(restProps.getPassword());
        assertNotNull(restProps.getURI());
        assertNotNull(restProps.getUsername());
        assertNotNull(restProps.getHostAndPort());
    }

}
