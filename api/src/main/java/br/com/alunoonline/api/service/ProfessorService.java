package br.com.alunoonline.api.service;


import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;

    //1- CREATE - cria novo professor no banco
    public void criarProfessor(Professor professor) {
        //INSERT INTO professor
        professorRepository.save(professor);
    }

    //2- READ - busca professor pelo id, devolve optional(podendo ser vazio)
    public Optional<Professor> buscarProfessorPorId(Long id){
        //SELECT FROM professor WHERE id = ?
        return professorRepository.findById(id);
    }

    //3- READ - retorna todos os professores cadastrados no banco
    public List<Professor> listarTodosOsProfessores() {
        //SELECT * FROM PROFESSOR
        return professorRepository.findAll();
    }

    //4- UPDATE - atualiza dados de um professor existente pelo id
    public void atualizarProfessorPorId(Long id, Professor professor){

        //verifica se tem professor antes de editar
        Optional<Professor> professorDoBanco = buscarProfessorPorId(id);
        if (professorDoBanco.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado.");
        }

        //pega objeto gerenciado pelo JPA e atualiza os campos
        Professor professorParaEditar = professorDoBanco.get();
        professorParaEditar.setNome(professor.getNome());
        professorParaEditar.setCpf(professor.getCpf());
        professorParaEditar.setEmail(professor.getEmail());

        //salva as alterações - UPDATE INTO professor
        professorRepository.save(professorParaEditar);

    }

    //5- REMOVE - remove um professor pelo ID
    public void deletarProfessor(Long id){

        //verifica se tem professor antes de deletar
        Optional<Professor> professorDoBanco = buscarProfessorPorId(id);
        if (professorDoBanco.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado para exclusão");
        }

        //deleta usando o ID - DELETE FROM PROFESSOR WHERE id = ?
        professorRepository.deleteById(id);

    }



}
