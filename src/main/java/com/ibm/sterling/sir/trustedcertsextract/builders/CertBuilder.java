package com.ibm.sterling.sir.trustedcertsextract.builders;

import com.ibm.sterling.sir.trustedcertsextract.domain.TrustedCert;
import org.springframework.stereotype.Component;


import java.security.MessageDigest;
import javax.security.cert.X509Certificate;
import java.text.SimpleDateFormat;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Formatter;

@Component
public class CertBuilder {

    final String datePattern = "dd/MM/yyyy";
    final SimpleDateFormat df = new SimpleDateFormat(datePattern);

    public void realise(TrustedCert certIn){

        byte[] decoded = Base64.getDecoder().decode(certIn.getCertData());
        try {
            X509Certificate X509cert = X509Certificate.getInstance(decoded);
            MessageDigest md1 = MessageDigest.getInstance("SHA-1");
            byte[] der = X509cert.getEncoded();
            md1.update(der);
            byte[] sha1Byte = md1.digest();

            MessageDigest md2 = MessageDigest.getInstance("SHA-256");
            der = X509cert.getEncoded();
            md2.update(der);
            byte[] sha2Byte = md2.digest();
            certIn.setSerialNumber(X509cert.getSerialNumber()+"");
            certIn.setIssuerDN(X509cert.getIssuerDN().getName());
            certIn.setSubjectDN(X509cert.getSubjectDN().getName());
            certIn.setStartDate(df.format(X509cert.getNotBefore()));
            certIn.setEndDate(df.format(X509cert.getNotAfter()));
            certIn.setSha1(byteArray2Hex(sha1Byte));
            certIn.setSha256(byteArray2Hex(sha2Byte));

        } catch (javax.security.cert.CertificateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private static String byteArray2Hex(final byte[] hash) {

        String retval;
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        retval = formatter.toString();
        formatter.close();
        return retval;
    }

}
