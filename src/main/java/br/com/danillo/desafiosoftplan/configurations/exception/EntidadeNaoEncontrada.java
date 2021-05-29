package br.com.danillo.desafiosoftplan.configurations.exception;

public class EntidadeNaoEncontrada extends RuntimeException {
    public EntidadeNaoEncontrada(String mensagem) {
        super(mensagem);
    }
}
