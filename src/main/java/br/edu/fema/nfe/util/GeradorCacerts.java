/*
 * Copyright 2017 João Pedro Sacheti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License athttp://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.edu.fema.nfe.util;

import javax.net.ssl.*;
import java.io.*;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Essa classe gera um arquivo chamado NFeCacerts contendo todos os
 * certificados SSL dos servidores da secretaria da fazenda, sendo necessário vincular
 * tal arquivo na JVM para que a conexão SSL possa ocorrer sem erros.
 */
public class GeradorCacerts {

    private static final String JSSECACERTS = "NFeCacerts";

    private static final int TIMEOUT_WS = 30;

    public static void executarProcesso() {
        try {

            char[] passphrase = "changeit".toCharArray();

            File file = new File(JSSECACERTS);

            if (!file.isFile()) {

                char SEP = File.separatorChar;

                File dir = new File(System.getProperty("java.home") + SEP + "lib" + SEP + "security");

                file = new File(dir, JSSECACERTS);

                if (!file.isFile()) {

                    file = new File(dir, "cacerts");

                }

            }

            info("| Loading KeyStore " + file + "...");

            InputStream in = new FileInputStream(file);

            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

            ks.load(in, passphrase);

            in.close();

            get("homnfe.sefaz.am.gov.br", ks);
            get("hnfe.sefaz.ba.gov.br", ks);
            get("nfeh.sefaz.ce.gov.br", ks);
            get("homolog.sefaz.go.gov.br", ks);
            get("hnfe.fazenda.mg.gov.br", ks);
            get("homologacao.nfe.ms.gov.br", ks);
            get("homologacao.sefaz.mt.gov.br", ks);
            get("nfehomolog.sefaz.pe.gov.br", ks);
            get("homologacao.nfe2.fazenda.pr.gov.br", ks);
            get("homologacao.nfe.sefaz.rs.gov.br", ks);
            get("homologacao.nfe.fazenda.sp.gov.br", ks);
            get("hom.nfe.fazenda.gov.br", ks);
            get("hom.sefazvirtual.fazenda.gov.br", ks);
            get("homologacao.nfe.sefazvirtual.rs.gov.br", ks);

            File cafile = new File(JSSECACERTS);

            OutputStream out = new FileOutputStream(cafile);

            ks.store(out, passphrase);

            out.close();
            info("| Done");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    private static void get(String host, KeyStore ks) throws Exception {

        SSLContext context = SSLContext.getInstance("TLS");

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(

                TrustManagerFactory.getDefaultAlgorithm());

        tmf.init(ks);

        X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];

        SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);

        context.init(null, new TrustManager[] { tm }, null);

        SSLSocketFactory factory = context.getSocketFactory();

        info("| Opening connection to " + host + ":" + 443 + "...");

        SSLSocket socket = (SSLSocket) factory.createSocket(host, 443);

        socket.setSoTimeout(TIMEOUT_WS * 1000);

        try {

            info("| Starting SSL handshake...");

            socket.startHandshake();

            socket.close();

            info("| No errors, certificate is already trusted");

        } catch (SSLException e) {
            e.printStackTrace();
            error("| " + e.toString());

        }

        X509Certificate[] chain = tm.chain;

        if (chain == null) {

            info("| Could not obtain server certificate chain");

        } else {
            info("| Server sent " + chain.length + " certificate(s):");
        }

        MessageDigest sha1 = MessageDigest.getInstance("SHA1");

        MessageDigest md5 = MessageDigest.getInstance("MD5");

        for (int i = 0; i < (chain != null ? chain.length : 0); i++) {

            X509Certificate cert = chain[i];

            sha1.update(cert.getEncoded());

            md5.update(cert.getEncoded());

            String alias = host + "-" + (i);

            ks.setCertificateEntry(alias, cert);

            info("| Added certificate to keystore '" + JSSECACERTS + "' using alias '" + alias + "'");

        }

    }

    private static void info(String log) {

        System.out.println("|INFO: " + log);

    }

    private static void error(String log) {

        System.out.println("|ERROR: " + log);

    }

    private static class SavingTrustManager implements X509TrustManager {

        private final X509TrustManager tm;

        private X509Certificate[] chain;

        SavingTrustManager(X509TrustManager tm) {

            this.tm = tm;

        }

        public X509Certificate[] getAcceptedIssuers() {

            return new X509Certificate[0];

        }

        public void checkClientTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {

            throw new UnsupportedOperationException();

        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {

            this.chain = chain;

            tm.checkServerTrusted(chain, authType);

        }

    }

}
