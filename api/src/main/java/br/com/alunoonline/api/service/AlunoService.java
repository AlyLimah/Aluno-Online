package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service //classe service do spring
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    //CREATE - adicionar aluno
    public void criarAluno(Aluno aluno){

        alunoRepository.save(aluno); //INSERT into aluno
    }

    //READ - listar todos
    public List<Aluno> listarTodosAlunos(){
        return alunoRepository.findAll(); //SELECT * FROM aluno
    }

    //READ - buscar por aluno
    public Optional<Aluno> buscarAlunoPorId(Long id){
        return alunoRepository.findById(id);
    }

    //UPDATE - atualizar por ID
    public void atualizarAlunoPorId(Long id, Aluno aluno){
        Optional<Aluno> alunoDoBanco = buscarAlunoPorId(id);

        if (alunoDoBanco.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }

        Aluno alunoParaEditar = alunoDoBanco.get();
        alunoParaEditar.setNome(aluno.getNome());
        alunoParaEditar.setCpf(aluno.getCpf());
        alunoParaEditar.setEmail(aluno.getEmail());

        alunoRepository.save(alunoParaEditar);
    }
    public void deletarAluno(Long id){
        Optional<Aluno> alunoDoBanco = buscarAlunoPorId(id);

        if (alunoDoBanco.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Aluno não encontrado para exclusão");
        }
        alunoRepository.deleteById(id);

    }

}
