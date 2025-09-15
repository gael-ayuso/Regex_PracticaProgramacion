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
            Pattern pattern = Pattern.compile(expression);
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
            Pattern pattern = Pattern.compile(expression);

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
