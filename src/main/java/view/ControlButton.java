package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <h2>Componente ControlButton</h2>
 *
 * Botón de control para acciones de ventana dentro de la interfaz
 * de la calculadora.
 *
 * <p>Permite ejecutar dos acciones principales:
 * <ul>
 *   <li>Cerrar la aplicación</li>
 *   <li>Minimizar la ventana</li>
 * </ul>
 *
 * <p>El botón utiliza renderizado vectorial para dibujar
 * los iconos de control sin necesidad de imágenes externas.
 *
 * @author Diego Alexander Gaviria Jimenez
 * @version 2.0
 */
public class ControlButton extends JButton {

    /** Color base del botón */
    private Color backgroundColor = new Color(231,76,60);

    /** Color al pasar el cursor */
    private Color hoverColor = new Color(192,57,43);

    /** Indica si el cursor está sobre el botón */
    private boolean hovering = false;

    /** Tamaño del botón */
    private int dimension = 22;

    /** Tipo de botón: "x" cerrar o "-" minimizar */
    private String buttonType = "x";

    /**
     * Constructor del botón de control.
     */
    public ControlButton() {
        init();
        initAction();
    }

    /**
     * Configuración visual del botón.
     */
    private void init(){

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        Dimension size = new Dimension(dimension,dimension);

        setPreferredSize(size);
        setMinimumSize(size);

        // Listener para hover
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e){
                hovering = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e){
                hovering = false;
                repaint();
            }
        });
    }

    /**
     * Define la acción del botón según el tipo configurado.
     */
    private void initAction(){

        addActionListener(e -> {

            Window window = SwingUtilities.getWindowAncestor(this);

            if(window instanceof JFrame){
                JFrame frame = (JFrame) window;

                if(buttonType.equals("x")){
                    frame.dispose();
                }
                else{
                    frame.setState(JFrame.ICONIFIED);
                }
            }
        });
    }

    /**
     * Renderiza el botón circular y el icono vectorial.
     *
     * @param g contexto gráfico de renderizado
     */
    @Override
    protected void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(hovering ? hoverColor : backgroundColor);

        g2.fillOval(0,0,getWidth(),getHeight());

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2f));

        int padding = getWidth()/4;

        if(buttonType.equals("x")){

            g2.drawLine(padding,padding,
                    getWidth()-padding,getHeight()-padding);

            g2.drawLine(getWidth()-padding,padding,
                    padding,getHeight()-padding);
        }
        else{

            g2.drawLine(padding,getHeight()/2,
                    getWidth()-padding,getHeight()/2);
        }

        g2.dispose();
    }

    /**
     * Define el tipo de botón de control.
     *
     * @param type "x" para cerrar o "-" para minimizar
     */
    public void setButtonType(String type){

        buttonType = type;

        if(type.equals("x")){
            backgroundColor = new Color(231,76,60);
            hoverColor = new Color(192,57,43);
        }
        else{
            backgroundColor = new Color(46,204,113);
            hoverColor = new Color(39,174,96);
        }

        repaint();
    }

    /**
     * Obtiene el tipo actual de botón.
     *
     * @return tipo de botón
     */
    public String getButtonType(){
        return buttonType;
    }
}
