package com.rodrigor.java.exemplos.sca_interface;

import java.util.Scanner;

public class SCA {

    private GravadorAluno gravador;

    public SCA(GravadorAluno gravador) {
        this.gravador = gravador;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer de entrada

            switch (opcao) {
                case 1:
                    cadastrarAluno(scanner);
                    break;
                case 2:
                    listarAlunos();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }

    private void exibirMenu() {
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Cadastrar aluno");
        System.out.println("2 - Listar alunos cadastrados");
        System.out.println("0 - Sair");
    }

    private void cadastrarAluno(Scanner scanner) {
        System.out.println("Digite o nome do aluno:");
        String nome = scanner.nextLine();
        System.out.println("Digite a matrícula do aluno:");
        String matricula = scanner.nextLine();

        Aluno aluno = new Aluno(nome, matricula);
        gravador.salvarAluno(aluno);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    private void listarAlunos() {
        System.out.println("Lista de alunos cadastrados:");
        for (Aluno aluno : gravador.recuperarAlunos()) {
            System.out.println("Nome: " + aluno.getNome() + ", Matrícula: " + aluno.getMatricula());
        }
    }

    public static void main(String[] args) {
        GravadorAluno gravador = new GravadorJSON(); // Utilizando GravadorJSON como instância do GravadorAluno
        SCA sca = new SCA(gravador);
        sca.iniciar();
    }
}

