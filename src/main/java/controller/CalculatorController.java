package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Operations;
import view.CalculatorFrame;

/**
 * Coordinates user input from the Swing view with the calculator model.
 */
public class CalculatorController implements ActionListener {

    private static final String OPERATORS = "+-/X";

    private final CalculatorFrame view;
    private final Operations model;
    private boolean isResultShown = false;

    /**
     * Constructor for the CalculatorController.
     * @param view The calculator view.
     * @param model The calculator model.
     */
    public CalculatorController(CalculatorFrame view, Operations model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = getCommand(e); // Obtiene el comando del evento, ya sea del botón o del ActionCommand

        // Si el comando es nulo o está en blanco, no se realiza ninguna acción
        if (command == null || command.isBlank()) {
            return;
        }

        String currentText = view.getDisplay().getText();

        switch (command) {
            case "=":
                evaluate(currentText);
                break;
            case "C":
                clear();
                break;
            case "sen":
                appendFunction("sin(", currentText);
                break;
            case "sin":
                appendFunction("sin(", currentText);
                break;
            case "cos":
                appendFunction("cos(", currentText);
                break;
            case "log":
                appendFunction("log(", currentText);
                break;
            case "tan":
                appendFunction("tan(", currentText);
                break;
            default:
                append(command, currentText);
                break;
        }
    }

    private String getCommand(ActionEvent e) {
        String command = e.getActionCommand();
        if ((command == null || command.isBlank()) && e.getSource() instanceof JButton) {
            command = ((JButton) e.getSource()).getText();
        }

        return command;
    }

    private void evaluate(String currentText) {
        String result = model.evaluate(currentText);
        view.getDisplay2().setText(currentText + " =");
        view.getDisplay().setText(result);
        isResultShown = true;
    }

    /**
     * limpiar la pantalla de la calculadora, restableciendo el estado inicial.
     */
    private void clear() {
        view.getDisplay().setText("0");
        view.getDisplay2().setText("");
        isResultShown = false;
    }

    /**
     * Appends a command to the display.
     * @param command The command to append.
     * @param currentText The current text in the display.
     */
    private void append(String command, String currentText) {
        if (isResultShown && !isOperator(command)) {
            view.getDisplay2().setText("");
            view.getDisplay().setText(command);
            isResultShown = false;
            return;
        }

        if (isResultShown && isOperator(command)) {
            view.getDisplay().setText(currentText + command);
            isResultShown = false;
            return;
        }

        if (shouldReplaceInitialZero(command, currentText)) {
            view.getDisplay().setText(command);
        } else if (isRepeatedOperator(command, currentText)) {
            view.getDisplay().setText(currentText.substring(0, currentText.length() - 1) + command);
        } else if (!isRepeatedDecimal(command, currentText)) {
            view.getDisplay().setText(currentText + command);
        }

        isResultShown = false;
    }
    
    /**
     * Appends a function to the display.
     * @param function The function to append.
     * @param currentText The current text in the display.
    */
    private void appendFunction(String function, String currentText) {
        if (isResultShown || currentText.equals("0")) {
            view.getDisplay2().setText("");
            view.getDisplay().setText(function);
        } else {
            view.getDisplay().setText(currentText + function);
        }

        isResultShown = false;
    }

    /**
     * Determina si if  ingresar un nuevo comando debe reemplazar el cero inicial en la pantalla.
     * @param command The command that was pressed.
     * @param currentText The current text in the display.
     * @return True if the initial zero should be replaced, false otherwise.
     */
    private boolean shouldReplaceInitialZero(String command, String currentText) {
        return currentText.equals("0") && !command.equals(".") && !isOperator(command) && !command.equals("%");
    }
    
    /**
     * Determines if the command is a repeated operator.
     * @param command The command that was pressed.
     * @param currentText The current text in the display.
     * @return True if the command is a repeated operator, false otherwise.
     */
    private boolean isRepeatedOperator(String command, String currentText) {
        if (!isOperator(command) || currentText.isEmpty()) {
            return false;
        }

        String lastCharacter = currentText.substring(currentText.length() - 1);
        return isOperator(lastCharacter);
    }

    /**
     * Determines if the command is a repeated decimal point.
     * @param command The command that was pressed.
     * @param currentText The current text in the display.
     * @return True if the command is a repeated decimal point, false otherwise.
     */
    private boolean isRepeatedDecimal(String command, String currentText) {
        if (!command.equals(".")) {
            return false;
        }

        String[] parts = currentText.split("[+\\-/X]", -1);
        String currentNumber = parts.length == 0 ? currentText : parts[parts.length - 1];
        return currentNumber.contains(".");
    }


    /**
     * Determines if the given value is an operator.
     * @param value The value to check.
     * @return True if the value is an operator, false otherwise.
    */
    private boolean isOperator(String value) {
        return value.length() == 1 && OPERATORS.contains(value);
    }
}
