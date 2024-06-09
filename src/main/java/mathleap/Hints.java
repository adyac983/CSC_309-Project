package mathleap;

public class Hints {
    public static String getHint(String equation, int step) {
        if (equation.contains("x^2")) {
            return getQuadraticHint(equation, step);
        } else if (equation.contains("+") || equation.contains("-")) {
            return getLinearHint(equation, step);
        } else {
            return getSimpleHint(equation, step);
        }
    }

    private static String getQuadraticHint(String equation, int step) {
        String[] parts = equation.split(" ");
        int a = Integer.parseInt(parts[0].replace("x^2", "").trim());
        int b = Integer.parseInt(parts[2].replace("x", "").trim());
        int c = Integer.parseInt(parts[4].replace("= 0", "").trim());

        switch (step) {
            case 1:
                return "Step 1: Identify the coefficients: a = " + a + ", b = " + b + ", c = " + c;
            case 2:
                int discriminant = b * b - 4 * a * c;
                return "Step 2: Calculate the discriminant: D = b^2 - 4ac = " + discriminant;
            case 3:
                return "Step 3: If D >= 0, the equation has real roots. Proceed to solve using factorization or the quadratic formula.";
            default:
                return "No more hints available. Try solving it from here!";
        }
    }

    private static String getLinearHint(String equation, int step) {
        String[] parts = equation.split(" ");
        int coefX = Integer.parseInt(parts[0].replace("x", "").trim());
        int constant = Integer.parseInt(parts[2].trim());
        String operator = parts[1];
        int result = Integer.parseInt(parts[4].replace("= 0", "").trim());

        switch (step) {
            case 1:
                return "Step 1: Move the constant to the other side of the equation.";
            case 2:
                if (operator.equals("+")) {
                    result -= constant;
                } else {
                    result += Math.abs(constant);
                }
                return "Step 2: Simplify the equation: " + coefX + "x = " + result;
            case 3:
                return "Step 3: Divide by the coefficient of x to isolate x.";
            default:
                return "No more hints available. Try solving it from here!";
        }
    }

    private static String getSimpleHint(String equation, int step) {
        String[] parts = equation.split("x = ");
        int coefX = Integer.parseInt(parts[0].trim());
        int result = Integer.parseInt(parts[1].trim());

        switch (step) {
            case 1:
                return "Step 1: Divide the result" + result + " by the coefficient of x" + coefX;
            default:
                return "No more hints available. Try solving it from here!";
        }
    }
}
