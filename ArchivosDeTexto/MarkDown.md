# Práctica de Programación. Expresiones Regulares

### Integrantes del Equipo:

- Ayuso Contreras Gael Antonio
- Cen Santana Cristopher Israel

### Explicación

La ***manera en que implementamos el uso de expresiones regulares para la búsqueda de patrones*** fue que se utilizó la función de **RegEx** (Regular Expression) para revisar lo siguiente:

1. **Primero** si la expresión regular es válida, en el caso que no lo fuera el código no correría.
2. **Posteriormente** realiza la búsqueda de palabras que cumplan con la expresión regular en cada línea del archivo de texto y recorre todas las que lo cumplan en la línea.
3. Al **final** añade todas las coincidencias a un archivo de texto nuevo.

En base a lo anterior, utilizamos principalmente **dos funciones** de nuestra clase `functions.regex.ExpressionValidation`:

**`ValidSyntax(String expression)`**

- Esta verifica si la expresión regular que ingresamos es válida antes de intentar buscar coincidencias, evitando así cometer errores de compilación en tiempo de ejecución.
- **Cómo se utilizó:**
    - Primero, se pasa la cadena que el usuario ingresó en la interfaz.
    - Si retorna `true`, seguimos con la búsqueda. En caso de ser `false`, muestra un mensaje indicando que la expresión no es válida
    - **Ejemplo de uso:**
    
    ```java
    if (functions.regex.ExpressionValidation.ValidSyntax(expresion)) {
    		return true;
        // Continua con la búsqueda
    } else {
    		return false;
        // Muestra el mensaje de error
    }
    ```
    

**`TestExpression(String expression, String inputFilePath, String outputFilePath)`**

- Se encarga de leer el archivo de texto línea por línea, buscar todas las palabras que coincidan con la expresión regular y guardarlas en un archivo de salida.
- **Ejemplo de uso:**

```java
 public static void TestExpression(String expression, String inputFilePath, String outputFilePath) {
        try {
            // Si el archivo no existe, lo crea; si existe, lo sobreescribe.
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
            File readingfile = new File(inputFilePath);
            // Lee las lineas del archivo de entrada
            Scanner scanner = new Scanner(readingfile);
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);

            //Valida que haya una siguiente palabra
            while (scanner.hasNext()) {
                String data = scanner.next();
                Matcher matcher = pattern.matcher(data);
                //Escribe la palabra que se encuentra en una string hasta que ya no haya
                while (matcher.find()) {
                    writer.write(matcher.group() + "\n");
                }
            }
```

# **Código**

## Main

```java
public class Main {
    public static void main(String[] args) {
        //Llama a la interfaz
        new UiRegex();
    }
}
```

## Validador de Expresiones

```java
package functions.regex;  
  
import java.io.*;  
import java.util.Scanner;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
import java.util.regex.PatternSyntaxException;  
  
public class ExpressionValidation {  
  
    //Expresion que valida que la expresion es valida  
    public static boolean ValidSyntax(String expression) {  
        try {  
            //Si la sintaxis es correcta retorna true  
            Pattern pattern = Pattern.compile(expression, Pattern.CANON_EQ);  
            return true;  
        } catch (PatternSyntaxException e) {  
            //System.out.println("La expresion regular no es valida"); *Lo quite porque en teoria no se ve la terminal  
            return false;  
        }  
    }  
    //Funcion que realiza la busqueda de las palabras con la expresion regular  
    public static void TestExpression(String expression, String inputFilePath, String outputFilePath) {  
        try {  
            // Escribe y crea el archivo  
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));  
            File readingfile = new File(inputFilePath);  
            // Lee las lineas del archivo de entrada  
            Scanner scanner = new Scanner(readingfile);  
            Pattern pattern = Pattern.compile(expression, Pattern.CANON_EQ);  
  
            //Valida que haya una siguiente palabra  
            while (scanner.hasNextLine()) {  
                String data = scanner.nextLine();  
                Matcher matcher = pattern.matcher(data);  
                //Escribe la palabra que se encuentra en una string hasta que ya no haya  
                while (matcher.find()) {  
                    writer.write(matcher.group() + "\n");  
                }  
            }  
  
            //Cerrar archivos  
            writer.close();  
            scanner.close();  
        } catch (FileNotFoundException e) {  
            //System.out.println("Archivo no encontrado");  
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }  
    }  
}
```

## Interfaz

```java
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
```

# Ejemplos de Ejecución con Resultado

Haremos pruebas con los siguientes textos:

