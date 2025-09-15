package functions.regex;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class UiRegex extends JFrame {
    //Aqui se declarará la expresión a buscar
    private final JTextField zonaDeExpresion;
    //Etiqueta que indicara el archivo seleccionado
    private final JLabel archivo;
    private String archivoSeleccionado;
    //Boton de busqueda de expresiones y de guardar resultados
    private final JButton buscadorDeExpresiones;
    //Etiqueta que indica el estado del resultado (donde se guardo o si se guardo)
    private final JLabel resultado;

    public UiRegex() {

        this.setTitle("Validador de Expresiones Regulares");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        // Panel Gneral Multiuso
        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));
        panelGeneral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        archivo = new JLabel("Buscar documento");
        archivo.setFont(new Font("Arial", Font.BOLD, 15));
        panelGeneral.add(archivo);
        panelGeneral.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton buscadorDeArchivosBoton = new JButton("Seleccionar archivo .txt");
        buscadorDeArchivosBoton.setFocusPainted(false);
        buscadorDeArchivosBoton.addActionListener(e -> seleccionarArchivo());
        panelGeneral.add(buscadorDeArchivosBoton);
        panelGeneral.add(Box.createRigidArea(new Dimension(10, 15)));

        JLabel textoInstruccion = new JLabel("Ingresa la expresión a buscar en el archivo seleccionado");
        panelGeneral.add(textoInstruccion);
        panelGeneral.add(Box.createRigidArea(new Dimension(0, 10)));
        
        //Especificaciones de la zona de la expresion regular, para que no se pierda el usuario
        zonaDeExpresion = new JTextField("El texto va aqui...");
        zonaDeExpresion.setForeground(Color.GRAY);
        zonaDeExpresion.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (zonaDeExpresion.getText().equals("El texto va aqui...")) {
                    zonaDeExpresion.setText("");
                    zonaDeExpresion.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (zonaDeExpresion.getText().isEmpty()) {
                    zonaDeExpresion.setText("El texto va aquí...");
                    zonaDeExpresion.setForeground(Color.GRAY);
                }
            }
        });
        panelGeneral.add(zonaDeExpresion);

        panelGeneral.add(Box.createRigidArea(new Dimension(0, 15)));

        buscadorDeExpresiones = new JButton("Buscar expresión");
        buscadorDeExpresiones.setFocusPainted(false);
        buscadorDeExpresiones.setBackground(new Color(234, 133, 62));
        buscadorDeExpresiones.setForeground(Color.WHITE);
        buscadorDeExpresiones.setEnabled(false);
        buscadorDeExpresiones.addActionListener(e -> runExpressionValidation());
        panelGeneral.add(buscadorDeExpresiones);

        panelGeneral.add(Box.createRigidArea(new Dimension(0, 15)));

        resultado = new JLabel("");
        resultado.setFont(new Font("Arial", Font.BOLD, 14));
        resultado.setForeground(new Color(234, 133, 62));
        panelGeneral.add(resultado);
        add(panelGeneral, BorderLayout.CENTER);
        setVisible(true);
    }

    private void seleccionarArchivo() {
        JFileChooser seleccionadorDeArchivo = new JFileChooser();
        seleccionadorDeArchivo.setFileFilter(new FileNameExtensionFilter("Archivos .txt", "txt"));
        int option = seleccionadorDeArchivo.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            archivoSeleccionado = seleccionadorDeArchivo.getSelectedFile().getAbsolutePath();
            archivo.setText("Archivo seleccionado: " + archivoSeleccionado);
            buscadorDeExpresiones.setEnabled(true);
        }
    }
    //Se ejecuta el validador de expresiones y se guarda el archivo
    private void runExpressionValidation() {
        String expresion = zonaDeExpresion.getText();

        if (ExpressionValidation.ValidSyntax(expresion)) {
            JFileChooser guardadorDeArchivos = new JFileChooser();
            guardadorDeArchivos.setDialogTitle("Guardar resultados de RegEx");
            guardadorDeArchivos.setFileFilter(new FileNameExtensionFilter("Archivos .txt", "txt"));

            int option = guardadorDeArchivos.showSaveDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                File fileToSave = guardadorDeArchivos.getSelectedFile();
                // Aseguramos que termine en .txt
                String outputPath = fileToSave.getAbsolutePath();
                if (!outputPath.toLowerCase().endsWith(".txt")) {
                    outputPath += ".txt";
                }
                // Llamada al validador y guardamos el path
                ExpressionValidation.TestExpression(
                        expresion,
                        archivoSeleccionado,
                        outputPath
                );
                
                resultado.setText("Los resultados se guardaron en: " + outputPath);
            } else {
                resultado.setText("No se guardó ningún archivo.");
            }
        } else {
            resultado.setText("La expresión regular no es válida");
        }
    }
}
