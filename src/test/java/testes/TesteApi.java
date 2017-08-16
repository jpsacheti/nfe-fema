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

package testes;

import br.edu.fema.nfe.gerador.GeradorDocumento;
import br.edu.fema.nfe.validacao.Validador;
import br.edu.fema.nfe.validacao.ValidadorEnvio;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TesteApi {
    public static void main(String[] args) throws Exception {
        GeradorDocumento gerador = new GeradorDocumento();
        final String documento = gerador.gerarDocumento();
        System.out.println(documento);
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("nota.xml"))) {
            bw.write(documento);
        }
        Validador validador = new ValidadorEnvio();
        validador.validar(documento);
        validador.listaInconsistencias().forEach(System.out::println);

    }
}
