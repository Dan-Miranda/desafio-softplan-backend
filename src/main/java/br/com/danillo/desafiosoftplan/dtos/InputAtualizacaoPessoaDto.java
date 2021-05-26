package br.com.danillo.desafiosoftplan.dtos;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

import java.util.Optional;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InputAtualizacaoPessoaDto {
    @NotNull(message = "O campo nome é obrigatório.")
    @ApiModelProperty(example = "Danillo")
    private Optional<String> nome;

    @ApiModelProperty(example = "masculino")
    private Optional<String> sexo;

    @ApiModelProperty(example = "teste@teste.com")
    private Optional<String> email;

    @ApiModelProperty(example = "Brasil")
    private Optional<String> nacionalidade;


    @JsonIgnore
    public boolean validarEmail(){
        if (this.email.isPresent()) {
            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            var pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
            var matcher = pattern.matcher(this.email.get());
            return matcher.matches();
        }
        return false;
     }

}
