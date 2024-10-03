package menu;

import java.util.Scanner;

import static menu.ConsoleColors.*;
public class MenuDeJogos {
    public static void main(String[] args) {
        int opcao;
        do{
            opcao = menuJogos();
            rodarJogo(opcao);
        } while(opcao != 3);
    }
    public static int menuJogos(){
        int cod;
        Boolean check;
        Scanner scan = new Scanner(System.in);
        do{
            check = true;
            System.out.println("\n--- MENU DE JOGOS ---\n");
            System.out.println("1) Jogo da velha\n" +
                    "2) Sudoku\n" +
                    "3) Sair\n");
            cod = scan.nextInt();
            if(cod < 1 || cod > 3){
                System.out.println("Opção inválida. Insira um número inteiro de 1 a 3.\n");
                check = false;
            }
        } while(!check);
        return cod;
    }
    public static void rodarJogo(int cod){

        switch (cod){
            case 1:
                jogoDaVelha();
                break;
            case 2:
                sudoku();
                break;
            case 3:
                System.out.println("\nVocê optou por sair.\n" +
                        "Até a próxima! Obrigada por jogar conosco :)");
        }
    }
    public static void jogoDaVelha(){
        int n = 1;
        String[][] tabuleiro = new String[3][3];
        String[] pecas =  new String[2];
        Boolean fimJogo = false;

        System.out.println("\n--- Jogo da velha ---\n");
        quemComecaJV(pecas);
        construirTabuleiroJV(tabuleiro);

        while(!fimJogo){
            int i = 0;
            do{
                efetuarJogadaJV(pecas[i], tabuleiro, n);
                construirTabuleiroJV(tabuleiro);
                fimJogo = verificarVencedorJV(tabuleiro,fimJogo);
                i++;

            } while (i < pecas.length && !fimJogo);
            n++;
        }
    }
    public static void construirTabuleiroJV(String[][] matriz){
        for (int i = 0; i < matriz.length; i++) {
            System.out.println("-------------------");
            for (int j = 0; j < matriz[i].length; j++) {
                if(matriz[i][j] ==  null){
                    System.out.print("|     ");
                } else{
                    System.out.print("|  " + matriz[i][j] + "  ");
                }
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-------------------");
    }
    public static void quemComecaJV(String[] pecas){
        String primeiro;
        Scanner scan = new Scanner(System.in);
        Boolean check;
        do{
            check = false;
            System.out.println("Quem começa, 'X' ou 'O'?");
            primeiro = scan.nextLine().toUpperCase();

            switch (primeiro){
                case "X":
                    pecas[0] = "X";
                    pecas[1] = "O";
                    break;
                case "O":
                    pecas[0] = "O";
                    pecas[1] = "X";
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente inserindo 'X' ou 'O'.\n");
                    check = true;
            }
        } while(check);

        System.out.println("A peca " + pecas[0] + " comeca o jogo.\n");
    }
    public static void efetuarJogadaJV(String peca, String[][] matriz, int n){
        int l, c;
        Boolean val = true;
        Scanner scan = new Scanner(System.in);
        do{
            System.out.println(n + "ª jogada da peca " + peca + " -> Escolha a linha e a coluna (0 a 2): ");
            l = scan.nextInt();
            c = scan.nextInt();
            val = validarJogadaJV(matriz, l, c);
            if(val){
                matriz[l][c] = peca;
            }
        }while(!val);
    }
    public static Boolean validarJogadaJV(String[][] matriz, int l, int c){ // validar também para os números estarem no intervalo de 0 e 2
        Boolean check;

        if(!(l < 0 || l > 2 || c < 0 || c > 2)){
            if(matriz[l][c] == null){
                check = true;
            } else {
                check = false;
                System.out.println(ConsoleColors.RED + "Jogada inválida. Esta casa já está ocupada. Tente novamente!\n" + ConsoleColors.RESET);
            }
        } else{
            System.out.println(ConsoleColors.RED + "Jogada inválida. Insira um número inteiro de 0 a 2.\n" + ConsoleColors.RESET);
            check = false;
        }
        return check;
    }
    public static Boolean verificarVencedorJV(String[][] matriz, Boolean fimJogo){
        String vencedor = "";
        Boolean empate = true;

        for (int i = 0; i < matriz.length; i++) {
            if(matriz[i][0] == matriz[i][1] && matriz[i][1] == matriz[i][2] && matriz[i][0] != null){
                vencedor = matriz[i][0];
                fimJogo = true;
            } else if (matriz[0][i] == matriz[1][i] && matriz[1][i] == matriz[2][i] && matriz[0][i] != null){
                vencedor = matriz[0][i];
                fimJogo = true;
            }
        }

        if(matriz[0][0] == matriz[1][1] && matriz[1][1] == matriz[2][2] && matriz[1][1] != null){
            vencedor = matriz[1][1];
            fimJogo = true;
        } else if (matriz[0][2] == matriz[1][1] && matriz[1][1] == matriz[2][0] && matriz[1][1] != null) {
            vencedor = matriz[1][1];
            fimJogo = true;
        }

        if(fimJogo){
            System.out.println(ConsoleColors.GREEN + "Fim do jogo! A peca '" + vencedor + "' ganhou!" + ConsoleColors.RESET);
            empate = false;

        } else {
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[i].length; j++) {
                    if(matriz[i][j] == null) {
                        empate = false;
                    }
                }
            }
        }

        if(empate){
            fimJogo = true;
            System.out.println("Houve empate. Sem vencedor nessa rodada!");
        }

        return fimJogo;
    }
    public static void sudoku(){
        int[][] subGrade00 = new int[3][3], subGrade01 = new int[3][3], subGrade02 = new int[3][3];
        int[][] subGrade10 = new int[3][3], subGrade11 = new int[3][3], subGrade12 = new int[3][3];
        int[][] subGrade20 = new int[3][3], subGrade21 = new int[3][3], subGrade22 = new int[3][3];
        int[][][][] grade = {{subGrade00, subGrade01, subGrade02}, {subGrade10, subGrade11, subGrade12}, {subGrade20, subGrade21, subGrade22}};
        int nivel;
        Boolean fimJogo = false;

        nivel = escolherNivel();
        preencherGradeInicial(grade, nivel);
        exibirTabuleiro(grade);

        while(!fimJogo){
            efetuarJogada(grade);
            exibirTabuleiro(grade);
            fimJogo = verificarFim(grade);
        }
    }
    public static int escolherNivel(){
        int cod;
        Boolean check;
        Scanner scan = new Scanner(System.in);
        do{
            check = true;
            System.out.println("\n         --- SUDOKU ---\n");
            System.out.println("Selecione o nível que deseja jogar: ");
            System.out.println("1 - Iniciante\n" +
                    "2 - Intermediário\n" +
                    "3 - Avançado\n");
            cod = scan.nextInt();

            if(cod < 1 || cod > 3){
                System.out.println("Nível inválido. Insira um número inteiro de 1 a 3.");
                check = false;
            }
        } while(!check);

        return cod;
    }
    public static void preencherGradeInicial(int[][][][] grade, int cod){

        switch (cod){
            case 1:
                grade[0][0][0][0] = 5;
                grade[0][0][0][1] = 3;
                grade[0][1][0][0] = 6;
                grade[0][2][0][1] = 9;
                grade[0][2][0][2] = 8;

                grade[0][0][1][1] = 7;
                grade[0][1][1][0] = 1;
                grade[0][1][1][1] = 9;
                grade[0][1][1][2] = 5;

                grade[0][2][2][1] = 6;

                grade[1][0][0][0] = 8;
                grade[1][1][0][0] = 4;
                grade[1][2][0][0] = 7;

                grade[1][0][1][1] = 6;
                grade[1][1][1][0] = 8;
                grade[1][1][1][2] = 3;
                grade[1][2][1][1] = 2;

                grade[1][0][2][2] = 3;
                grade[1][1][2][2] = 1;
                grade[1][2][2][2] = 6;

                grade[2][0][0][1] = 6;

                grade[2][1][1][0] = 4;
                grade[2][1][1][1] = 1;
                grade[2][1][1][2] = 9;
                grade[2][2][1][1] = 8;

                grade[2][0][2][0] = 2;
                grade[2][0][2][1] = 8;
                grade[2][1][2][2] = 5;
                grade[2][2][2][1] = 7;
                grade[2][2][2][2] = 9;

                System.out.println("Nível: Iniciante");
                break;

            case 2:
                grade[0][2][0][1] = 8;

                grade[0][0][1][2] = 3;
                grade[0][1][1][0] = 7;
                grade[0][1][1][2] = 2;
                grade[0][2][1][0] = 9;

                grade[0][0][2][0] = 9;
                grade[0][2][2][0] = 5;
                grade[0][2][2][1] = 4;

                grade[1][0][0][1] = 1;
                grade[1][1][0][0] = 4;
                grade[1][1][0][1] = 3;

                grade[1][1][2][1] = 6;
                grade[1][1][2][2] = 7;
                grade[1][2][2][1] = 5;

                grade[2][0][0][1] = 4;
                grade[2][0][0][2] = 7;
                grade[2][2][0][2] = 9;

                grade[2][0][1][2] = 6;
                grade[2][1][1][0] = 2;
                grade[2][1][1][2] = 9;
                grade[2][2][1][0] = 8;

                grade[2][0][2][1] = 3;

                System.out.println("Nível: Intermediário");
                break;

            case 3:
                grade[0][1][0][1] = 6;
                grade[0][2][0][1] = 1;
                grade[0][2][0][2] = 3;

                grade[0][1][1][2] = 9;
                grade[0][2][1][1] = 5;

                grade[0][1][2][0] = 7;
                grade[0][1][2][1] = 2;
                grade[0][2][2][0] = 8;

                grade[1][0][0][1] = 8;
                grade[1][1][0][2] = 7;

                grade[1][0][1][0] = 2;
                grade[1][0][1][2] = 6;
                grade[1][2][1][0] = 1;
                grade[1][2][1][2] = 4;

                grade[1][1][2][0] = 3;
                grade[1][2][2][1] = 5;

                grade[2][0][0][2] = 5;
                grade[2][1][0][1] = 2;
                grade[2][1][0][2] = 9;

                grade[2][0][1][1] = 8;
                grade[2][1][1][0] = 6;

                grade[2][0][2][0] = 9;
                grade[2][0][2][1] = 4;
                grade[2][1][2][1] = 1;

                System.out.println("Nível: Avançado");
                break;
        }
    }
    public static void exibirTabuleiro(int[][][][] grade){
        System.out.println("\n        --- SUDOKU ---");
        System.out.println(" -----------------------------");
        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                System.out.print("|");
                for (int k = 0; k < grade[i][j].length; k++) {
                    for (int l = 0; l < grade[i][j][k].length; l++) {
                        if(grade[i][j][k][l] == 0){
                            System.out.print("   ");
                        }else{
                            System.out.print(" " + ConsoleColors.WHITE_BOLD_BRIGHT + grade[i][j][k][l] + " ");
                        }
                    }
                    System.out.print("|");
                }
                System.out.println();
            }
            System.out.println(" -----------------------------");
        }
    }
    public static void exibirTabuleiroComErro(int[][][][] grade,  int L, int l, int C, int c, int n){
        System.out.println("\n        --- SUDOKU ---");
        System.out.println(" -----------------------------");
        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                System.out.print("|");
                for (int k = 0; k < grade[i][j].length; k++) {
                    for (int w = 0; w < grade[i][j][k].length; w++) {
                        if(i == L && j == l && k == C && w == c){
                            System.out.print(" " + ConsoleColors.RED_BOLD_BRIGHT + n + " " + ConsoleColors.RESET);
                        }else if(grade[i][j][k][w] == 0){
                            System.out.print("   ");
                        }else{
                            System.out.print(" " + ConsoleColors.WHITE_BOLD_BRIGHT +grade[i][j][k][w] + " ");
                        }
                    }
                    System.out.print("|");
                }
                System.out.println();
            }
            System.out.println(" -----------------------------");
        }
    }
    public static void efetuarJogada(int[][][][] grade){ // TRATAR ERROS!
        int n, L, C, l, c;
        Boolean val, check;
        Scanner scan = new Scanner(System.in);
        do{
            check = true;
            System.out.println("\nDefina a posição do número inserindo os índices (0 a 2): \n");
            System.out.println("Linha da grade principal:");
            L = scan.nextInt();
            System.out.println("Coluna da grade principal:");
            C = scan.nextInt();
            System.out.println("Linha da subgrade:");
            l = scan.nextInt();
            System.out.println("Coluna da subgrade:");
            c = scan.nextInt();

            System.out.println("Insira o número que deseja acrescentar no tabuleiro:");
            n = scan.nextInt();

            if(L < 0 || l < 0 || C < 0 || c < 0 || L > 2 || l > 2 || C > 2 || c > 2){
                System.out.println(ConsoleColors.RED + "Índices inválidos. Insira somente um número inteiro de 0 a 2." + ConsoleColors.RESET);
                check = false;
            }
            if(n<1 || n > 9){
                System.out.println(ConsoleColors.RED + "Número a acrescentar inválido. Deve ser um inteiro de 1 a 9." + ConsoleColors.RESET);
                check = false;
            }
        }while(!check);

        val = validarJogada(n, L, C, l, c, grade);

        if(val){
            grade[L][l][C][c] = n;
        }
    }
    public static Boolean validarJogada(int n, int L, int C, int l, int c, int[][][][] grade){
        Boolean check, check1 = true, check2;

        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                if(grade[L][l][i][j] == n || grade[i][j][C][c] == n || grade[L][i][C][j] == n){
                    check1 = false;
                }
            }
        }

        if(grade[L][l][C][c] == 0){
            check2 = true;
        } else {
            check2 = false;
        }

        if(!check2){
            System.out.println(ConsoleColors.RED_BOLD + "\nJOGADA INVÁLIDA! Essa casa já está ocupada!" + ConsoleColors.RESET);
        } else if (!check1) {
            exibirTabuleiroComErro(grade, L, l, C, c, n);
            System.out.println(ConsoleColors.RED_BOLD +"\nJOGADA INVÁLIDA! Esse número não pode ficar nessa casa!" + ConsoleColors.RESET);
        }

        check = check1 && check2;
        return check;
    }
    public static Boolean verificarFim(int[][][][] grade){
        Boolean fimJogo = true;

        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                for (int k = 0; k < grade[i][j].length; k++) {
                    for (int l = 0; l < grade[i][j][k].length; l++) {
                        if(grade[i][j][k][l] == 0){
                            fimJogo = false;
                        }
                    }
                }
            }
        }
        if(fimJogo){
            System.out.println(ConsoleColors.GREEN + "\nPARABÉNS! Você completou o desafio!" + ConsoleColors.RESET);
        }
        return fimJogo;
    }
}

