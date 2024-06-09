import mathleap.Equations;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EquationsTest {

    @Test
    void testGetEquation() {
        String equation = Equations.getEquation(1, 1);
        assertNotNull(equation);
        assertTrue(equation.contains("x") || equation.contains("x^2"));
    }

    @Test
    void testGetEquationMap() {
        HashMap<Integer, String> map = Equations.getEquationMap();
        assertNotNull(map);
        assertEquals(131, map.size());
    }

    @Test
    void testSolveLinearEquation() {
        double answer = Equations.solveMidEquation("3x + 6 = 0");
        assertEquals(-2, answer);
    }

}

