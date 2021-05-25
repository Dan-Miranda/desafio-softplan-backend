package br.com.danillo.desafiosoftplan.models;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Component
public class PessoaModel {
    private String nome;
    private String sexo;
    private String email;
    private Date dataNascimento;
    private String naturalidade;
    private String nacionalidade;
    private String cpf;
    private Date dataCadastro;
    private Date dataAtualizacao;
}
