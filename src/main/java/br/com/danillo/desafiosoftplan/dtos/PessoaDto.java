package br.com.danillo.desafiosoftplan.dtos;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PessoaDto {
    @NotNull(message = "O campo nome é obrigatório.")
    @ApiModelProperty(example = "Danillo")
    private String nome;

    @ApiModelProperty(example = "masculino")
    private String sexo;

    @ApiModelProperty(example = "teste@teste.com")
    private String email;

    @NotNull(message = "O campo dataNascimento é obrigatório.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "America/Sao_Paulo")
    @ApiModelProperty(example = "2021-04-16T10:55:33.444")
    private String dataNascimento;

    @ApiModelProperty(example = "São Paulo - SP")
    private String naturalidade;

    @ApiModelProperty(example = "Brasil")
    private String nacionalidade;

    @ApiModelProperty(example = "04718242088")
    private String cpf;

    @ApiModelProperty(example = "2021-04-16T10:55:33.444")
    private Date dataCadastro;

    @ApiModelProperty(example = "2021-04-16T10:55:33.444")
    private Date dataAtualizacao;

    @JsonIgnore
    public boolean validarDataNascimento() throws ParseException {
        var formatoData = new SimpleDateFormat("dd/MM/yyyy");
        var data = formatoData.parse(this.dataNascimento);
        return data.before(new Date());
    }

    @JsonIgnore
    public boolean validarCpf(@Nullable String cpf) {
        var cpfString = "";
        if (cpf != null) {
            cpfString = cpf;
        } else {
            cpfString = this.cpf;
        }

        var isNumerosConsecutivos = cpfString.equals("00000000000") || cpfString.equals("11111111111") ||
            cpfString.equals("22222222222") || cpfString.equals("33333333333") ||
            cpfString.equals("44444444444") || cpfString.equals("55555555555") ||
            cpfString.equals("66666666666") || cpfString.equals("77777777777") ||
            cpfString.equals("88888888888") || cpfString.equals("99999999999") ||
            (cpfString.length() != 11);

        if (isNumerosConsecutivos) {
            return(false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                num = (int)(cpfString.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);

            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(cpfString.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

            if ((dig10 == cpfString.charAt(9)) && (dig11 == cpfString.charAt(10)))
                 return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    @JsonIgnore
    public boolean validarEmail(){
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        var pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(this.email);
        return matcher.matches();
     }

}
