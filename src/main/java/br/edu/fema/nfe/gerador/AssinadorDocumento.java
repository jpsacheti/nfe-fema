package br.edu.fema.nfe.gerador;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;

import org.w3c.dom.Document;

import br.edu.fema.nfe.util.KeyStoreLoader;

public class AssinadorDocumento {
	private final static XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
	private static PrivateKey privateKey;
	private static KeyInfo keyInfo;
	private static Document documento;

	public static void assinarDocumento(String arquivoXml, KeyStoreLoader keyStoreLoader,
			char[] senhaCertificado) throws Exception {
		carregarCertificado(keyStoreLoader, senhaCertificado);
	}

	private static void carregarCertificado(KeyStoreLoader ksLoader, char[] senha) throws Exception {
		KeyStore ks = ksLoader.load(senha);

		KeyStore.PrivateKeyEntry pkEntry = null;
		Enumeration<String> aliasesEnum = ks.aliases();
		while (aliasesEnum.hasMoreElements()) {
			String alias = (String) aliasesEnum.nextElement();
			if (ks.isKeyEntry(alias)) {
				pkEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias,
						new KeyStore.PasswordProtection(senha));
				privateKey = pkEntry.getPrivateKey();
				break;
			}
		}

		X509Certificate cert = (X509Certificate) pkEntry.getCertificate();

		KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
		List<X509Certificate> x509Content = new ArrayList<X509Certificate>();

		x509Content.add(cert);
		X509Data x509Data = keyInfoFactory.newX509Data(x509Content);
		keyInfo = keyInfoFactory.newKeyInfo(Collections.singletonList(x509Data));
	}
}
