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

    public List<Token> analisar(int lineNumber) throws IOException {
        while (posicaoDoCaractere < entrada.length()) {
            char caractere = entrada.charAt(posicaoDoCaractere);

            if (caractere == '(') {
                listaDeTokens.add(new Token("DELIMITADOR_ABRE_PARENTESES", "("));
                posicaoDoCaractere++;
                continue;
            } else if (caractere == ')') {
                listaDeTokens.add(new Token("DELIMITADOR_FECHA_PARENTESES", ")"));
                posicaoDoCaractere++;
                continue;
            } else if (caractere == '{') {
                listaDeTokens.add(new Token("DELIMITADOR_ABRE_CHAVE", "{"));
                posicaoDoCaractere++;
                continue;
            } else if (caractere == '}') {
                listaDeTokens.add(new Token("DELIMITADOR_FECHA_CHAVE", "}"));
                posicaoDoCaractere++;
                continue;
            }

            if (caractere == ';') {
                listaDeTokens.add(new Token("DELIMITADOR_PONTO_VIRGULA", ";"));
                posicaoDoCaractere++;
                continue;
            }

            if (caractere == ',') {
                listaDeTokens.add(new Token("DELIMITADOR_VIRGULA", ","));
                posicaoDoCaractere++;
                continue;
            }

            if (Character.isWhitespace(caractere)) {
                posicaoDoCaractere++;
                continue;
            }

            if (Character.isDigit(caractere)) {
                lerNumero();
            } else if (caractere == ':' && peek() == '=') {
                posicaoDoCaractere += 2;
                listaDeTokens.add(new Token("ATRIBUICAO", ":="));
            } else if (caractere == '"') {
                lerString();
            } else if (caractere == '{') {
                lerComentario();
            } else if (isOperadorRelacionalInicio(caractere)) {
                lerOperadorRelacional();
            } else if (isOperadorAritmetico(caractere)) {
                lerOperadorAritmetico();
            } else if (Character.isLetter(caractere)) {
                lerIdentificadorOuPalavraChave();
            } else {
                throw new IOException("[ERRO] Caractere '" + caractere + "' não reconhecido na posição "
                        + posicaoDoCaractere + ", linha " + lineNumber + " da cadeia de entrada.");
            }
        }

        listaDeTokens.add(new Token("EOF", "Fim da entrada"));
        return listaDeTokens;
    }

    private void lerNumero() {
        StringBuilder sb = new StringBuilder();

        if (entrada.charAt(posicaoDoCaractere) == '-') {
            sb.append('-');
            posicaoDoCaractere++;
        }

        while (posicaoDoCaractere < entrada.length() && Character.isDigit(entrada.charAt(posicaoDoCaractere))) {
            sb.append(entrada.charAt(posicaoDoCaractere));
            posicaoDoCaractere++;
        }

        listaDeTokens.add(new Token("NUM_INT", sb.toString()));
    }

    private void lerString() throws IOException {
        StringBuilder sb = new StringBuilder();
        posicaoDoCaractere++; // pula aspas iniciais

        while (posicaoDoCaractere < entrada.length()) {
            char c = entrada.charAt(posicaoDoCaractere);
            if (c == '"') {
                posicaoDoCaractere++;
                listaDeTokens.add(new Token("STRING", sb.toString()));
                return;
            } else {
                sb.append(c);
                posicaoDoCaractere++;
            }
        }

        throw new IOException("[ERRO] String não fechada.");
    }

    private void lerComentario() throws IOException {
        posicaoDoCaractere++; // pula '{'
        while (posicaoDoCaractere < entrada.length() && entrada.charAt(posicaoDoCaractere) != '}') {
            posicaoDoCaractere++;
        }

        if (posicaoDoCaractere < entrada.length() && entrada.charAt(posicaoDoCaractere) == '}') {
            posicaoDoCaractere++;
            ; // pula '}'
        } else {
            throw new IOException("[ERRO] Comentário não fechado na posição " + posicaoDoCaractere + ".");
        }
    }

    private void lerOperadorRelacional() {
        char atual = entrada.charAt(posicaoDoCaractere);
        char proximo = peek();
        String operador;

        if (atual == '<' && proximo == '>') {
            operador = "<>";
            posicaoDoCaractere += 2;
        } else if (atual == '<' && proximo == '=') {
            operador = "<=";
            posicaoDoCaractere += 2;
        } else if (atual == '>' && proximo == '=') {
            operador = ">=";
            posicaoDoCaractere += 2;
        } else {
            operador = String.valueOf(atual);
            posicaoDoCaractere++;
        }

        listaDeTokens.add(new Token("OP_RELACIONAL", operador));
    }

    private void lerOperadorAritmetico() {
        char caractere = entrada.charAt(posicaoDoCaractere);
        String tipoDoToken;

        switch (caractere) {
            case '+':
                tipoDoToken = "OP_SOMA";
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
            default:
                tipoDoToken = "DESCONHECIDO";
                break;
        }

        listaDeTokens.add(new Token(tipoDoToken, String.valueOf(caractere)));
        posicaoDoCaractere++;
    }

    private void lerIdentificadorOuPalavraChave() {
        StringBuilder sb = new StringBuilder();

        while (posicaoDoCaractere < entrada.length()
                && (Character.isLetterOrDigit(entrada.charAt(posicaoDoCaractere)))) {
            sb.append(entrada.charAt(posicaoDoCaractere));
            posicaoDoCaractere++;
        }

        String palavra = sb.toString().toLowerCase();

        String tipoDoToken;

        switch (palavra) {
            case "if":
            case "else":
            case "while":
            case "do":
            case "read":
            case "write":
            case "package":
            case "import":
            case "public":
            case "private":
            case "protected":
            case "class":
                tipoDoToken = "PALAVRA_CHAVE";
                break;
            case "int":
                tipoDoToken = "INDENTIFICADOR_TIPO_INTEIRO";
                break;
            case "true":
            case "false":
                tipoDoToken = "BOOLEANO";
                break;
            default:
                tipoDoToken = "IDENTIFICADOR";
                break;
        }

        listaDeTokens.add(new Token(tipoDoToken, palavra));
    }

    private char peek() {
        if (posicaoDoCaractere + 1 < entrada.length()) {
            return entrada.charAt(posicaoDoCaractere + 1);
        } else {
            return ' ';
        }
    }

    private boolean isOperadorAritmetico(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean isOperadorRelacionalInicio(char c) {
        return c == '<' || c == '>' || c == '=';
    }
}
