package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Operations {

    private static final String VALID_EXPRESSION_REGEX = "[0-9+\\-*/().%\\sa-zA-Z]+";
    private static final int MAX_DECIMAL_PLACES = 6; // Número máximo de decimales a mostrar en el resultado



    /**
     * Evalúa una expresión matemática dada como cadena de texto.
     * @param expression
     * @return
     */
    public String evaluate(String expression) {


        if (expression == null || expression.trim().isEmpty()) {
            return "Error";
        }

        // Normaliza la expresión matemática ingresada por el usuario, reemplazando símbolos y funciones por sus equivalentes reconocidos por la biblioteca de evaluación.
        try {
            String mathExpression = normalizeExpression(expression);

            if (!isValidExpression(mathExpression)) {
                return "Error";
            }

            Expression exp = new ExpressionBuilder(mathExpression).build();
            double result = exp.evaluate();

            if (!Double.isFinite(result)) {
                return "Error";
            }

            return formatResult(result);

        } catch (Exception e) {
            return "Error";
        }
    }

    
    /**
     * Normaliza la expresión matemática ingresada por el usuario, reemplazando símbolos y funciones por sus equivalentes   *reconocidos por la biblioteca de evaluación.
     * @param expression
     * @return
     */
    private String normalizeExpression(String expression) {


        // Remplaza simbolos y funcions por unas que entienda la biblioteca de evaluación, además de eliminar espacios innecesarios.
        String normalized = expression
                .toLowerCase()
                .replace("X", "*")
                .replace("x", "*")
                .replace("\u00F7", "/")
                .replace(",", ".")
                .replace("sen", "sin")
                .replace("%", "/100")
                .trim();

        if (containsFunctionToken(normalized)) {
            normalized = closeUnbalancedParentheses(normalized);
        }


        // Convierte los argumentos de las funciones trigonométricas de grados a radianes, ya que la biblioteca de evaluación espera los argumentos en radianes.
        normalized = normalized.replaceAll(
            "sin\\(([^)]+)\\)",
            "sin(($1*pi)/180)"
        );

        // Convierte los argumentos de las funciones trigonométricas de grados a radianes, ya que la biblioteca de evaluación espera los argumentos en radianes.
        normalized = normalized.replaceAll(
            "cos\\(([^)]+)\\)",
            "cos(($1*pi)/180)"
        );
        
        // Convierte los argumentos de las funciones trigonométricas de grados a radianes, ya que la biblioteca de evaluación espera los argumentos en radianes.
        normalized = normalized.replaceAll(
            "tan\\(([^)]+)\\)",
            "tan(($1*pi)/180)"
        );

        return normalized;
    }
    
    /**
     * Valida la expresión matemática ingresada por el usuario.
     * @param expression
     * @return
     */
    private boolean isValidExpression(String expression) {
        return expression.matches(VALID_EXPRESSION_REGEX)
                && !containsUnsupportedFunctions(expression)
                && !expression.matches(".*[+\\-*/.]{2,}.*")
                && hasBalancedParentheses(expression);
    }

    /**
     * Verifica si la expresión matemática contiene funciones no soportadas.
     * @param expression La expresión matemática a verificar.
     * @return True si la expresión contiene funciones no soportadas, false en caso contrario.
     */
    private boolean containsUnsupportedFunctions(String expression) {
        return expression
                .replace("sin", "")
                .replace("cos", "")
                .replace("tan", "")
                .replace("log", "")
                .replace("pi", "")
                .matches(".*[A-Za-z].*");
    }

    private String closeUnbalancedParentheses(String expression) {
        int balance = 0;
        StringBuilder builder = new StringBuilder(expression);

        for (int index = 0; index < builder.length(); index++) {
            char character = builder.charAt(index);
            if (character == '(') {
                balance++;
            } else if (character == ')') {
                balance--;
            }
        }

        while (balance > 0) {
            builder.append(')');
            balance--;
        }

        return builder.toString();
    }

    private boolean containsFunctionToken(String expression) {
        return expression.contains("sin(")
                || expression.contains("cos(")
                || expression.contains("tan(")
                || expression.contains("log(");
    }

    /**
     * Verifica si la expresión matemática tiene paréntesis balanceados.
     *
     * @param expression La expresión matemática a verificar.
     * @return True si la expresión tiene paréntesis balanceados, false en caso contrario.
     */
    private boolean hasBalancedParentheses(String expression) {
        int balance = 0;

        for (char character : expression.toCharArray()) {
            if (character == '(') {
                balance++;
            } else if (character == ')') {
                balance--;
            }

            if (balance < 0) {
                return false;
            }
        }

        return balance == 0;
    }

    /**
     * Formatea el resultado de la evaluación de la expresión matemática, limitando el número de decimales y eliminando ceros innecesarios.
     * @param result El resultado a formatear.
     * @return  El resultado formateado.
     */
    private String formatResult(double result) {
        BigDecimal value = BigDecimal.valueOf(result)
                .setScale(MAX_DECIMAL_PLACES, RoundingMode.HALF_UP)
                .stripTrailingZeros();

        return value.toPlainString();
    }
}
