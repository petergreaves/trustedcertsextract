package com.ibm.sterling.sir.trustedcertsextract.writers;

import com.ibm.sterling.sir.trustedcertsextract.builders.SQLInsertBuilder;
import com.ibm.sterling.sir.trustedcertsextract.domain.TrustedCert;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TCWriterFile implements TCWriter{
    private final Logger logger = LoggerFactory.getLogger(TCWriterFile.class);

    @Value("${trustedcerts.file.path}")
    private String outputFilePath;

    private final List<String> certInserts = new ArrayList<>();


    @Override
    public void accept(TrustedCert cert) {

        SQLInsertBuilder builder = new SQLInsertBuilder(cert);
        certInserts.add(builder.toInsert());
    }

    @Override
    public void write() throws FileNotFoundException {

        // now write to a file - each line is a SQL INSERT
        final PrintWriter writer = new PrintWriter(outputFilePath);

        final String defineStatement = "SET DEFINE OFF;" + System.lineSeparator();

        try {
            writer.write(defineStatement); //in case the cert name includes &
            writer.flush();

            // now the cert data
            certInserts.forEach(s -> {

                writer.write(s+ System.lineSeparator());
                writer.flush();

            } );
        }
        finally {
            writer.close();
            logger.info("Wrote " + certInserts.size() +" certs to " + outputFilePath);

        }

    }
}