```Musk's purchase marks the first time since early 2020 that he bought Tesla stock on the open market and comes as the company is transitioning to emphasize artificial intelligence (AI), robotaxis and robotics amid a sluggish EV market.

It also comes as the billionaire is seeking greater control over Tesla and the company's board put forward a $1 trillion compensation package that aims to address his goals while setting ambitious financial and operational goals for Musk's payouts.

Tesla CEO Elon Musk bought about $1 billion in Tesla stock on Friday in his first open market purchase since 2020. (Etienne Laurent/AFP via / Getty Images)

Musk has sought greater voting power and a larger stake in Tesla and previously said that if he doesn't receive 25% voting power, he would rather do his work on [AI and robotics](https://www.foxbusiness.com/category/artificial-intelligence) outside the company. As of December, Musk had a roughly 13% stake in the company, according to LSEG data.

Jed Dorsheimer, group head of energy and power technologies at William Blair, said in a note that the firm sees Musk's stock purchase "as a clear signal of confidence from Musk," noting that the stock surged on the news.

"With Musk's purchase, combined with the upward momentum for delivery expectations and robotaxi rollout, we are becoming more bullish but retaining our Market Perform rating," Dorsheimer said.
```

Y usamos la expresión `\d+` que nos debe de devolver las palabras que tiene números del 0 al 9 una o mas veces.

![[Pasted image 20250915143920.png]]

En la dirección `/home/goodsgames/Desktop/xd.txt` debemos encontrar las palabras que cumplan con la expresión regular.
![[Pasted image 20250915144048.png]]
![[Pasted image 20250915144121.png]]

---

Probamos con el siguiente texto:

```
Former NBA player Jason Collins is receiving treatment for a brain tumor, his family announced.

In a first-person essay published in Sports Illustrated in 2013, Collins wrote about his sexuality and became the first openly gay player in any of the four major North American men's professional sports leagues.

At the time, Collins said his public admission was the first time he had spoken about that aspect of his personal life beyond his family and close friends.

Jason Collins is interviewed after a Brooklyn Nets game

Brooklyn Nets center Jason Collins conducts a radio interview following a 108-102 victory against the Los Angeles Lakers at Staples Center in Los Angeles, California on Feb. 23, 2014. (Gary A. Vasquez/USA TODAY Sports)

"No one wants to live in fear. I've always been scared of saying the wrong thing. I don't sleep well. I never have," Collins wrote in 2013. "But each time I tell another person, I feel stronger and sleep a little more soundly. It takes an enormous amount of energy to guard such a big secret. I've endured years of misery and gone to enormous lengths to live a lie. I was certain that my world would fall apart if anyone knew. And yet when I acknowledged my sexuality I felt whole for the first time."
```

Usaremos la expresión regular `[A-Z]\w+`, son las palabras que inician con una letra mayúscula y puede terminar con cualquier sentencia de caracteres.

![[Pasted image 20250915155413.png]]

El resultado:

![[Pasted image 20250915155429.png]]

--- 
Ahora probaremos con la letra de una cancion:
```
[Verse 1: Diana Gordon]  
Imagine life, imagine life without me, it's a waste of time  
[You want the kind of love that can make a child] 
Don't want just anybody, (Not just anybody, not just anybody) ooh  
Good love fill my body up like a glass when you pour it all out  
If you know me, you know I mind, someone like me is hard to find  
No mistakin' if I am there, well aware you are always mine  
[And still you seem so surprised, always gon' be mine]
  
[Chorus: Diana Gordon _with Lil Yachty_]  
_I wanna be where you are  
I just can't walk away, you drive me crazy, ah_  
I lose it, lose it  
And _I wanna be where y__ou are  
I just can't walk away, you_ _drive me crazy, ah_  
I lose it, losе it  
  
[Verse 2: Lil Yachty]  
[Imagine me circlin' through life without a piеce of you]
[Seven out of seven days I'm needin' you  
You bring life to the party]
[Good love feels like butterflies suffocatin' your insides, oh]
It's a late night, you know I try, [I cannot picture when you lie]
I'm mistaken thinkin' you were taken, knowin' that you were always mine  
And still I feel so surprised, you're always gon' be mine  
```

En este texto tenemos palabras entre `[ ]` significa en las letras de una canción pueden tener diferentes significados, como indicar una parte de la letra que no está cantada, una aclaración o nota del compositor, una traducción al inglés de una palabra en otro idioma, o para identificar un sonido o efecto de audio específico.

Entonces digamos que queremos saber cuales son esas palabras en esta canción. La expresion regular seria `\[.+\]`, la cual son las palabras que empiezan en `[` y terminan en `]`.

![[Pasted image 20250915161858.png]]


El resultado:

![[Pasted image 20250915161919.png]]