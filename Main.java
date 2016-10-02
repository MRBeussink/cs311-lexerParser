/**
 * Created by markbeussink on 9/30/16.
 */
public class Main {

    public static void main(String[] args) {
       Lexer lexer = new Lexer("for (int i = 0; i < count; i++)");

        do {
            lexer.lex();
        } while (!lexer.isAtEnd());
        
    }
}
