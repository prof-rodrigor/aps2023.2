package com.rodrigor.java.exemplos.sca_interface;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GravadorJSON implements GravadorAluno {

    private final String arquivo = "alunos.json";
    private Gson gson;

    public GravadorJSON() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void salvarAluno(Aluno aluno) {
        List<Aluno> alunos = recuperarAlunos();
        alunos.add(aluno);
        salvarAlunos(alunos);
    }

    @Override
    public List<Aluno> recuperarAlunos() {
        try {
            List<Aluno> alunos = gson.fromJson(new FileReader(arquivo), new TypeToken<List<Aluno>>() {}.getType());
            if (alunos == null) {
                return new ArrayList<>();
            }
            return alunos;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void salvarAlunos(List<Aluno> alunos) {
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(alunos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
