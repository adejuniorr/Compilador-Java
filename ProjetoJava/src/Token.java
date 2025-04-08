public class Token {
    private String tipoDoToken;
    private String valorDoToken;

    public Token(String tipoDoToken, String valorDoToken) {
        this.tipoDoToken = tipoDoToken;
        this.valorDoToken = valorDoToken;
    }

    public String getTipoDoToken() {
        return tipoDoToken;
    }

    public String getValorDoToken() {
        return valorDoToken;
    }

    @Override
    public String toString() {
        return "Token [tipoDoToken='" + tipoDoToken + "', valorDoToken='" + valorDoToken + "']";
    }
}