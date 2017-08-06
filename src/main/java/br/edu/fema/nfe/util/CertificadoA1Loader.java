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

import java.io.InputStream;
import java.security.KeyStore;
import java.util.Objects;

public class CertificadoA1Loader implements KeyStoreLoader {

    private InputStream stream;

    public CertificadoA1Loader(InputStream stream) {
        this.stream = Objects.requireNonNull(stream);
    }

    @Override
    public KeyStore load(char[] senha) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        keyStore.load(stream, senha);
        return keyStore;
    }

}
