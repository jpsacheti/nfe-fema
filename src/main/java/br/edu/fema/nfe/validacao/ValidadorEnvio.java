package br.edu.fema.nfe.validacao;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ValidadorEnvio implements Validador {

	private final Path caminhoXsd;
	private List<String> inconsistencias = new ArrayList<>();

	public ValidadorEnvio() {
		caminhoXsd = Paths.get("schemas", "enviNFe_v3.10.xsd");
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		inconsistencias.add(filtrarRetorno(exception.getMessage()));

	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		inconsistencias.add(filtrarRetorno(exception.getMessage()));

	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		inconsistencias.add(filtrarRetorno(exception.getMessage()));

	}

	@Override
	public boolean validar(String valor) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);
		factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
				"http://www.w3.org/2001/XMLSchema");
		factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", caminhoXsd.toString());
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(this);
		builder.parse(new ByteArrayInputStream(valor.getBytes()));
		return inconsistencias.isEmpty();
	}

	@Override
	public List<String> listaInconsistencias() {
		return inconsistencias;
	}

	private String filtrarRetorno(String message) {
		message.replaceAll("cvc-type.3.1.3:", "-").replaceAll("cvc-attribute.3:", "-")
				.replaceAll("cvc-complex-type.2.4.a:", "-").replaceAll("cvc-complex-type.2.4.b:", "-")
				.replaceAll("The value", "O valor").replaceAll("of element", "do campo")
				.replaceAll("is not valid", "nao é valido")
				.replaceAll("Invalid content was found starting with element", "Encontrado o campo")
				.replaceAll("One of", "Campo(s)").replaceAll("is expected", "é obrigatorio")
				.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\"", "")
				.replaceAll("http://www.portalfiscal.inf.br/nfe:", "");
		return System.getProperty("line.separator") + message.trim();
	}
}
