public class Fibonacci {
    public static int fib(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            int a = 0;
            int b = 1;
            for (int i = 2; i <= n; i++) {
                int c = a + b;
                a = b;
                b = c;
            }
            return b;
        }
    }

    public static void main(String[] args) {
        // Testes dos resultados dados no enunciado
        assert fib(0) == 0;
        assert fib(1) == 1;
        assert fib(2) == 1;
        assert fib(3) == 2;
        assert fib(4) == 3;
        assert fib(5) == 5;
        assert fib(6) == 8;
    }
}
