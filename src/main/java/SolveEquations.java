public class SolveEquations {

    public static double solveCubicEquation(double a, double b, double c, double d) {
        // Approximate solution using Newton's method
        double x = 0.0;
        double prevX;
        do {
            prevX = x;
            x = prevX - (evaluateCubicEquation(a, b, c, d, prevX) / evaluateCubicDerivative(a, b, c, prevX));
        } while (Math.abs(x - prevX) > 0.0001); // Tolerance for convergence
        return x;
    }

    public static double evaluateCubicEquation(double a, double b, double c, double d, double x) {
        return a * x * x * x + b * x * x + c * x + d;
    }

    public static double evaluateCubicDerivative(double a, double b, double c, double x) {
        return 3 * a * x * x + 2 * b * x + c;
    }
}
