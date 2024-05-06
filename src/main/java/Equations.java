import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

public class Equations {
    private static final Random random = new Random();
    private static final DecimalFormat df = new DecimalFormat("#.00"); // Pattern for always two decimal places
    private static final HashMap<Integer, String> equationsMap = new HashMap<>();

    static {
        // Populate the equations map with equationNumber and corresponding equation
        for (int i = 1; i <= 131; i++) {
            equationsMap.put(i, generateRandomEquation());
        }
    }

    public static String getEquation(int equationNumber) {
        return equationsMap.getOrDefault(equationNumber, "No equation found for " + equationNumber);
    }

    public static double getAnswer(int equationNumber) {
        String equation = equationsMap.getOrDefault(equationNumber, "No equation found for " + equationNumber);
        if (equation.contains("x^2")) {
            return solveQuadraticEquation(equation);
        } else if (equation.contains("x")) {
            return solveLinearEquation(equation);
        } else {
            throw new IllegalArgumentException("Unsupported equation type: " + equation);
        }
    }

    private static String generateRandomEquation() {
        int equationType = random.nextInt(2); // 0 for linear, 1 for quadratic
        return equationType == 0 ? generateLinearEquation() : generateQuadraticEquation();
    }

    private static String generateLinearEquation() {
        int coefX = random.nextInt(10) + 1; // Random coefficient for x (1 to 10)
        int constant = random.nextInt(20) - 10; // Random constant (-10 to 10)
        return coefX + "x + " + constant + " = 0";
    }

    private static String generateQuadraticEquation() {
        int a = random.nextInt(5) + 1; // Random coefficient for x^2 (1 to 5)
        int b = random.nextInt(10) - 5; // Random coefficient for x (-5 to 5)
        int c = random.nextInt(10) - 5; // Random constant (-5 to 5)
        return a + "x^2 + " + b + "x + " + c + " = 0";
    }

    private static double solveLinearEquation(String equation) {
        String[] parts = equation.split(" ");
        double coefX = Double.parseDouble(parts[0].replace("x", ""));
        double constant = Double.parseDouble(parts[2]);
        return Double.parseDouble(df.format(-constant / coefX)); // Round off to always two decimal places
    }

    private static double solveQuadraticEquation(String equation) {
        String[] parts = equation.split(" ");
        double a = Double.parseDouble(parts[0].replace("x^2", ""));
        double b = Double.parseDouble(parts[2].replace("x", ""));
        double c = Double.parseDouble(parts[4]);

        double discriminant = b * b - 4 * a * c;
        if (discriminant >= 0) {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return Double.parseDouble(df.format(Math.max(root1, root2))); // Round off to always two decimal places
        } else {
            throw new IllegalArgumentException("No real roots for the quadratic equation.");
        }
    }

   /* public static void main(String[] args) {
        int equationNumber = random.nextInt(131) + 1; // Random equation number from 1 to 131

        String equation = Equations.getEquation(equationNumber);
        System.out.println("Equation " + equationNumber + ": " + equation);

        double answer = Equations.getAnswer(equationNumber);
        System.out.println("Solution: " + answer);
    }*/
}
