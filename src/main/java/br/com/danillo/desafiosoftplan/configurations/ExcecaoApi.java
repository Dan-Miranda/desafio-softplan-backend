package br.com.danillo.desafiosoftplan.configurations;

import java.time.LocalDateTime;

import com.spotify.docker.client.exceptions.NotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.danillo.desafiosoftplan.configurations.exception.EntidadeNaoEncontrada;
import br.com.danillo.desafiosoftplan.configurations.exception.Excecao;

@ControllerAdvice
public class ExcecaoApi extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> errosValidacao(IllegalArgumentException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(EntidadeNaoEncontrada.class)
    public ResponseEntity<?> entidadeNaoEncontrada(EntidadeNaoEncontrada e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        body = Excecao.builder()
                .mensagem(ex.getMessage())
                .timestamps(LocalDateTime.now())
                .build();

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
