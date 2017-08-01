package br.edu.fema.nfe.gerador;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.edu.fema.nfe.util.KeyStoreLoader;

public class AssinadorDocumento {
	private final static XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
	private static PrivateKey privateKey;
	private static KeyInfo keyInfo;
	private static Document documento;
	private static List<Transform> transformList;

	public static String assinarDocumento(String arquivoXml, KeyStoreLoader keyStoreLoader,
			char[] senhaCertificado) throws Exception {
		carregarCertificado(keyStoreLoader, senhaCertificado);
		carregarTransforms();
		documento = carregarDocumento(arquivoXml);
		assinarNFe();
		return exportarXml();
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

	private static void carregarTransforms() throws NoSuchAlgorithmException,
			InvalidAlgorithmParameterException {
		transformList = new ArrayList<Transform>();
		TransformParameterSpec tps = null;
		Transform envelopedTransform = signatureFactory.newTransform(Transform.ENVELOPED, tps);
		Transform c14NTransform = signatureFactory.newTransform(
				"http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);
		transformList.add(envelopedTransform);
		transformList.add(c14NTransform);
	
	}

	private static Document carregarDocumento(String arquivoXml) throws SAXException, IOException,
			ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		Document document = factory.newDocumentBuilder().parse(
				new ByteArrayInputStream(arquivoXml.getBytes()));
	
		return document;
	}

	private static void assinarNFe() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			MarshalException, XMLSignatureException {
		NodeList elements = documento.getElementsByTagName("infNFe");
		Element el = (Element) elements.item(0);
		el.setIdAttribute("Id", true);
		String id = el.getAttribute("Id");
		Reference ref = signatureFactory.newReference("#" + id,
				signatureFactory.newDigestMethod(DigestMethod.SHA1, null), transformList, null, null);
		SignedInfo si = signatureFactory.newSignedInfo(signatureFactory.newCanonicalizationMethod(
				CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null), signatureFactory
				.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));
		XMLSignature signature = signatureFactory.newXMLSignature(si, keyInfo);
		DOMSignContext dsc = new DOMSignContext(privateKey, documento.getDocumentElement()
				.getElementsByTagName("NFe").item(0));
		signature.sign(dsc);
	
	}

	private static String exportarXml() throws TransformerException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.transform(new DOMSource(documento), new StreamResult(os));
		String xml = os.toString();
		return xml;
	}
}
