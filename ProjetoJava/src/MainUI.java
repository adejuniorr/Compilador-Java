import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class MainUI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Cole o caminho completo do arquivo de entrada:");
        String caminhoArquivo = scanner.nextLine().trim();

        try {
            String conteudo = lerArquivoComoString(caminhoArquivo);

            AnalisadorLexico analisador = new AnalisadorLexico(conteudo);
            List<Token> tokens = analisador.analisar(0);
            gerarTabelaHTML(tokens);
            System.out.println("✅ Arquivo 'tokens.html' gerado com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo ou gerar tokens: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static String lerArquivoComoString(String caminho) throws IOException {
        return Files.readString(new File(caminho).toPath());
    }

    private static void gerarTabelaHTML(List<Token> tokens) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("""
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <title>Lista de Tokens</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #f9f9f9;
                        padding: 0;
                        position: relative;
                        margin: 0;
                    }
                    h1 {
                        text-align: center;
                        font-size: 28px;
                        position: sticky;
                        background-color: #f9f9f9;
                        top: 0;
                        margin: 0;
                        padding: 20px 0; 
                    }
                    table {
                        width: 80%;
                        margin: 0 auto;
                        border-collapse: collapse;
                        font-size: 20px;
                    }
                    .table_header {
                        position: sticky;
                        background-color: #f9f9f9;
                        top: 72px;
                    }
                    th, td {
                        padding: 16px 24px;
                        text-align: left;
                        border-bottom: 1px solid #ddd;
                    }
                    th {
                        background-color: #f0f0f0;
                    }
                    tr:hover {
                        background-color: #f1f1f1;
                    }
                </style>
            </head>
            <body>
                <h1>Lista de Tokens</h1>
                <table>
                    <tr class="table_header"><th>Tipo</th><th>Valor</th></tr>
        """);

        for (Token token : tokens) {
            html.append("<tr>")
                .append("<td>").append(token.getTipo()).append("</td>")
                .append("<td>").append(token.getValor()).append("</td>")
                .append("</tr>");
        }

        html.append("""
                </table>
            </body>
            </html>
        """);

        try (FileWriter writer = new FileWriter("tokens.html")) {
            writer.write(html.toString());
        }
    }
}
