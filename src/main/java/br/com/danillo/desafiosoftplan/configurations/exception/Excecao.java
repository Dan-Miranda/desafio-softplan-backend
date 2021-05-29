package br.com.danillo.desafiosoftplan.configurations.exception;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Excecao {
    private String mensagem;
    private LocalDateTime timestamps;
}
