package br.com.danillo.desafiosoftplan.dtos;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OutputAlterarPessoaDto {
    @ApiModelProperty(example = "76839953041")
    private String idPessoa;

    @ApiModelProperty(example = "607874828fe0223030582bb8")
    private Date dataAtualizacao;
}
