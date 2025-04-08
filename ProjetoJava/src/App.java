import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\adejr\\github\\Compilador-Java\\ProjetoJava\\src\\CodeSample.txt";
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder entrada = new StringBuilder();
            String currentLine;
            AnalisadorLexico analisador = new AnalisadorLexico(entrada.toString());

            System.out.println("Reading file: " + fileName);
            System.out.println("--------------------------------------------------");

            while ((currentLine = br.readLine()) != null) {
                lineNumber++;
                entrada.append(currentLine).append("\n");
                analisador = new AnalisadorLexico(entrada.toString());
                System.out.println(String.format("%-4d| %s", lineNumber, currentLine));
                analisador.analisar(lineNumber);
            }

            System.out.println("--------------------------------------------------");
            System.out.println("Tokens encontrados:");

            for (Token token : AnalisadorLexico.listaDeTokens) {
                System.out.println(token);
            }

            System.out.println("--------------------------------------------------");
            System.out.println("End of file reached.");
        } catch (IOException error) {
            System.err.println("Erro ao ler arquivo: " + error.getMessage());
        }
    }
}
