public class Solution {
    public static String getSolution(String equation) {
        if (equation.contains("x^2")) {
            return getQuadraticSolution(equation);
        } else if (equation.contains("+") || equation.contains("-")) {
            return getLinearSolution(equation);
        } else {
            return getSimpleSolution(equation);
        }
    }

    private static String getQuadraticSolution(String equation) {
        String[] parts = equation.split(" ");
        int a = Integer.parseInt(parts[0].replace("x^2", "").trim());
        int b = Integer.parseInt(parts[2].replace("x", "").trim());
        int c = Integer.parseInt(parts[4].replace("= 0", "").trim());

        // Discriminant
        int discriminant = b * b - 4 * a * c;
        double sqrtDiscriminant = Math.sqrt(discriminant);

        // Steps for solving quadratic equation
        StringBuilder steps = new StringBuilder();
        steps.append("Step 1: Calculate the discriminant: D = b^2 - 4ac\n");
        steps.append(String.format("D = (%d)^2 - 4 * %d * %d = %d\n", b, a, c, discriminant));
        steps.append("Step 2: Calculate the roots using the quadratic formula: x = (-b ± √D) / 2a\n");

        if (discriminant > 0) {
            double root1 = (-b + sqrtDiscriminant) / (2 * a);
            double root2 = (-b - sqrtDiscriminant) / (2 * a);
            steps.append(String.format("x1 = (-%d + √%d) / 2 * %d = %.2f\n", b, discriminant, a, root1));
            steps.append(String.format("x2 = (-%d - √%d) / 2 * %d = %.2f\n", b, discriminant, a, root2));
        } else if (discriminant == 0) {
            double root = -b / (2 * a);
            steps.append(String.format("x = -%d / 2 * %d = %.2f\n", b, a, root));
        } else {
            steps.append("The equation has no real roots.\n");
        }
        return steps.toString();
    }

    private static String getLinearSolution(String equation) {
        String[] parts = equation.split(" ");
        int coefX = Integer.parseInt(parts[0].replace("x", "").trim());
        int constant = Integer.parseInt(parts[2].trim());
        String operator = parts[1];
        int result = Integer.parseInt(parts[4].replace("= 0", "").trim());

        // Steps for solving linear equation
        StringBuilder steps = new StringBuilder();
        steps.append("Step 1: Move the constant to the right side of the equation:\n");
        if (operator.equals("+")) {
            steps.append(String.format("%dx = %d - %d\n", coefX, result, constant));
            result -= constant;
        } else {
            steps.append(String.format("%dx = %d + %d\n", coefX, result, Math.abs(constant)));
            result += Math.abs(constant);
        }
        steps.append(String.format("Step 2: Divide by the coefficient of x:\n"));
        steps.append(String.format("x = %d / %d\n", result, coefX));
        steps.append(String.format("x = %.2f\n", (double) result / coefX));
        return steps.toString();
    }

    private static String getSimpleSolution(String equation) {
        String[] parts = equation.split("x = ");
        int coefX = Integer.parseInt(parts[0].trim());
        int result = Integer.parseInt(parts[1].trim());

        // Steps for solving simple equation
        StringBuilder steps = new StringBuilder();
        steps.append("Step 1: Divide by the coefficient of x:\n");
        steps.append(String.format("x = %d / %d\n", result, coefX));
        steps.append(String.format("x = %.2f\n", (double) result / coefX));
        return steps.toString();
    }
}
