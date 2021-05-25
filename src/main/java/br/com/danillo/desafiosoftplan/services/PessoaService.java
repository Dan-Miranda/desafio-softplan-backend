package br.com.danillo.desafiosoftplan.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import br.com.danillo.desafiosoftplan.dtos.InputNovaPessoaDto;
import br.com.danillo.desafiosoftplan.dtos.OutputAlterarPessoaDto;
import br.com.danillo.desafiosoftplan.dtos.OutputNovaPessoaDto;
import br.com.danillo.desafiosoftplan.dtos.PessoaDto;

@Service
public interface PessoaService {
    OutputNovaPessoaDto inserePessoa(final InputNovaPessoaDto inputNovaPessoaDto) throws InterruptedException, ExecutionException;
    List<PessoaDto> buscaPessoa() throws InterruptedException, ExecutionException;
    PessoaDto buscaPessoaPorCpf(final String cpf) throws InterruptedException, ExecutionException;
    OutputAlterarPessoaDto alteraPessoa(final String idPessoa) throws InterruptedException, ExecutionException;
}
