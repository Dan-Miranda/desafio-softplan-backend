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

    @ApiModelProperty(example = "2021-04-16T10:55:33.444")
    private Date dataAtualizacao;
}
