import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalisadorLexico {
    private String entrada;
    private int posicaoDoCaractere;
    private List<Token> listaDeTokens;

    public AnalisadorLexico(String entrada) {
        this.entrada = entrada;
        this.posicaoDoCaractere = 0;
        this.listaDeTokens = new ArrayList<>();
    }

    public List<Token> analisar() throws IOException {
        while (posicaoDoCaractere < entrada.length()) {
            char caractere = entrada.charAt(posicaoDoCaractere);

            if (Character.isWhitespace(caractere)) {
                posicaoDoCaractere++;
                continue;
            }

            if (Character.isDigit(caractere) || caractere == '.') {
                lerDigito();
            } else if (isOperator(caractere)) {
                lerOperador();
            } else if (caractere == '(' || caractere == ')') {
                lerParentesis();
            } else {
                throw new IOException("[ERRO] Caractere '" + caractere + "' não reconhecido na posição "
                        + posicaoDoCaractere + " da cadeia de entrada.");
            }
        }

        listaDeTokens.add(new Token("EOF", "Fim da entrada"));
        return listaDeTokens;
    }

    private void lerDigito() {
        StringBuilder digito = new StringBuilder();
        boolean temPontoFlutuante = false;

        while (posicaoDoCaractere < entrada.length()) {
            char caractere = entrada.charAt(posicaoDoCaractere);

            if (Character.isDigit(caractere)) {
                digito.append(caractere);
                posicaoDoCaractere++;
            } else if (caractere == '.' && temPontoFlutuante == false) {
                digito.append(caractere);
                temPontoFlutuante = true;
                posicaoDoCaractere++;
            } else {
                break;
            }
        }

        String tipoDoToken = temPontoFlutuante ? "NUM_REAL" : "NUM_INT";
        String valorDoToken = digito.toString();
        listaDeTokens.add(new Token(tipoDoToken, valorDoToken));
    }

    private void lerOperador() {
        char caractere = entrada.charAt(posicaoDoCaractere);
        String tipoDoToken = "";

        switch (caractere) {
            case '+':
                tipoDoToken = "OP_SUM";
                break;
            case '-':
                tipoDoToken = "OP_SUB";
                break;
            case '*':
                tipoDoToken = "OP_MUL";
                break;
            case '/':
                tipoDoToken = "OP_DIV";
                break;
        }

        listaDeTokens.add(new Token(tipoDoToken, String.valueOf(caractere)));
        posicaoDoCaractere++;
    }

    private void lerParentesis() {
        char caractere = entrada.charAt(posicaoDoCaractere);
        String tipoDoToken = (caractere == '(') ? "PAR_OPEN" : "PAR_CLOSE";
        listaDeTokens.add(new Token(tipoDoToken, String.valueOf(caractere)));
        posicaoDoCaractere++;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
}