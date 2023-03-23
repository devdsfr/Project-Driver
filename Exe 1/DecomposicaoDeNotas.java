//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Scanner;

public class DecomposicaoDeNotas {
    public DecomposicaoDeNotas() {
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] notas = new int[]{200, 100, 50, 20, 10, 5, 2, 1};
        int[] qtd_notas_arr = new int[notas.length];
        System.out.print("Digite o valor a ser decomposto: ");
        int valor = sc.nextInt();

        int i;
        for(i = 0; i < notas.length; ++i) {
            int qtd_notas = valor / notas[i];
            qtd_notas_arr[i] = qtd_notas;
            valor %= notas[i];
        }

        System.out.println("Notas necessárias:");

        for(i = 0; i < notas.length; ++i) {
            if (qtd_notas_arr[i] != 0) {
                System.out.printf("%d nota(s) de R$ %d,00\n", qtd_notas_arr[i], notas[i]);
            }
        }

        sc.close();
    }
}
