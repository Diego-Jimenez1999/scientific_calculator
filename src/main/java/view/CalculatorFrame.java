package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.CalculatorController;

/**
 * Ventana principal de la calculadora.
 */
public class CalculatorFrame extends JFrame {

    private static final Color SURFACE_COLOR = Color.decode("#F8F9FA");
    private static final Color NUMBER_BUTTON_COLOR = Color.decode("#FFFFFF");
    private static final Color OPERATOR_BUTTON_COLOR = Color.decode("#FF9F43");
    private static final Color FUNCTION_BUTTON_COLOR = Color.decode("#ff7b43");
    private static final Color PRIMARY_TEXT_COLOR = Color.decode("#2D3436");
    private static final Color SECONDARY_TEXT_COLOR = Color.decode("#636E72");
    private static final Font DISPLAY_FONT = new Font("Tahoma", Font.PLAIN, 36);
    private static final Font HISTORY_FONT = new Font("Tahoma", Font.PLAIN, 20);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 24);

    private final PanelRound rootPanel = new PanelRound(); // Panel principal con bordes redondeados
    private final ControlButton closeButton = new ControlButton(); // Botón de cierre
    private final ControlButton minimizeButton = new ControlButton(); // Botón de minimizar
    private final PanelRound displayPanel = new PanelRound(); // Panel de visualización
    private final JLabel historyLabel = new JLabel(); // Etiqueta para mostrar la historia de operaciones
    private final JLabel displayLabel = new JLabel(); // Etiqueta para mostrar el resultado actual
    private final JPanel buttonsPanel = new JPanel(); // Panel para contener los botones de la calculadora
    private final JPanel contentPanel = new JPanel(); // Panel para organizar el displayPanel y buttonsPanel dentro del rootPanel

    private final List<NumberButton> allButtons = new ArrayList<>();

    private Point dragStartPoint;

    public CalculatorFrame() {
        
        initFrame();
        initComponents();
        initWindowDragging();
        applyStyles();
    }

    /**
     * Configuración inicial de la ventana.
     */
    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setBackground(new Color(0, 0, 0, 0));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    /**
     * Inicialización de los componentes de la ventana.
     */
    private void initComponents() {
        rootPanel.setBackground(SURFACE_COLOR);
        rootPanel.setLayout(new BorderLayout(0, 14));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(12, 21, 24, 28));

        JPanel controlsPanel = createControlsPanel();
        configureDisplayPanel();
        configureButtonsPanel();

        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(0, 12));
        contentPanel.add(displayPanel, BorderLayout.NORTH);
        contentPanel.add(buttonsPanel, BorderLayout.CENTER);

        rootPanel.add(controlsPanel, BorderLayout.NORTH);
        rootPanel.add(contentPanel, BorderLayout.CENTER);

        add(rootPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Crea el panel de controles.
     * @return El panel de controles.
     */
    private JPanel createControlsPanel() {
        // Panel para contener los botones de control (cerrar y minimizar)
        JPanel controlsPanel = new JPanel();
        controlsPanel.setOpaque(false);
        controlsPanel.setLayout(new BorderLayout());
        
        // Panel para contener los botones de control (cerrar y minimizar)
        JPanel leftButtonsPanel = new JPanel();
        leftButtonsPanel.setOpaque(false);

        closeButton.setText("");
        minimizeButton.setText("");
        minimizeButton.setButtonType("-");

        leftButtonsPanel.add(closeButton);
        leftButtonsPanel.add(minimizeButton);

        controlsPanel.add(leftButtonsPanel, BorderLayout.WEST);
        return controlsPanel;
    }

    /**
     * Configura el panel de visualización.
     * Incluye la etiqueta de historia y la etiqueta de resultado actual.
     */
    private void configureDisplayPanel() {
        displayPanel.setLayout(new GridLayout(2, 1));
        displayPanel.setPreferredSize(new Dimension(329, 151));
        displayPanel.setBackground(Color.decode("#BFC3C9"));

        historyLabel.setText("");
        historyLabel.setFont(HISTORY_FONT);
        historyLabel.setForeground(SECONDARY_TEXT_COLOR);
        historyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        historyLabel.setBorder(BorderFactory.createEmptyBorder(8, 10, 2, 10));

        displayLabel.setText("0");
        displayLabel.setFont(DISPLAY_FONT);
        displayLabel.setForeground(SECONDARY_TEXT_COLOR);
        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        displayLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        displayLabel.setOpaque(false);

        displayPanel.add(historyLabel);
        displayPanel.add(displayLabel);
    }
    
    /**
     * Configura el panel de botones.
     * Agrega los botones de números, operadores y funciones a la calculadora.
     */
    private void configureButtonsPanel() {
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(6, 4, 10, 10));
        
        // Fila 1
        addButton("(", "NUMBER_BUTTON_COLOR");
        addButton(")", "NUMBER_BUTTON_COLOR");
        addButton("%", "NUMBER_BUTTON_COLOR");
        addButton("/", "OPERATOR_BUTTON_COLOR");

        // Fila 2
        addButton("7", "NUMBER_BUTTON_COLOR");
        addButton("8", "NUMBER_BUTTON_COLOR");
        addButton("9", "NUMBER_BUTTON_COLOR");
        addButton("X", "OPERATOR_BUTTON_COLOR");

        // Fila 3
        addButton("4", "NUMBER_BUTTON_COLOR");
        addButton("5", "NUMBER_BUTTON_COLOR");
        addButton("6", "NUMBER_BUTTON_COLOR");
        addButton("-", "OPERATOR_BUTTON_COLOR");

        // Fila 4
        addButton("1", "NUMBER_BUTTON_COLOR");
        addButton("2", "NUMBER_BUTTON_COLOR");
        addButton("3", "NUMBER_BUTTON_COLOR");
        addButton("+", "OPERATOR_BUTTON_COLOR");

        // Fila 5
        
        addButton(".", "NUMBER_BUTTON_COLOR");
        addButton("0", "NUMBER_BUTTON_COLOR");
        addButton("C", "OPERATOR_BUTTON_COLOR");
        


        //fila 6
        addButton("=", "OPERATOR_BUTTON_COLOR");
        addButton("sen", "function");
        addButton("cos", "function");
        addButton("log", "function");
        addButton("tan", "function");

    }
    
    /**
     * Agrega un botón al panel de botones.
     * El método también aplica estilos al botón según su tipo (número, operador o función).
     * @param text El texto del botón.
     * @param isOperator Indica si el botón es un operador (para aplicar estilos) .
     */
    private void addButton(String text, String isOperator) {
        NumberButton button = new NumberButton();

        button.setText(text);
        button.setFont(BUTTON_FONT);
        button.setMargin(new Insets(0, 0, 0, 0)); //elimina el espacio interno del botón

        // Aplica estilos según el tipo de botón
        switch(isOperator) {
            case "function":
                button.setBackground(OPERATOR_BUTTON_COLOR);
                button.setForeground(Color.WHITE);
                break;
            case "NUMBER_BUTTON_COLOR":
                button.setBackground(NUMBER_BUTTON_COLOR);
                button.setForeground(PRIMARY_TEXT_COLOR);
                break;
            case "OPERATOR_BUTTON_COLOR":
                button.setBackground(OPERATOR_BUTTON_COLOR);
                button.setForeground(Color.WHITE);
                break;
            
            default:
                break;
        }

        allButtons.add(button); // Agrega el botón a la lista de todos los botones para facilitar la asignación de eventos posteriormente
        buttonsPanel.add(button); // Agrega el botón al panel de botones
    }
    
    /**
     * Aplica estilos a los componentes de la interfaz.
     */
    private void applyStyles() {

        rootPanel.setBackground(SURFACE_COLOR);
        displayLabel.setForeground(SECONDARY_TEXT_COLOR);
        
        // Aplica estilos a los botones según su tipo
        for (NumberButton button : allButtons) {

            if (isFunctionButton(button.getText())) {

                button.setBackground(FUNCTION_BUTTON_COLOR);
                button.setForeground(Color.WHITE);

            } else if (!isOperatorButton(button.getText())) {
                button.setBackground(NUMBER_BUTTON_COLOR);
                button.setForeground(PRIMARY_TEXT_COLOR);
            }
            button.setFont(BUTTON_FONT);
        }
    }

    /**
     * Verifica si un botón es un operador.
     * @param text El texto del botón.
     * @return True si el botón es un operador, false en caso contrario.
    */
    private boolean isOperatorButton(String text) {
        return "/".equals(text)
                || "X".equals(text)
                || "-".equals(text)
                || "+".equals(text)
                || "=".equals(text);
    }

    /**
     * Verifica si un botón es una función.
     * @param text El texto del botón.
     * @return True si el botón es una función, false en caso contrario.
     */
    private boolean isFunctionButton(String text) {
        return "sen".equalsIgnoreCase(text)
                || "sin".equalsIgnoreCase(text)
                || "cos".equalsIgnoreCase(text)
                || "tan".equalsIgnoreCase(text)
                || "log".equalsIgnoreCase(text);
    }
    
    /**
     * Inicializa el arrastre de la ventana.
    */
    private void initWindowDragging() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                dragStartPoint = event.getPoint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent event) {
                if (dragStartPoint == null) {
                    return;
                }

                setLocation(
                        getLocation().x + event.getX() - dragStartPoint.x,
                        getLocation().y + event.getY() - dragStartPoint.y
                );
            }
        });
    }
    
    /**
     * Inicializa el controlador de la calculadora.
     * @param controller El controlador de la calculadora.
     */
    public void initController(CalculatorController controller) {
        for (NumberButton button : allButtons) {
            button.addActionListener(controller);
        }
    }

    public JLabel getDisplay() {
        return displayLabel;
    }

    public JLabel getDisplay2() {
        return historyLabel;
    }

}
