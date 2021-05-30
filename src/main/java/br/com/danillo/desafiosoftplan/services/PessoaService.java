package br.com.danillo.desafiosoftplan.services;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import br.com.danillo.desafiosoftplan.dtos.PessoaDto;
import br.com.danillo.desafiosoftplan.dtos.InputAtualizacaoPessoaDto;
import br.com.danillo.desafiosoftplan.dtos.OutputNovaPessoaDto;

@Service
public interface PessoaService {
    OutputNovaPessoaDto inserePessoa(final PessoaDto pessoaDto) throws InterruptedException, ExecutionException, ParseException;
    List<PessoaDto> buscaPessoas() throws InterruptedException, ExecutionException;
    PessoaDto buscaPessoaPorCpf(final String cpf) throws InterruptedException, ExecutionException;
    void atualizaPessoa(final String cpf, final InputAtualizacaoPessoaDto inputAtualizacaoPessoaDto) throws InterruptedException, ExecutionException;
    void deletaPessoa (final String cpf) throws InterruptedException, ExecutionException;
}
