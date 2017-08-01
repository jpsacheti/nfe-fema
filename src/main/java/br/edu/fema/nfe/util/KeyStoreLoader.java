package br.edu.fema.nfe.util;

import java.security.KeyStore;

public interface KeyStoreLoader {
	KeyStore load(char[] senha) throws Exception;
}
