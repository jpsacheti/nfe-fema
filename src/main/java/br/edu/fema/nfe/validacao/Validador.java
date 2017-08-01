package br.edu.fema.nfe.validacao;

import java.util.List;

import org.xml.sax.ErrorHandler;

public interface Validador extends ErrorHandler {
	boolean validar(String valor) throws Exception;

	List<String> listaInconsistencias();
}
