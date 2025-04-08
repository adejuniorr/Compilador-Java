import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        String filePath = "src/CodeSample.txt";
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            StringBuilder entrada = new StringBuilder();
            String currentLine;

            System.out.println("Reading file: " + fileName);
            System.out.println("--------------------------------------------------");

            while ((currentLine = br.readLine()) != null) {
                lineNumber++;
                entrada.append(currentLine).append("\n");
                System.out.println(String.format("%-4d| %s", lineNumber, currentLine));
            }

            System.out.println("--------------------------------------------------");

            AnalisadorLexico analisador = new AnalisadorLexico(entrada.toString());
            System.out.println("Tokens encontrados:");
            
            for (Token token : analisador.analisar()) {
                System.out.println(token);
            }

            System.out.println("--------------------------------------------------");
            System.out.println("End of file reached.");
        } catch (IOException error) {
            System.err.println("Erro ao ler arquivo: " + error.getMessage());
        }
    }
}
