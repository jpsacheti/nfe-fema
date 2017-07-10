package br.edu.fema.nfe.model;

public enum Estado {
	RONDONIA(11), ACRE (12), AMAZONAS(13), RORAIMA(14), PARA(15), AMAPÁ(16),
	TOCANTINS(17), MARANHÃO(21), PIAUI(22), CEARA(23), RIO_GRANDE_DO_NORTE(24), PARAÍBA (25),
	PERNAMBUCO(26), ALAGOAS(27), SERGIPE(28), BAHIA(29), MINAS_GERAIS(31), ESPIRITO_SANTO(32),
	RIO_DE_JANEIRO(33), SAO_PAULO(35), PARANA(41), SANTA_CATARINA(42), RIO_GRANDE_DO_SUL(43),
	MATO_GROSSO_DO_SUL(50), MATO_GROSSO(51), GOIAS(52), DISTRITO_FEDERAL(53);
	
	private Integer codigoIbge;
	
	private Estado(Integer codigoIbge){
		this.codigoIbge = codigoIbge;
	}
	
	public Integer getCodigoIbge() {
		return codigoIbge;
	}
}
