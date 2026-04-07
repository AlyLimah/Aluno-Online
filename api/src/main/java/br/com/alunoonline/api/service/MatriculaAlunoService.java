package br.com.alunoonline.api.service;


import br.com.alunoonline.api.dtos.AtualizarNotasRequestDTO;
import br.com.alunoonline.api.dtos.DisciplinasAlunoResponseDTO;
import br.com.alunoonline.api.dtos.HistoricoAlunoResponseDTO;
import br.com.alunoonline.api.enums.MatriculaAlunoStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MatriculaAlunoService {

    private static final Double MEDIA_PARA_APROVACAO = 7.0;

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;

    //criar matrícula
    public void criarMatricula(MatriculaAluno matriculaAluno) {
        matriculaAluno.setStatus(MatriculaAlunoStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);

    }

    //trancar matrícula
    public void trancarMatricula(Long id) {
        MatriculaAluno matricula = matriculaAlunoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Matricula não encontrada"));

        //trancar se tiver matriculado
        if (matricula.getStatus().equals(MatriculaAlunoStatusEnum.MATRICULADO)) {
            matricula.setStatus(
                    MatriculaAlunoStatusEnum.TRANCADO);
            matriculaAlunoRepository.save(matricula);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Só é possível trancar com status MATRICULADO");
        }
    }

    //atualizar notas
    public void atualizarNotas(Long id, AtualizarNotasRequestDTO dto) {
        //busca no repositório correto e trata o Optional
        MatriculaAluno matricula = matriculaAlunoRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Aluno não encontrado"));

        if (dto.getNota1()!= null) matricula.setNota1(dto.getNota1());
        if (dto.getNota2()!= null) matricula.setNota2(dto.getNota2());

        if (matricula.getNota1() != null && matricula.getNota2() != null ){
            Double media = (matricula.getNota1() + matricula.getNota2())/ 2;
            matricula.setStatus(media >= MEDIA_PARA_APROVACAO
                               ? MatriculaAlunoStatusEnum.APROVADO
                               : MatriculaAlunoStatusEnum.REPROVADO);
        }
        matriculaAlunoRepository.save(matricula);
    }

    public HistoricoAlunoResponseDTO emitirHistorico(Long alunoId) {
        // Busca e lista todas as matrículas desse aluno em específico
        List<MatriculaAluno> matriculasDoAluno = matriculaAlunoRepository.findAlunoById(alunoId);

        if (matriculasDoAluno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma matrícula para este aluno.");
        }

        return getHistoricoAlunoResponseDTO(matriculasDoAluno);
    }
    private static HistoricoAlunoResponseDTO getHistoricoAlunoResponseDTO(List<MatriculaAluno> matriculasDoAluno) {
        HistoricoAlunoResponseDTO historico = new HistoricoAlunoResponseDTO();
        historico.setNomeAluno(matriculasDoAluno.getFirst().getAluno().getNome());
        historico.setCpfAluno(matriculasDoAluno.getFirst().getAluno().getCpf());
        historico.setEmailAluno(matriculasDoAluno.getFirst().getAluno().getEmail());
        List<DisciplinasAlunoResponseDTO> listaDisciplinas = matriculasDoAluno.stream()
        .map(matricula -> {
            DisciplinasAlunoResponseDTO item = new DisciplinasAlunoResponseDTO();
            item.setNomeDisciplina(matricula.getDisciplina().getNome());
            item.setNota1(matricula.getNota1());
            item.setNota2(matricula.getNota2());
            item.setMedia((matricula.getNota1() + matricula.getNota2() / 2)); //Opcional
            item.setStatus(matricula.getStatus());
            return item;
        }).toList();
        historico.setDisciplinas(listaDisciplinas);
        return historico;
    }
}


