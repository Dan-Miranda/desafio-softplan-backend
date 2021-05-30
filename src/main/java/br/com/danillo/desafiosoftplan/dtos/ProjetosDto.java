package br.com.danillo.desafiosoftplan.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjetosDto {
    private String backend;
    private String dockerhub;
    private String frontend;
}
