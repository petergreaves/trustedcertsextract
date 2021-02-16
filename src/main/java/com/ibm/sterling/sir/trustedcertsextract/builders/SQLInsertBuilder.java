package com.ibm.sterling.sir.trustedcertsextract.builders;

import com.ibm.sterling.sir.trustedcertsextract.domain.TrustedCert;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;

public class SQLInsertBuilder {


    private TrustedCert cert;

    @Autowired
    public SQLInsertBuilder(TrustedCert cert){

        this.cert = cert;

    }
    public String toInsert() {

       final CharSequence in = "'";
       final CharSequence out = "''";

        StringBuffer buf = new StringBuffer("INSERT INTO SCT_TRUSTED_CERTIFICATE ");
        buf.append("(CERT_ID, CERTIFICATE_NAME, SERIAL_NUMBER, START_DATE, END_DATE, THUMBPRINT, ISSUER, SUBJECT, CERTIFICATE, THUMBPRINT256) ");
        buf.append(" VALUES ");
        buf.append(" ('CERT_ID_' || to_char(SCT_TRUSTED_CERT_IDSEQ.NEXTVAL), ");
        buf.append("'" + cert.getCertName()+"',");
        buf.append("'" + cert.getSerialNumber()+"',");
        buf.append("'" + cert.getStartDate()+"',");
        buf.append("'" + cert.getEndDate()+"',");
        buf.append("'" + cert.getSha1()+"',");
        buf.append("'" + cert.getIssuerDN().replace(in, out)+"',");
        buf.append("'" + cert.getSubjectDN().replace(in, out)+"',");
        buf.append("NULL ,");
        buf.append("'" + cert.getSha256()+"');");

        return buf.toString();

    }

}
