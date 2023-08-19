package br.com.dbc.vemser.ecommerce.dto.produto;

import br.com.dbc.vemser.ecommerce.entity.enums.TipoSetor;
import br.com.dbc.vemser.ecommerce.entity.enums.TipoTamanho;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCreateDTO {

    @Schema(description = "URL da imagem", required = true, example = "https://www.imagemteste.com/imagemTeste")
    private String url;

    @NotBlank
    @Size(min = 1, max = 30, message = "Verifique a quantidade de caracteres.")
    @Schema(description = "Modelo do produto", required = true, example = "MANGALONGA")
    private String modelo;



    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+", message = "Só é permitido um caractere: ex = P")
    @Size(min = 1, max = 1, message = "Número de caracteres excedido.")
    @Schema(description = "Tamanho do produto", required = true, example = "P")
    private String tamanho;



    @NotBlank
    @Pattern(regexp = "[a-zA-Z]+", message = "Somente letras é permitido.")
    @Size(min = 2, max = 150, message = "Verifique a quantidade de caracteres.")
    @Schema(description = "Cor do produto", required = true, example = "Branco")
    private String cor;

    @NotBlank

    @Size(min = 3, max = 150, message = "Verifique a quantidade de caracteres.")
    @Schema(description = "Setor do produto", required = true, example = "FEMININO")
    private String setor;


    @NotNull
    @Positive(message = "Não é permitido inserir valores menores do que zero.")
    @Schema(description = "Valor do produto", required = true)
    private Double valor;
}