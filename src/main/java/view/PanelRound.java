package view;

import javax.swing.*;
import java.awt.*;

/**
 * <h2>Componente PanelRound</h2>
 *
 * Panel personalizado con soporte para esquinas redondeadas.
 *
 * <p>Este componente permite crear contenedores visuales
 * con estilo moderno dentro de la interfaz gráfica
 * de la calculadora.
 *
 * <p>El panel utiliza renderizado con AntiAliasing
 * para evitar bordes pixelados.
 *
 * @author Diego Alexander Gaviria Jimenez
 * @version 2.0
 */
public class PanelRound extends JPanel {

    /** Radio de las esquinas redondeadas */
    private int radius = 30;

    /**
     * Constructor por defecto.
     */
    public PanelRound(){
        setOpaque(false);
    }

    /**
     * Constructor que permite definir el radio de las esquinas.
     *
     * @param radius radio de curvatura
     */
    public PanelRound(int radius){
        this.radius = radius;
        setOpaque(false);
    }

    /**
     * Renderiza el fondo del panel con esquinas redondeadas.
     *
     * @param g contexto gráfico de renderizado
     */
    @Override
    protected void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());

        g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);

        g2.dispose();

        super.paintComponent(g);
    }

    /**
     * Define el radio de las esquinas del panel.
     *
     * @param radius nuevo radio
     */
    public void setRadius(int radius){
        this.radius = radius;
        repaint();
    }

    /**
     * Obtiene el radio actual de las esquinas.
     *
     * @return radio del panel
     */
    public int getRadius(){
        return radius;
    }
}