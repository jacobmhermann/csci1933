public class GPA {


    public static double getGPANumber(String c) {

        double number = 0;

        if (c.equals("a")) {
            number = 4.0;
        } else if (c.equals("a-")) {
            number = 3.667;
        } else if (c.equals("b+")) {
            number = 3.333;
        } else if (c.equals("b")) {
            number = 3.0;
        } else if (c.equals("b-")) {
            number = 2.667;
        } else if (c.equals("c+")) {
            number = 2.333;
        } else if (c.equals("c")) {
            number = 2.0;
        } else if (c.equals("c-")) {
            number = 1.667;
        } else if (c.equals("d+")) {
            number = 1.333;
        } else if (c.equals("d")) {
            number = 1.0;
        } else if (c.equals("f")) {
            number = 0;
        } else if (c.equals(" ")) {
            number = 100;
        } else {
            System.out.println("Error in grades entered");
            System.exit(1);
        }
        return number;
    }

    public static void main(String[] args) {
        double sumGrades = 0;
        double sumCredits = 0;

        for (int i=0; i < args.length; i += 2) {
            double gradeNum = getGPANumber(args[i]);
            double credit = Double.parseDouble(args[i+1]);
            double classGPA = gradeNum * credit;
            sumGrades += classGPA;
            sumCredits += credit;
        }
        double totalGPA = sumGrades/sumCredits;
        String GPAString = String.format("%.3f",totalGPA);
        System.out.println("The GPA is: " + GPAString);
        System.out.println();
    }

}
