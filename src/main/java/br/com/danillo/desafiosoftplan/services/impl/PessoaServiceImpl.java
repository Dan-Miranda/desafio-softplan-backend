package br.com.danillo.desafiosoftplan.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import br.com.danillo.desafiosoftplan.dtos.PessoaDto;
import br.com.danillo.desafiosoftplan.dtos.InputAtualizacaoPessoaDto;
import br.com.danillo.desafiosoftplan.dtos.OutputNovaPessoaDto;
import br.com.danillo.desafiosoftplan.models.PessoaModel;
import br.com.danillo.desafiosoftplan.services.FirebaseService;
import br.com.danillo.desafiosoftplan.services.PessoaService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PessoaServiceImpl implements PessoaService {

    private final FirebaseService firebaseService;
    private final ModelMapper modelMapper;

    public static final String COL_NAME = "pessoas";
    public static final String MENSAGEM_CPF_INVALIDO = "CPF inválido";

    @Override
    public OutputNovaPessoaDto inserePessoa(PessoaDto inputNovaPessoaDto) throws InterruptedException, ExecutionException {
        Assert.isTrue(inputNovaPessoaDto.validarCpf(null), MENSAGEM_CPF_INVALIDO);
        Assert.isTrue(inputNovaPessoaDto.validarDataNascimento(), "Data de nascimento inválida");
        try {
            if (inputNovaPessoaDto.getEmail().length() > 0) {
                Assert.isTrue(inputNovaPessoaDto.validarEmail(), "Email inválido");
            }

            var pessoas = this.buscaPessoas();
            pessoas.stream().forEach(pessoa -> {
                var documentoExistente = pessoa.getCpf() != null && pessoa.getCpf().equals(inputNovaPessoaDto.getCpf()) ;
                Assert.isTrue(!documentoExistente, "Pessoa já cadastrada");
            });

            var colecaoPessoas = firebaseService.getFirestore().collection(COL_NAME);

            var hoje = new Date();
            var novaPessoa = modelMapper.map(inputNovaPessoaDto, PessoaModel.class);
            novaPessoa.setDataAtualizacao(hoje);
            novaPessoa.setDataCadastro(hoje);
            colecaoPessoas.document(novaPessoa.getCpf()).set(novaPessoa);

            return new OutputNovaPessoaDto(novaPessoa.getCpf(), novaPessoa.getDataCadastro());
        } catch (RuntimeException erro) {
            throw new RuntimeException("Erro ao cadastrar nova pessoa", erro);
        }

    }

    @Override
    public List<PessoaDto> buscaPessoas() throws InterruptedException, ExecutionException {
        List<PessoaDto> listaPessoasDto = new ArrayList<>();
        try {
            var pessoas = firebaseService.getFirestore().collection(COL_NAME);
            var querySnapshot = pessoas.get();

            querySnapshot.get().getDocuments().forEach(documento -> {
                listaPessoasDto.add(documento.toObject(PessoaDto.class));
            });

            return listaPessoasDto;
        } catch (RuntimeException erro) {
            throw new RuntimeException("Erro ao buscar pessoas", erro);
        }
    }

    @Override
    public PessoaDto buscaPessoaPorCpf(String cpf) throws InterruptedException, ExecutionException {
        var pessoaDto = new PessoaDto();
        var isCpfVaildo = pessoaDto.validarCpf(cpf);
        Assert.isTrue(isCpfVaildo, MENSAGEM_CPF_INVALIDO);
        try {

            var colecaoPessoas = firebaseService.getFirestore().collection(COL_NAME);
            var pessoaRepository = colecaoPessoas.document(cpf).get().get();
            pessoaDto = pessoaRepository.toObject(PessoaDto.class);
            Assert.notNull(pessoaDto, "Não foi encontrada nenhuma pessoa com esse CPF!");

            return pessoaDto;

        } catch (RuntimeException erro) {
            throw new RuntimeException("Erro ao buscar pessoa por CPF", erro);
        }
    }

    @Override
    public void atualizaPessoa(String cpf, InputAtualizacaoPessoaDto inputAtualizacaoPessoaDto)
        throws InterruptedException, ExecutionException {
        var pessoaDto = new PessoaDto();
        var isCpfVaildo = pessoaDto.validarCpf(cpf);
        Assert.isTrue(isCpfVaildo, MENSAGEM_CPF_INVALIDO);
        try {
            pessoaDto = this.buscaPessoaPorCpf(cpf);

            if (inputAtualizacaoPessoaDto.getNome() != null) {
                pessoaDto.setNome(inputAtualizacaoPessoaDto.getNome().get());
            }
            if (inputAtualizacaoPessoaDto.getEmail() != null) {
                pessoaDto.setEmail(inputAtualizacaoPessoaDto.getEmail().get());
            }
            if (inputAtualizacaoPessoaDto.getSexo() != null) {
                pessoaDto.setSexo(inputAtualizacaoPessoaDto.getSexo().get());
            }
            if (inputAtualizacaoPessoaDto.getNacionalidade() != null) {
                pessoaDto.setNacionalidade(inputAtualizacaoPessoaDto.getNacionalidade().get());
            }

            var atualizaPessoaModel = modelMapper.map(pessoaDto, PessoaModel.class);
            atualizaPessoaModel.setDataAtualizacao(new Date());

            var colecaoPessoas = firebaseService.getFirestore().collection(COL_NAME);
            colecaoPessoas.document(cpf).set(atualizaPessoaModel);
        } catch (RuntimeException erro) {
            throw new RuntimeException("Erro ao atualizar pessoa", erro);
        }
    }

    @Override
    public void deletaPessoa(String cpf) throws InterruptedException, ExecutionException {
        var pessoaDto = new PessoaDto();
        var isCpfVaildo = pessoaDto.validarCpf(cpf);
        Assert.isTrue(isCpfVaildo, MENSAGEM_CPF_INVALIDO);
        try {
            pessoaDto = this.buscaPessoaPorCpf(cpf);
            Assert.notNull(pessoaDto, "Não foi encontrada nenhuma pessoa com esse CPF.");
            var colecaoPessoas = firebaseService.getFirestore().collection(COL_NAME);
            colecaoPessoas.document(cpf).delete();
        } catch(RuntimeException erro) {
            throw new RuntimeException("Erro ao deletar pessoa", erro);
        }
    }
}
