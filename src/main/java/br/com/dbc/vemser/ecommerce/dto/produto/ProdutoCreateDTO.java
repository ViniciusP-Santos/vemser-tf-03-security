package br.com.dbc.vemser.ecommerce.dto.produto;

import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCreateDTO {


    @NotBlank
    @Schema(description = "Modelo do produto", required = true, example = "MANGALONGA")
    private String modelo;


    @NotNull
    @Schema(description = "Tamanho do produto", required = true, example = "3")
    private TipoTamanho tamanho;


    @NotBlank
    @Schema(description = "Cor do produto", required = true, example = "Branco")
    private String cor;

    @NotBlank
    @Schema(description = "Descricao do produto", required = true, example = "Produto de malha fina, se modela ao corpo...")
    private String descricao;

    @NotNull
    @Schema(description = "Setor do produto", required = true, example = "1")
    private TipoSetor setor;

    @NotNull
    @Schema(description = "Valor do produto", required = true)
    private Double valor;
}