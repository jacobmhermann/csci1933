// Complex.java
// A complex number class
// When a constructor is included, the default constructor goes away.
// It is usually a good idea to re-implement the default constructor.

public class Complex {

    private double realPart = 0;
    private double imaginaryPart = 0;

    public Complex(double a, double b) {
        realPart = a;
        imaginaryPart = b;
    }

    // accessor methods

    public void setRealPart(double value) {
        realPart = value;
    }

    public void setImaginaryPart(double value) {
        imaginaryPart = value;
    }

    public double getRealPart() {
        return realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    // operators

    public void addComplex(Complex c) {
        realPart = realPart + c.getRealPart();
        imaginaryPart = imaginaryPart + c.getImaginaryPart();
    }

    public void subtractComplex(Complex c) {
        realPart = realPart - c.getRealPart();
        imaginaryPart = imaginaryPart - c.getImaginaryPart();
    }

    public static void main(String[] args) {
        Complex test1 = new Complex(1,2);
        Complex test2 = new Complex(3,4);
        Complex test3 = new Complex(5,6);
        Complex test4 = new Complex(7,8);
        test1.addComplex(test2);
        double actual1a = test1.getRealPart();
        double actual1b = test1.getImaginaryPart();
        double expected1a = 4;
        double expected1b = 6;
        test1.subtractComplex(test2);
        double actual2a = test1.getRealPart();
        double actual2b = test1.getImaginaryPart();
        double expected2a = 1;
        double expected2b = 2;
        test2.addComplex(test3);
        double actual3a = test2.getRealPart();
        double actual3b = test2.getImaginaryPart();
        double expected3a = 8;
        double expected3b = 10;
        test2.addComplex(test4);
        double actual4a = test2.getRealPart();
        double actual4b = test2.getImaginaryPart();
        double expected4a = 15;
        double expected4b = 18;
        System.out.println("Expected = " + expected1a + " + " + expected1b + "i");
        System.out.println("Actual = " + actual1a + " + " + actual1b + "i");
        System.out.println();
        System.out.println("Expected = " + expected2a + " + " + expected2b + "i");
        System.out.println("Actual = " + actual2a + " + " + actual2b + "i");
        System.out.println();
        System.out.println("Expected = " + expected3a + " + " + expected3b + "i");
        System.out.println("Actual = " + actual3a + " + " + actual3b + "i");
        System.out.println();
        System.out.println("Expected = " + expected4a + " + " + expected4b + "i");
        System.out.println("Actual = " + actual4a + " + " + actual4b + "i");
        System.out.println();
    }

} // Complex class
