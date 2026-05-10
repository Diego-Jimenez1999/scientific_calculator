package controller;

import model.Operations;
import view.CalculatorFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * <h2>Clase CalculatorController (Controlador)</h2>
 * Gestiona el flujo de datos entre la Vista y el Modelo, actuando como puente.
 * * @author Diego Alexander Gaviria
 * @version 2.1
 */
public class CalculatorController implements ActionListener {

    private final CalculatorFrame view;
    private final Operations model;
    private boolean isResultShown = false;

    /**
     * Constructor del controlador.
     * @param view Instancia de la interfaz gráfica.
     * @param model Instancia de la lógica matemática.
     */
    public CalculatorController(CalculatorFrame view, Operations model) {
        this.view = view;
        this.model = model;
    }

    /**
     * Captura los eventos de clic, extrae el valor del botón y actualiza la vista.
     * @param e Evento de acción capturado desde los botones.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Recuperamos el comando, si es nulo obtenemos el texto del botón
        String command = e.getActionCommand();
        if (command == null && e.getSource() instanceof JButton) {
            command = ((JButton) e.getSource()).getText();
        }

        String currentText = view.getDisplay().getText();

        if (command.equals("=")) {
            view.getDisplay().setText(model.evaluate(currentText));
            isResultShown = true;
        } else if (command.equals("C")) {
            view.getDisplay().setText("0");
            isResultShown = false;
        } else {
            // Lógica de concatenación
            if (isResultShown || currentText.equals("0")) {
                view.getDisplay().setText(command);
            } else {
                view.getDisplay().setText(currentText + command);
            }
            isResultShown = false;
        }
    }

}