package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <h2>Componente NumberButton</h2>
 * Botón personalizado que extiende {@link JButton} para representar
 * números y operadores de la calculadora.
 *
 * <p>Este componente implementa un diseño visual moderno con:
 * <ul>
 *   <li>Esquinas redondeadas</li>
 *   <li>Efecto hover automático</li>
 *   <li>Renderizado suavizado (AntiAliasing)</li>
 * </ul>
 *
 * <p>Se utiliza dentro de la interfaz de la calculadora como botón
 * interactivo para ingresar números u operadores matemáticos.
 *
 * @author Diego Alexander Gaviria Jimenez
 * @version 2.0
 */
public class NumberButton extends JButton {

    /** Radio de curvatura de las esquinas del botón */
    private int roundness = 100;

    /** Indica si el cursor está sobre el botón */
    private boolean hovering = false;

    /**
     * Constructor por defecto.
     * Inicializa el botón con el valor "0".
     */
    public NumberButton() {
        this("0");
    }

    /**
     * Constructor que permite definir el texto inicial del botón.
     *
     * @param text Texto que se mostrará en el botón
     */
    public NumberButton(String text) {
        super(text);
        init();
    }

    /**
     * Configuración inicial del componente.
     *
     * <p>Se eliminan los estilos por defecto del JButton y se
     * establecen propiedades visuales personalizadas.
     */
    private void init() {

        // Eliminamos estilos visuales por defecto
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        // Cursor tipo mano para indicar interacción
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Fuente del botón
        setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Tamaño recomendado del botón
        setPreferredSize(new Dimension(60,60));

        // Listener para detectar interacción del mouse
        addMouseListener(new MouseAdapter() {

            /**
             * Evento cuando el cursor entra al botón.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                hovering = true;
                repaint();
            }

            /**
             * Evento cuando el cursor sale del botón.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                hovering = false;
                repaint();
            }
        });
    }

    /**
     * Método encargado de renderizar el botón personalizado.
     *
     * <p>Dibuja un rectángulo redondeado con efecto hover
     * y posteriormente permite que JButton pinte el texto.
     *
     * @param g Contexto gráfico utilizado para dibujar el componente
     */
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        // Activamos suavizado de bordes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Color dinámico dependiendo del hover
        Color color = hovering ? getBackground().darker() : getBackground();

        g2.setColor(color);

        // Dibujamos fondo redondeado
        g2.fillRoundRect(0,0,getWidth(),getHeight(),roundness,roundness);

        g2.dispose();

        // Permite que JButton dibuje texto e iconos
        super.paintComponent(g);
    }

    /**
     * Define el radio de curvatura de las esquinas del botón.
     *
     * @param r nuevo valor de redondez
     */
    public void setRoundness(int r){
        roundness = r;
        repaint();
    }

    /**
     * Obtiene el radio de redondez actual del botón.
     *
     * @return radio de curvatura de las esquinas
     */
    public int getRoundness(){
        return roundness;
    }
}