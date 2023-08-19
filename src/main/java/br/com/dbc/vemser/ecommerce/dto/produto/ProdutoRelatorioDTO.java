package br.com.dbc.vemser.ecommerce.dto.produto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRelatorioDTO {

    private String modelo;
    private String setor;
    private Double valor;
}
