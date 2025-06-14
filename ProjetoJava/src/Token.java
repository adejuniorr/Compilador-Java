public class Token {
    private String tipoDoToken;
    private String valorDoToken;

    public Token(String tipoDoToken, String valorDoToken) {
        this.tipoDoToken = tipoDoToken;
        this.valorDoToken = valorDoToken;
    }

    public String getTipo() {
        return tipoDoToken;
    }

    public String getValor() {
        return valorDoToken;
    }

    @Override
    public String toString() {
        return "Token [tipoDoToken='" + tipoDoToken + "', valorDoToken='" + valorDoToken + "']";
    }
}