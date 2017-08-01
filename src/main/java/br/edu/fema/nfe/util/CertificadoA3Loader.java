package br.edu.fema.nfe.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
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
