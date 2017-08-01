package br.edu.fema.nfe.util;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.Objects;

public class CertificadoA1Loader implements KeyStoreLoader {

	private KeyStore keyStore;
	private InputStream stream;

	public CertificadoA1Loader(InputStream stream) {
		this.stream = Objects.requireNonNull(stream);
	}

	@Override
	public KeyStore load(char[] senha) throws Exception {
		keyStore = KeyStore.getInstance("pkcs12");
		keyStore.load(stream, senha);
		return keyStore;
	}

}
