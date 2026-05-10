package main;

import controller.CalculatorController;
import model.Operations;
import view.CalculatorFrame;

/**
 * <h2>Clase Principal (Main)</h2>
 * Punto de entrada de la aplicación CalculadoraPro.
 * Implementa el patrón arquitectónico MVC (Modelo-Vista-Controlador),
 * orquestando la creación e interconexión de las diferentes capas.
 * * @author Diego Alexander Gaviria Jimenez
 * @version 1.0
 */
public class CalculatorApp {

    /**
     * Método principal que arranca la aplicación.
     * Realiza la inyección de dependencias necesaria para conectar la interfaz
     * gráfica con la lógica de negocio a través del controlador.
     * * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {

        // Inicialización de componentes (Capas independientes)
        CalculatorFrame view = new CalculatorFrame();
        Operations model = new Operations();
        
        // Inyección de dependencias en el controlador
        // Se vincula la vista y el modelo para que el controlador pueda gestionarlos
        CalculatorController controller = new CalculatorController(view, model);
        
        // Configuración y activación de la interfaz
        // Asigna el controlador como el gestor de eventos de los botones
        view.initController(controller);
        
        // Centra la ventana en la pantalla del usuario
        view.setLocationRelativeTo(null);
        
        // Hace visible la aplicación
        view.setVisible(true);
    }
}
