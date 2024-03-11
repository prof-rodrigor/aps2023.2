package com.rodrigor.java.exemplos.sca_interface;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class GravadorXML implements GravadorAluno {

    private final String arquivoXML;

    public GravadorXML(String arquivoXML) {
        this.arquivoXML = arquivoXML;
    }

    @Override
    public void salvarAluno(Aluno aluno) {
        List<Aluno> alunos = recuperarAlunos();
        alunos.add(aluno);
        salvarListaAlunos(alunos);
    }

    @Override
    public List<Aluno> recuperarAlunos() {
        try {
            if (!Files.exists(Paths.get(arquivoXML))) {
                return new ArrayList<>();
            }
            JAXBContext context = JAXBContext.newInstance(Alunos.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Alunos alunos = (Alunos) unmarshaller.unmarshal(new File(arquivoXML));
            return alunos.getLista();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void salvarListaAlunos(List<Aluno> alunos) {
        try {
            JAXBContext context = JAXBContext.newInstance(Alunos.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(new Alunos(alunos), new FileWriter(arquivoXML));
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    private static class Alunos {
        private List<Aluno> lista;

        public Alunos() {
            this.lista = new ArrayList<>();
        }

        public Alunos(List<Aluno> lista) {
            this.lista = lista;
        }

        public List<Aluno> getLista() {
            return lista;
        }

        public void setLista(List<Aluno> lista) {
            this.lista = lista;
        }
    }
}
