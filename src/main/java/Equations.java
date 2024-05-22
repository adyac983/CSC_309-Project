import java.util.HashMap;
import java.util.Random;

public class Equations {
    private static final Random random = new Random();
    private static final HashMap<Integer, String> equationsMap = new HashMap<>();
    private static int levelChoice = 2;

    static {
        // Populate the equations map with equationNumber and corresponding equation
        for (int i = 1; i <= 131; i++) {
            if (levelChoice == 1) {
                equationsMap.put(i, generateEasyEquation());
            } else if (levelChoice == 2) {
                equationsMap.put(i, generateMidEquation());
            } else {
                equationsMap.put(i, generateHardEquation());
            }
        }
    }

    public static String getEquation(int equationNumber, int lC) {
        levelChoice = lC;
        return equationsMap.getOrDefault(equationNumber, "No equation found for " + equationNumber);
    }

    public static double getAnswer(int equationNumber, int lC) {
        String equation = equationsMap.getOrDefault(equationNumber, "No equation found for " + equationNumber);
        if (lC == 3) {
            return solveHardEquation(equation);
        } else if (lC == 2) {
            return solveMidEquation(equation);
        } else {
            return solveEasyEquation(equation);
        }
    }

    private static String generateEasyEquation() {
        int coefX = random.nextInt(10) + 1; // Random coefficient for x (1 to 10)
        int result = coefX * (random.nextInt(10) + 1); // Random result to keep x as a whole number
        return coefX + "x = " + result;
    }

    private static String generateMidEquation() {
        int coefX = random.nextInt(10) + 1; // Random coefficient for x (1 to 10)
        int result = random.nextInt(50) + 1; // Random result
        int constant = result - (coefX * (random.nextInt(10) + 1)); // Ensure x is a whole number
        return coefX + "x + " + constant + " = " + result;
    }

    private static String generateHardEquation() {
        int root1 = random.nextInt(10) + 1; // Random root1 (1 to 10)
        int root2 = random.nextInt(10) + 1; // Random root2 (1 to 10)
        int a = 1; // To ensure roots are whole numbers
        int b = - (root1 + root2);
        int c = root1 * root2;
        return a + "x^2 + " + b + "x + " + c + " = 0";
    }

    static double solveEasyEquation(String equation) {
        String[] parts = equation.split(" ");
        double coefX = Double.parseDouble(parts[0].replace("x", ""));
        double result = Double.parseDouble(parts[2]);
        return result / coefX;
    }

    static double solveMidEquation(String equation) {
        String[] parts = equation.split(" ");
        double coefX = Double.parseDouble(parts[0].replace("x", ""));
        double constant = Double.parseDouble(parts[2]);
        double result = Double.parseDouble(parts[4]);
        return (result - constant) / coefX;
    }

    static double solveHardEquation(String equation) {
        String[] parts = equation.split(" ");
        double a = Double.parseDouble(parts[0].replace("x^2", ""));
        double b = Double.parseDouble(parts[2].replace("x", ""));
        double c = Double.parseDouble(parts[4]);

        double discriminant = b * b - 4 * a * c;
        if (discriminant >= 0) {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return Math.max(root1, root2);
        } else {
            throw new IllegalArgumentException("No real roots for the quadratic equation.");
        }
    }

    public static HashMap<Integer, String> getEquationMap() {
        return equationsMap;
    }

   /* public static void main(String[] args) {
        int equationNumber = random.nextInt(131) + 1; // Random equation number from 1 to 131

        String equation = Equations.getEquation(equationNumber);
        System.out.println("Equation " + equationNumber + ": " + equation);

        double answer = Equations.getAnswer(equationNumber);
        System.out.println("Solution: " + answer);
    }*/
}
