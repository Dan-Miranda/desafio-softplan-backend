package br.com.danillo.desafiosoftplan.api;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResposta<T> {
    private int code;
    private String status;
    private T data;
    private String error;
}
