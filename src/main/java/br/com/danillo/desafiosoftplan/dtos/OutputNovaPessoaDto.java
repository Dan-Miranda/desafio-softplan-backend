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
public class OutputNovaPessoaDto {
    @ApiModelProperty(example = "607874828fe0223030582bb8")
    private String cpf;

    @ApiModelProperty(example = "2021-04-16T10:55:33.444")
    private Date dataCadastro;
}
