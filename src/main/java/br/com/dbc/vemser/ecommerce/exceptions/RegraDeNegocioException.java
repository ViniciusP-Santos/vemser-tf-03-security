package br.com.dbc.vemser.ecommerce.exceptions;


public class RegraDeNegocioException extends Exception {

//    private Map<String, String> camposViolados;

    public RegraDeNegocioException(String mensagemDeErro) {
        super(mensagemDeErro);
    }

//    public RegraDeNegocioException(Map<String, String> camposViolados) {
//        this.camposViolados = camposViolados;
//    }
//
//    public Map<String, String> getCamposViolados() {
//        return camposViolados;
//    }
}