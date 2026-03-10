package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.border.AbstractBorder;

/**
 * Clase que define un borde personalizado con esquinas redondeadas.
 * Permite configurar el grosor, el color y la presencia de una sombra decorativa.
 * @author Diego Alexander Gaviria Jimenez
 * @version 1.1
 */
public class BorderLineRound extends AbstractBorder {

    /** Radio de curvatura de las esquinas */
    private int radius;
    /** Grosor de la línea del borde en píxeles */
    private int borderWidth;
    /** Color del contorno del borde */
    private Color borderColor;
    /** Estado de visibilidad de la sombra proyectada */
    private boolean shadow;

    /**
     * Constructor con radio de esquina. Por defecto usa borde de 1px gris.
     * @param radius Radio de curvatura de las esquinas.
     */
    public BorderLineRound(int radius) {
        this(radius, 1, Color.GRAY, false);
    }

    /**
     * Constructor con radio y grosor del borde.
     * @param radius Radio de las esquinas.
     * @param borderWidth Grosor de la línea del borde en píxeles.
     */
    public BorderLineRound(int radius, int borderWidth) {
        this(radius, borderWidth, Color.GRAY, false);
    }
    
    /**
     * Constructor con radio, grosor y color del borde.
     * @param radius Radio de las esquinas.
     * @param borderWidth Grosor de la línea.
     * @param color Color del contorno.
    */
    public BorderLineRound(int radius, int borderWidth, Color color) {
        this(radius, borderWidth, color, false);
    }
      
    /**
     * Constructor optimizado para configuraciones rápidas de color y sombra.
     * @param borderColor Color del contorno.
     * @param shadow True para activar un efecto de sombra ligera.
     */
    public BorderLineRound(Color borderColor, boolean shadow) {
        this.radius = 12;        
        this.borderWidth = 1;    
        this.borderColor = borderColor;
        this.shadow = shadow;  
    }

    /**
     * Constructor completo para un control total del estilo del borde.
     * @param radius Radio de curvatura.
     * @param borderWidth Grosor de la línea.
     * @param borderColor Color del contorno.
     * @param shadow Estado de la sombra (true/false).
     */
    public BorderLineRound(int radius, int borderWidth, Color borderColor, boolean shadow) {
        this.radius = radius;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
        this.shadow = shadow;
    }

    /**
     * Método encargado de dibujar el borde físicamente sobre el componente.
     * Implementa renderizado avanzado para evitar distorsiones en las curvas.
     * * @param c Componente al que se aplica el borde.
     * @param g Objeto Graphics original.
     * @param x Posición horizontal.
     * @param y Posición vertical.
     * @param width Ancho del componente.
     * @param height Alto del componente.
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Activación de Antialiasing para bordes suaves
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Lógica de dibujo de sombra: se dibuja ligeramente desplazada
        if (shadow) {
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillRoundRect(
                    x + 2,
                    y + 2,
                    width - 1,
                    height - 1,
                    radius,
                    radius
            );
        }

        // Configuración de la línea y dibujo del contorno
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderWidth));

        // Ajuste matemático: se resta el borderWidth para que el borde no se salga del área visible
        g2.drawRoundRect(
                x,
                y,
                width - borderWidth,
                height - borderWidth,
                radius,
                radius
        );

        g2.dispose();
    }
    
    /* ===========================================================
       GETTERS Y SETTERS 
       =========================================================== */

    /** @return Radio de las esquinas */
    public int getRadius() { return radius; }

    /** @param radius Nuevo radio de curvatura */
    public void setRadius(int radius) { this.radius = radius; }

    /** @return Grosor de la línea */
    public int getBorderWidth() { return borderWidth; }

    /** @param borderWidth Nuevo grosor del borde */
    public void setBorderWidth(int borderWidth) { this.borderWidth = borderWidth; }

    /** @return Color actual del contorno */
    public Color getBorderColor() { return borderColor; }

    /** @param borderColor Nuevo color para el borde */
    public void setBorderColor(Color borderColor) { this.borderColor = borderColor; }

    /** @return True si la sombra está activa */
    public boolean isShadow() { return shadow; }

    /** @param shadow Activa o desactiva la sombra */
    public void setShadow(boolean shadow) { this.shadow = shadow; }
    
}