package com.rodrigor.java.exemplos.sca_interface;

import java.util.List;

public interface GravadorAluno {

    void salvarAluno(Aluno aluno);
    List<Aluno> recuperarAlunos();
}
