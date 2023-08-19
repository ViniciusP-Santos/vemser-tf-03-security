package br.com.dbc.vemser.ecommerce.dto.produto;


import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRelatorioDTO {
    private String modelo;
    private TipoSetor setor;
    private Double valor;
}
