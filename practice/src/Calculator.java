public class Calculator {
    private int last = 0;

    public int add(int x, int y) {
        last = x + y;
        return last;
    }

    public int subtract(int x, int y) {
        last = x - y;
        return last;
    }

    public int multiply(int x, int y) {
        last = x * y;
        return last;
    }

    public int divide(int x, int y) {
        last = x / y;
        return last;
    }

    public int addToLast(int x) {
        last += x;
        return last;
    }

    public int getLast() {
        return last;
    }
}
