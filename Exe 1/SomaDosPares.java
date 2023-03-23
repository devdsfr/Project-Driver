import java.util.Scanner;

public class SomaDosPares {
    public static void main(String[] args) {
        System.out.println("Informe primerio o tamanho do array e em seguida os seus respectivos valores: ");

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // tamanho da sequência
        int[] sequencia = new int[n]; // cria um array para armazenar a sequência

        for (int i = 0; i < n; i++) {
            sequencia[i] = sc.nextInt(); // preenche o array com os números da sequência
        }

        int soma_pares = 0;

        for (int num : sequencia) {
            if (num % 2 == 0) {
                soma_pares += num;
            }
        }

        System.out.println("A soma dos números pares na sequência é: " + soma_pares);

        sc.close();
    }
}
