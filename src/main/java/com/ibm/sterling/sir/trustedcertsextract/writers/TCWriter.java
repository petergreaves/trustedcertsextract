package com.ibm.sterling.sir.trustedcertsextract.writers;

import com.ibm.sterling.sir.trustedcertsextract.domain.TrustedCert;

import java.io.FileNotFoundException;

public interface TCWriter {

    void  accept(TrustedCert cert);
    void  write() throws Exception;
}
