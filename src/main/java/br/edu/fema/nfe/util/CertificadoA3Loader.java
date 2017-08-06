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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

public class CertificadoA3Loader implements KeyStoreLoader {

    private KeyStore keyStore;

    @SuppressWarnings("restriction")
    public CertificadoA3Loader() throws KeyStoreException {
        Provider provider;
        provider = new sun.security.pkcs11.SunPKCS11(getStreamConfig());
        Security.addProvider(provider);
        keyStore = KeyStore.getInstance("pkcs11", provider);
    }

    private InputStream getStreamConfig() {
        StringBuilder sb = new StringBuilder();
        sb.append("name = SmartCard");
        sb.append("\n");
        sb.append("showInfo = true");
        sb.append("\n");
        sb.append("library = ");
        sb.append("caminho_para_dll_Cartao");
        return new ByteArrayInputStream(sb.toString().getBytes());
    }

    @Override
    public KeyStore load(char[] senha) throws NoSuchAlgorithmException, CertificateException, IOException {
        keyStore.load(null, senha);
        return keyStore;
    }


}
