public class Fibonacci {

    public static int fibonacciRecursive(int n) {
        if (n == 0) { return 0; }
        if (n == 1) { return 1; }
        else { return fibonacciRecursive(n-1) + fibonacciRecursive(n-2); }
    }

    public static void main(String[] args) { System.out.println(fibonacciRecursive(10)); }
}
