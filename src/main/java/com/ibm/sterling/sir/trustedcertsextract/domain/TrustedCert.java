package com.ibm.sterling.sir.trustedcertsextract.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Builder
@Getter
@Setter
public class TrustedCert {
    private String certName;
    private String certData;
    private String serialNumber;
    private String sha1;
    private String sha256;
    private String startDate;
    private String endDate;
    private String issuerDN;
    private String subjectDN;
}
