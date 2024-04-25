public class Equations {
    public static String getEquation(int number) {
        switch (number) {
            case 1:
                return "3x + 5 = 11";
            case 2:
                return "2x^2 - 7x + 3 = 0";
            case 3:
                return "x/4 + 2 = 5";
            case 4:
                return "5x - 8 = 22";
            case 5:
                return "(2x + 1)/3 = 4";
            case 6:
                return "3x^3 + 5x^2 - 2x + 1 = 0";
            case 7:
                return "x^2/9 = 16";
            case 8:
                return "sqrt(x) + 7 = 11";
            default:
                return "No equation found for " + number;
        }
    }

    public static void equationAnswers() {
        // Equation 1: 3x + 5 = 11
        double x1 = (11 - 5) / 3.0;

        // Equation 2: 2x^2 - 7x + 3 = 0
        double a = 2, b = -7, c = 3;
        double discriminant = b * b - 4 * a * c;
        double x2_1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double x2_2 = (-b - Math.sqrt(discriminant)) / (2 * a);

        // Equation 3: x/4 + 2 = 5
        double x3 = (5 - 2) * 4;


        // Equation 4: 5x - 8 = 22
        double x4 = (22 + 8) / 5.0;

        // Equation 5: (2x + 1)/3 = 4
        double x5 = (4 * 3) - 1 / 2.0;

        // Equation 6: 3x^3 + 5x^2 - 2x + 1 = 0
        double x6 = SolveEquations.solveCubicEquation(3, 5, -2, 1);

        // Equation 7: x^2/9 = 16
        double x7_1 = Math.sqrt(16 * 9);
        double x7_2 = -x7_1;

        // Equation 8: sqrt(x) + 7 = 11
        double x8 = (11 - 7) * (11 - 7);
    }
}

