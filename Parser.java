/**
 * Created by markbeussink on 9/30/16.
 */
public class Parser {

    private Lexer.Token nextToken;  // holds the next token
    private Lexer lxr;        // holds the Lexer object

    public Parser(String input){
        lxr = new Lexer(input);
    }

    // Entry point for recusive parsing
    public void parse(){
        System.out.println("Beginning parse.");

        //for keyword
        nextToken = lxr.lex();
        if (nextToken == Lexer.Token.FOR_KEYWORD){
            // (
            nextToken = lxr.lex();
            if (nextToken == Lexer.Token.LEFT_PAREN){
                nextToken = lxr.lex();

                // <VarInit>!
                varInit();
                // ;
                if (nextToken == Lexer.Token.SEMI_COLON){
                    nextToken = lxr.lex();
                    // [<Condition>]
                    condition();
                    // ;
                    if (nextToken == Lexer.Token.SEMI_COLON){
                        nextToken = lxr.lex();
                        // [<Expr>]
                        expr();
                        // )
                        if (nextToken == Lexer.Token.RIGHT_PAREN){

                        } else {
                            error();
                        }
                    } else {
                        error();
                    }

                } else {
                    error();
                }

            } else {
                error();
            }
        } else {
            error();
        }
        System.out.println("Finished parse.");
    }

    public void varInit(){
        System.out.println("Enter <VarInit>");

        // [Type] id = value
        if (nextToken == Lexer.Token.TYPE) {
            nextToken = lxr.lex();

            // id
            if (nextToken == Lexer.Token.ID){
                nextToken = lxr.lex();

                // =
                if (nextToken == Lexer.Token.ASSIGN_OP){
                    nextToken = lxr.lex();

                    // value
                    if (nextToken == Lexer.Token.VALUE){
                        nextToken = lxr.lex();
                    } else {
                        error();
                    }
                } else {
                    error();
                }
            } else {
                error();
            }
        }

        // id = value
        else if(nextToken == Lexer.Token.ID) {
            nextToken = lxr.lex();

            // =
            if (nextToken == Lexer.Token.ASSIGN_OP){
                nextToken = lxr.lex();

                // value
                if (nextToken == Lexer.Token.VALUE){
                    nextToken = lxr.lex();
                } else {
                    error();
                }
            } else {
                error();
            }
        }
        else {
            error();
        }
        System.out.println("Exit <VarInit>");
    }

    public void condition(){
        System.out.println("Enter <Condition>");

        System.out.println("Exit <Condition>");
    }

    public void booleanLiteral(){
        System.out.println("Enter <BooleanLiteral>");

        System.out.println("Exit <BooleanLiteral>");
    }

    public void booleanExpression(){
        System.out.println("Enter <BooleanExpression>");

        System.out.println("Exit <BooleanExpression>");
    }

    public void expr(){
        System.out.println("Enter <Expr>");

        System.out.println("Exit <Expr>");
    }

    public void exp(){
        System.out.println("Enter <Exp>");

        System.out.println("Exit <Exp>");

    }

    public void term(){
        System.out.println("Enter <Term>");

        System.out.println("Exit <Term>");
    }

    public void error(){
        System.out.printf("ERROR: Invalid token: %s", nextToken.name());
    }
}
