import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        String inputFilePath = "/home/goodsgames/Downloads/Hola.txt",
                outputFilePath = "/home/goodsgames/Downloads/Hola.txt";

        if (ExpressionValidation.ValidSyntax(expression)) {
            ExpressionValidation.TestExpression(expression, inputFilePath, outputFilePath);
        }

    }


}
