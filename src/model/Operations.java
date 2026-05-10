package model;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Operations {

    public String evaluate(String expression) {

        try {

            String mathExpression = expression
                    .replace("X", "*")
                    .replace("%", "/100");

            Expression exp = new ExpressionBuilder(mathExpression).build();

            double result = exp.evaluate();

            if (result == (long) result) {
                return String.valueOf((long) result);
            }

            return String.valueOf(result);

        } catch (Exception e) {
            return "Error";
        }
    }
}