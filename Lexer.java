/**
 * Created by markbeussink on 9/30/16.
 */

public class Lexer {

    String input;      // Holds the input string
    int position;      // Holds the current index position of the analyzer
    Token token;       // Holds the current token
    Token nextToken;   // Holds the
    static final String NAME_PATTERN = "(\\A[a-zA-z])(\\w*)";
    static final String NUM_PATTERN = "(\\d*)";



    // Token Codes
    public enum Token {

        UNVALID_TOKEN,

        // Grammar symbols
        RIGHT_PAREN,
        LEFT_PAREN,
        SEMI_COLON ,

        // Operators
        RELATIONAL_OP,
        LOGIC_OP,
        ASSIGN_OP,
        INFIX_OP,
        PREPOST_OP,

        // Keywords and Terminals
        FOR_KEYWORD,
        ID,
        VALUE,
        BOOL_LIT,
        TYPE
    }

    public Lexer(String input) {
        this.input = input;
        position = 0;
    }

    public Token lex(){
        return nextToken;
    }

    public void lookUpChar(char c){
        switch (c) {
            /* Grammar punctuation */
            case '(':
                nextToken = Token.LEFT_PAREN;
                break;
            case ')':
                nextToken = Token.RIGHT_PAREN;
                break;
            case ';':
                nextToken = Token.SEMI_COLON;
                break;
            /* Relational Operators */
            case '>': // Handle >=
                if (input.charAt(position + 1) == '=') {
                    position++;
                }
            case '<': // Handle <=
                if (input.charAt(position + 1) == '=') {
                    position++;
                }
                nextToken = Token.RELATIONAL_OP;
                break;
            case '!': // Handle !=
                if (input.charAt(position + 1) == '=') {
                    position++;
                }
                else {
                    nextToken = Token.PREPOST_OP;
                    break;
                }
            case '=': // Handle ==
                if (input.charAt(position + 1) == '=') {
                    position++;
                    nextToken = Token.RELATIONAL_OP;
                    break;
                }
                else {
                    nextToken = Token.ASSIGN_OP;
                    break;
                }
                /* Logical Operators */
            case '|': // Handle ||
                if (input.charAt(position + 1) == '|'){
                    position++;
                }
            case '&': // Handle &&
                if (input.charAt(position + 1) == '&'){
                    position++;
                }
                nextToken = Token.LOGIC_OP;
                break;
            /* Infix Operators */
            case '+': // Handle ++
                if (input.charAt(position + 1) == '+'){
                    position++;
                    nextToken = Token.PREPOST_OP;
                    break;
                }
            case '-': // Handle --
                if (input.charAt(position + 1) == '-'){
                    position++;
                    nextToken = Token.PREPOST_OP;
                    break;
                }
            case '*':
            case '/':
            case '%':
                nextToken = Token.INFIX_OP;
                break;
            default:
                nextToken = Token.UNVALID_TOKEN;
        }
    }



    public void advance() {
        while (input.charAt(position) == ' '){
            position++;
        }
    }
}
