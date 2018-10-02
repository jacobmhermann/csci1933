public class Rational {

    double num;
    double denom;

    public Rational(double a, double b)
    {
        num = a;
        denom = b;
    }
    public double getNumerator()
    {
        return num;
    }
    public double getDenominator()
    {
        return denom;
    }
    public void addRational(Rational r)
    {
        num = num * r.getDenominator() + r.getNumerator() * denom;
        denom = denom * r.getDenominator();
    }
    public void subRational(Rational r)
    {
        num = num * r.getDenominator() - r.getNumerator() * denom;
        denom = denom * r.getDenominator();
    }
    public void mulRational(Rational r)
    {
        num = num * r.getNumerator();
        denom = denom * r.getDenominator();
    }
    public void divRational(Rational r)
    {
        num = num * r.getDenominator();
        denom = denom * r.getNumerator();
    }

    public static void main(String[] args)
    {
        Rational test1 = new Rational(1,1);
        Rational test2 = new Rational(1, 2);
        Rational test3 = new Rational(1, 3);
        Rational test4 = new Rational(1, 4);
        test1.addRational(test2);
        System.out.println("Expected = 3,2");
        System.out.println(("actual = " + test1.getNumerator() + ", " + test1.getDenominator()));
        test1.subRational(test2);
        System.out.println("Expected = 4,4");
        System.out.println(("actual = " + test1.getNumerator() + ", " + test1.getDenominator()));
        test2.mulRational(test3);
        System.out.println("Expected = 1,6");
        System.out.println(("actual = " + test2.getNumerator() + ", " + test2.getDenominator()));
        test4.divRational(test3);
        System.out.println("Expected = 3,4");
        System.out.println(("actual = " + test4.getNumerator() + ", " + test4.getDenominator()));
    }
}
