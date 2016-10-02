/**
 * Created by markbeussink on 9/30/16.
 */
public class Parser {

    private Lexer.Token nextToken;  // holds the next token
    private Lexer lxr;        // holds the Lexer object

    public class ParseError extends Exception{
        public ParseError(String errorMessage){
            super(errorMessage);
        }
    }

    public Parser(String input){
        lxr = new Lexer(input);
    }

    // Entry point for recusive parsing
    public void parse() throws ParseError{
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

    public void varInit() throws ParseError{
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

    public void condition()  throws ParseError{
        System.out.println("Enter <Condition>");

        // boolean literal
        if (nextToken == Lexer.Token.BOOL_LIT){
            nextToken = lxr.lex();
        }
        // boolean expression
        else {
            booleanExpression();
        }

        System.out.println("Exit <Condition>");
    }

    public void booleanExpression() throws ParseError{
        System.out.println("Enter <BooleanExpression>");

        // expr
        expr();
        // [<BooleanOp> <Expr>]
        if (nextToken == Lexer.Token.LOGIC_OP || nextToken == Lexer.Token.RELATIONAL_OP){
            nextToken = lxr.lex();
            // <Expr
            expr();
        } else {
            error();
        }

        System.out.println("Exit <BooleanExpression>");
    }

    public void expr() throws ParseError{
        System.out.println("Enter <Expr>");

        // [<PrePostOp>] <exp> [(<Infix_Op> <exp> | <PrePostOp>)]
        if (nextToken == Lexer.Token.PREPOST_OP){
            nextToken = lxr.lex();
        }

        exp();

        if (nextToken == Lexer.Token.INFIX_OP){
            nextToken = lxr.lex();
            exp();
        }
        else if (nextToken == Lexer.Token.PREPOST_OP){
            nextToken = lxr.lex();
        }
        System.out.println("Exit <Expr>");
    }

    public void exp() throws ParseError{
        System.out.println("Enter <Exp>");

        // <Term> | (<Expr>)
        if (nextToken == Lexer.Token.ID || nextToken == Lexer.Token.VALUE){
            nextToken = lxr.lex();
        }
        else if (nextToken == Lexer.Token.LEFT_PAREN){
            nextToken = lxr.lex();
            expr();
            if (nextToken == Lexer.Token.RIGHT_PAREN){
                nextToken = lxr.lex();
            } else {
                error();
            }
        }
        else {
            error();
        }
        System.out.println("Exit <Exp>");
    }


    public void error() throws ParseError{
        System.out.printf("ERROR: Invalid token: %s", nextToken.name());
        throw new ParseError("Invalid token " + nextToken.name() + "\n");
    }
}
