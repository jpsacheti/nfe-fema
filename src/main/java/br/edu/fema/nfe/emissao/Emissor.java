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

package br.edu.fema.nfe.emissao;

/**
 * Interface base para criar classes que façam a comunicação com o Webservice
 * da Secretaria da Fazenda
 */
public interface Emissor {
    /**
     * Método responsável por instanciar o stub (classe de comunicação gerada pelo AXIS)
     * passar os parâmetros e realizar o envio via HTTPS para os servidores da Sefaz
     * autorizadora. É importante que os {@link br.edu.fema.nfe.util.GeradorCacerts} tenham sido gerados
     *
     * @param documento de autorização a ser enviado
     * @return o XML com o resultado da resposta
     * @throws Exception
     */
    String emitir(String documento) throws Exception;
}
