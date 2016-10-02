/**
 * Created by markbeussink on 9/30/16.
 */
public class Parser {

    private Lexer.Token nextToken;  // holds the current token
    private Lexer lexer;            // holds the Lexer object

    public Parser(String input){
        lexer = new Lexer(input);
    }

    // Entry point for recusive parsing
    public void parse(){
        System.out.println("Beginning parse.");
    }

    public void varInit(){
        System.out.println("Enter <VarInit>");

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
}
