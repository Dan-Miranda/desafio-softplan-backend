package br.com.danillo.desafiosoftplan.resources;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.danillo.desafiosoftplan.dtos.PessoaDto;
import br.com.danillo.desafiosoftplan.dtos.ProjetosDto;
import br.com.danillo.desafiosoftplan.dtos.InputAtualizacaoPessoaDto;
import br.com.danillo.desafiosoftplan.dtos.OutputNovaPessoaDto;
import br.com.danillo.desafiosoftplan.services.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@Api(tags = { "Pessoas" })
@RequiredArgsConstructor
public class PessoaResource {

    private final PessoaService pessoaService;

    @ApiOperation(value = "Este endpoint cadastra novas pessoas")
    @PostMapping("/cadastrar")
    public ResponseEntity<OutputNovaPessoaDto> cadastraPessoa(
        @ApiParam(value = "Dados da pessoa que será cadastrada", required = true)
        @Valid @RequestBody PessoaDto pessoaDto)
        throws InterruptedException, ExecutionException {

        var resposta = pessoaService.inserePessoa(pessoaDto);
        return ResponseEntity
            .created(URI.create(String.format("%s/%s", "/cadastrar", resposta.getCpf())))
            .body(resposta);

    }

    @ApiOperation(value = "Este endpoint busca pessoas")
    @GetMapping("/buscar")
    public ResponseEntity<List<PessoaDto>> buscaPessoas() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(pessoaService.buscaPessoas());
    }

    @ApiOperation(value = "Este endpoint retorna os projetos do Danillo miranda")
    @GetMapping("/source")
    public ResponseEntity<ProjetosDto> projetos() {
        var links = new ProjetosDto(
            "https://github.com/Dan-Miranda/desafio-softplan-backend.git",
            "https://hub.docker.com/r/danillomiranda/desafio-softplan",
            "https://github.com/Dan-Miranda/desafio-softplan-frontend.git"
        );
        return ResponseEntity.status(HttpStatus.OK).body(links);
    }

    @ApiOperation(value = "Este endpoint busca uma pessoa por CPF")
    @GetMapping("/buscar/{cpf}")
    public ResponseEntity<PessoaDto> buscaPessoaPorCpf(
        @ApiParam(value = "CPF do cliente", required = true)
        @PathVariable(value = "cpf") String cpf
    ) throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(pessoaService.buscaPessoaPorCpf(cpf));
    }

    @ApiOperation(value = "Este endpoint atualiza pessoas")
    @PutMapping("/atualizar/{cpf}")
    public ResponseEntity<Object> atualizaPessoa(
        @ApiParam(value = "CPF do cliente", required = true)
        @PathVariable(value = "cpf") String cpf,
        @ApiParam(value = "Dados da pessoa que será cadastrada", required = true)
        @RequestBody InputAtualizacaoPessoaDto inputAtualizacaoPessoaDto) throws InterruptedException, ExecutionException {

        pessoaService.atualizaPessoa(cpf, inputAtualizacaoPessoaDto);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Este endpoint atualiza pessoas")
    @DeleteMapping("/deletar/{cpf}")
    public ResponseEntity<Object> deletaPessoa(
        @ApiParam(value = "CPF do cliente", required = true)
        @PathVariable(value = "cpf") String cpf) throws InterruptedException, ExecutionException {

        pessoaService.deletaPessoa(cpf);
        return ResponseEntity.noContent().build();
    }
}
