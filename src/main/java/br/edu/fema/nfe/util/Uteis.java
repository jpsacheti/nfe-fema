package br.edu.fema.nfe.util;

import java.text.Normalizer;

public class Uteis {
    public static String normalizar(String string, int tamanho) {
        String resultado;
        resultado = string.trim();
        resultado = truncarTexto(resultado, tamanho);
        resultado = retirarAcentos(resultado);
        return resultado;
    }

    private static String retirarAcentos(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    private static String truncarTexto(String texto, int limite) {
        return texto.length() > limite ? texto.substring(0, limite - 1) : texto;
    }

    public static String extrairNumeros(String texto) {
        return texto.replaceAll("\\D+", "");
    }
}
