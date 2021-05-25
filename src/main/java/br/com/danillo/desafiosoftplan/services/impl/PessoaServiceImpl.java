package br.com.danillo.desafiosoftplan.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentReference;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.danillo.desafiosoftplan.dtos.InputNovaPessoaDto;
import br.com.danillo.desafiosoftplan.dtos.OutputAlterarPessoaDto;
import br.com.danillo.desafiosoftplan.dtos.OutputNovaPessoaDto;
import br.com.danillo.desafiosoftplan.dtos.PessoaDto;
import br.com.danillo.desafiosoftplan.models.PessoaModel;
import br.com.danillo.desafiosoftplan.services.FirebaseService;
import br.com.danillo.desafiosoftplan.services.PessoaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    private final FirebaseService firebaseService;
    private final ModelMapper modelMapper;

    public static final String COL_NAME="pessoas";

    @Override
    public OutputNovaPessoaDto inserePessoa(InputNovaPessoaDto inputNovaPessoaDto) throws InterruptedException, ExecutionException {
        Assert.isTrue(inputNovaPessoaDto.validarCpf(), "CPF inválido");
        Assert.isTrue(inputNovaPessoaDto.validarDataNascimento(), "Data de nascimento inválida");

        if (inputNovaPessoaDto.getEmail().length() > 0) {
            Assert.isTrue(inputNovaPessoaDto.validarEmail(), "Email inválido");
        }

        var pessoas = this.buscaPessoa();

        pessoas.stream().forEach(pessoa -> {
            var documentoExistente = pessoa.getCpf() != null && pessoa.getCpf().equals(inputNovaPessoaDto.getCpf()) ;
            Assert.isTrue(!documentoExistente, "Pessoa já cadastrada");
        });

        var colecaoPessoas = firebaseService.getFirestore().collection(COL_NAME);

        var hoje = new Date();
        var novaPessoa = modelMapper.map(inputNovaPessoaDto, PessoaModel.class);
        novaPessoa.setDataAtualizacao(hoje);
        novaPessoa.setDataCadastro(hoje);
        colecaoPessoas.document(String.valueOf(novaPessoa.getCpf())).set(novaPessoa);

        return new OutputNovaPessoaDto(novaPessoa.getCpf(), novaPessoa.getDataCadastro());
    }

    @Override
    public List<PessoaDto> buscaPessoa() throws InterruptedException, ExecutionException {
        List<PessoaDto> list = new ArrayList<>();
        var pessoas = firebaseService.getFirestore().collection(COL_NAME);
        var querySnapshot = pessoas.get();

        querySnapshot.get().getDocuments().forEach(documento -> {
            list.add(documento.toObject(PessoaDto.class));
        });

        return list;
    }

    @Override
    public PessoaDto buscaPessoaPorCpf(String cpf) throws InterruptedException, ExecutionException {
        var pessoaRepository = firebaseService.getFirestore().collection(COL_NAME).document(cpf).get();
        Assert.isNull(pessoaRepository, "Não foi encontrada nenhuma pessoa com esse CPF!");

        return modelMapper.map(pessoaRepository, PessoaDto.class);
    }

    @Override
    public OutputAlterarPessoaDto alteraPessoa(String cpf) throws InterruptedException, ExecutionException {
        return new OutputAlterarPessoaDto();
    }
}
