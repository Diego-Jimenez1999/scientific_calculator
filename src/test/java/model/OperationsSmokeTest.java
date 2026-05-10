package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OperationsSmokeTest {

    private final Operations operations = new Operations();

    @Test
    void shouldEvaluateCoreExpressions() {
        assertResult("2+3X4", "14");
        assertResult("(2+3)X4", "20");
        assertResult("0.1+0.2", "0.3");
        assertResult("1/0", "Error");
        assertResult("2++3", "Error");
        assertResult("(2+3", "Error");
        assertResult("tan(0)", "0");
        assertResult("TAN(0)", "0");
        assertResult("sen(30)", "0.5");
        assertResult("cos(60)", "0.5");
        assertResult("tan(45)", "1");
        assertResult("tan(45", "1");
        assertResult("log(1)", "0");
    }

    private void assertResult(String expression, String expected) {
        assertEquals(expected, operations.evaluate(expression), expression);
    }
}
