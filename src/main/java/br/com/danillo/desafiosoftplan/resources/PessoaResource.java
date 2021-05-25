package br.com.danillo.desafiosoftplan.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.v1.FirestoreClient;
import com.spotify.docker.client.shaded.javax.ws.rs.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.danillo.desafiosoftplan.api.ApiResposta;
import br.com.danillo.desafiosoftplan.dtos.InputNovaPessoaDto;
import br.com.danillo.desafiosoftplan.dtos.OutputNovaPessoaDto;
import br.com.danillo.desafiosoftplan.dtos.PessoaDto;
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
    public ResponseEntity<OutputNovaPessoaDto> cadastrar(
        @ApiParam(value = "Dados a serem validados", required = true)
        @RequestBody InputNovaPessoaDto inputNovaPessoaDto)
        throws InterruptedException, ExecutionException {

        var resposta = pessoaService.inserePessoa(inputNovaPessoaDto);
        return ResponseEntity
            .created(URI.create(String.format("%s/%s", "/cadastrar", resposta.getIdPessoa())))
            .body(resposta);
    }

    @ApiOperation(value = "Este endpoint busca pessoas")
    @GetMapping("/buscar")
    public ResponseEntity<List<PessoaDto>> get() throws InterruptedException, ExecutionException {
        return ResponseEntity.ok(pessoaService.buscaPessoa());
    }
}
