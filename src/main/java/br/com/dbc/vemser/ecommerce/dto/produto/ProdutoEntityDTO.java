package br.com.dbc.vemser.ecommerce.dto.produto;


import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProdutoEntityDTO {
    private Integer idProduto;
    private String modelo;
    private TipoTamanho tamanho;
    private String cor;
    private String descricao;
    private TipoSetor setor;
    private Double valor;


}
