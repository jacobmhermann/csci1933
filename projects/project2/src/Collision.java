public class Collision {

    /* This extra class creates an object that stores information for a collision. In BallScreenSaver, this class
    * is used to store the most recent collision. Since every collision is assumed to be elastic, the Kinetic Energy
    * (scalar) and Momentum (vector) should be maintained. When the space bar is pressed, the terminal prints the
    * Kinetic Energy and Momentum before and after the last collision, which is used to prove that each remained
    * constant and, therefore, the collision is elastic.
    */

    private double Ki, Kf;
    private double[] Pi, Pf;

    public Collision(Ball one, Ball two) {
        // constructs Collision object and sets pre-collision values

        double x1i = one.getSpeedX();
        double y1i = one.getSpeedY();
        double velo1i = Math.sqrt(Math.pow(x1i,2) + Math.pow(y1i,2));

        double x2i = two.getSpeedX();
        double y2i = two.getSpeedY();
        double velo2i = Math.sqrt(Math.pow(x2i,2) + Math.pow(y2i,2));

        Ki = calculateK(velo1i,velo2i);
        Pi = calculateP(x1i,y1i,x2i,y2i);
    }

    public void updateFinalValues(Ball one, Ball two) {
        // updates the post-collision values

        double x1f = one.getSpeedX();
        double y1f = one.getSpeedY();
        double velo1f = Math.sqrt(Math.pow(x1f,2) + Math.pow(y1f,2));

        double x2f = two.getSpeedX();
        double y2f = two.getSpeedY();
        double velo2f = Math.sqrt(Math.pow(x2f,2) + Math.pow(y2f,2));

        Kf = calculateK(velo1f,velo2f);
        Pf = calculateP(x1f,y1f,x2f,y2f);
    }

    public double calculateK(double vel1, double vel2) {
        double K = .5 * (Math.pow(vel1,2) + Math.pow(vel2,2));
        return K;
    }

    public double[] calculateP(double x1, double y1, double x2, double y2) {
        double[] P = new double[2];
        P[0] = x1 + x2;
        P[1] = y1 + y2;
        return P;
    }

    // accessor methods

    public double getKi() { return Ki; }

    public void printPi() {System.out.print(Pi[0] + "," + Pi[1]); }

    public double getKf() { return Kf; }

    public void printPf() { System.out.print(Pf[0] + "," + Pf[1]); }

}
