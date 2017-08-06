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

package br.edu.fema.nfe.validacao;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
		final String refinado = message.replaceAll("cvc-type.3.1.3:", "-").replaceAll("cvc-attribute.3:", "-")
				.replaceAll("cvc-complex-type.2.4.a:", "-").replaceAll("cvc-complex-type.2.4.b:", "-")
				.replaceAll("The value", "O valor").replaceAll("of element", "do campo")
				.replaceAll("is not valid", "nao é valido")
				.replaceAll("Invalid content was found starting with element", "Encontrado o campo")
				.replaceAll("One of", "Campo(s)").replaceAll("is expected", "é obrigatorio")
				.replaceAll("\\{", "").replaceAll("}", "").replaceAll("\"", "")
				.replaceAll("http://www.portalfiscal.inf.br/nfe:", "");
		return System.getProperty("line.separator") + refinado.trim();
	}
}
