/**
 * Created by markbeussink && Cameron Niccum 9/30/16.
 */

public class Lexer {

    private String input;      			// Holds the input string
    private String lexeme;     			// Holds the current lexeme
    private int position;     			// Holds the current index position of the analyzer
    private Token nextToken;   			// Holds the value of the output token
    private LexemeClass lexemeClass;	// Holds the classification of the lexeme

	// regex patterns for checking names and numbers
    private static final String NAME_PATTERN = "(\\w*)";
    private static final String NUM_PATTERN = "(\\d*)";
    private static final String LETTER_PATTERN = "(\\A[a-zA-z])";
	private static final String WHITESPACE_PATTERN = "(\\s)";


    // Token Codes
    public enum Token {

        INVALID_TOKEN,

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
    
    private enum LexemeClass {
    	NAME,
    	NUMBER,
    	SPECIAL,
    }

    public Lexer(String input) {
        this.input = input;
        position = 0;
    }

    public void setNewInput(String newInput){
        this.input = input;
    }

    public Token lex(){
    	// get the lexeme
    	lexeme = "";
    	getLexeme();
    	
    	switch (lexemeClass){
    	case NAME:
    		if (lexeme.equalsIgnoreCase("true") || lexeme.equalsIgnoreCase("false")){
    				nextToken = Token.BOOL_LIT;
    		}
    		else if (lexeme.equalsIgnoreCase("for")){
    			nextToken= Token.FOR_KEYWORD;
    		}
			else if (lexeme.equals("int") || lexeme.equals("long") || lexeme.equals("short") || lexeme.equals("byte")){
				nextToken = Token.TYPE;
			}
    		else {
    			nextToken = Token.ID;
    		}
    		break;
    	case NUMBER:
    		nextToken = Token.VALUE;
    		break;
    	case SPECIAL:
    		nextToken = lookUpChar(lexeme);
    		break;
    	}
    	System.out.printf("Lexeme is: %s, token is: %s, remaining length: %d\n",
				lexeme, nextToken.name(), (input.length() - position));
        return nextToken;
    }

    private Token lookUpChar(String lexeme){
    	if (lexeme.length() ==  1){
    		switch (lexeme.charAt(0)) {
            /* Grammar punctuation */
            	case '(':
                	return Token.LEFT_PAREN;
            	case ')':
                	return Token.RIGHT_PAREN;
            	case ';':
                	return Token.SEMI_COLON;
            /* Relational Operators */
            	case '>': // Handle >=
            		return Token.RELATIONAL_OP;
            	case '<': // Handle <=
                	return Token.RELATIONAL_OP;
            	case '!': // Handle !=
                    return Token.PREPOST_OP;
            	case '=': // Handle ==
                    return Token.ASSIGN_OP;
            /* Logical Operators */
            	case '|': // Handle ||
            		return Token.LOGIC_OP;
            	case '&': // Handle &&
                	return Token.LOGIC_OP;
            /* Infix Operators */
            	case '+': // Handle ++
                	return Token.INFIX_OP;
            	case '-': // Handle --
                	return Token.INFIX_OP;
            	case '*':
            		return Token.INFIX_OP;
            	case '/':
            		return Token.INFIX_OP;
            	case '%':
                	return Token.INFIX_OP;
            	default:
            		return Token.INVALID_TOKEN;
    		}
    	} 
    	else if (lexeme.length() == 2){
    		if (lexeme.equals("==") || lexeme.equals("!=") ||
    				lexeme.equals("&&") || lexeme.equals("||")){
    			return Token.LOGIC_OP;
    		}
    		else if (lexeme.equals("<=") || lexeme.equals("<=")){
    			return Token.RELATIONAL_OP;
    		}
    		else if (lexeme.equals("++") || lexeme.equals("--")){
    			return Token.PREPOST_OP;
    		}
    		else {
    			return Token.INVALID_TOKEN;
    		}
    	}
    	else {
    		return Token.INVALID_TOKEN;
    	}
    }

    public boolean isAtEnd(){
        return (position > input.length() - 1);
    }

    private void advance(){
    	if (!isAtEnd()){
    		position++;
    	}
    }
    
    private void getNonBlank(){
    	while (!(isAtEnd()) && (input.substring(position, position +1).matches(WHITESPACE_PATTERN))){
    		advance();
    	}
    }

    private void getLexeme() {
    	
    	getNonBlank();
    if((position +1)< input.length()){
    	//Checks for Letter/Number/Whitespace/Invalid_Token
    	if (input.substring(position, position +1).matches(LETTER_PATTERN)){
    		lexeme += input.charAt(position);
    		advance();
    		lexemeClass = LexemeClass.NAME;

    		while (input.substring(position, position + 1).matches(NAME_PATTERN)){
    			lexeme += input.charAt(position);
    			advance();
    		}
    	} else if (input.substring(position, position + 1).matches(NUM_PATTERN)){
    		lexeme += input.charAt(position);
    		advance();
    		lexemeClass = LexemeClass.NUMBER;
    		while (input.substring(position, position + 1).matches(NUM_PATTERN)){
    			lexeme += input.charAt(position);
    			advance();
    		}
    	} else if (!(input.substring(position, position + 1).matches(WHITESPACE_PATTERN))){
    		if (!(isAtEnd()) && lookUpChar(input.substring(position, position + 1)) != Token.INVALID_TOKEN){
    			lexeme += input.charAt(position);
    			advance();
    			lexemeClass = LexemeClass.SPECIAL;
    			if (lookUpChar(lexeme + input.charAt(position)) != Token.INVALID_TOKEN){
    				lexeme += input.charAt(position);
    				advance();
    			}
    		}
    		else {
    			lexeme += input.charAt(position);
        		lexemeClass = LexemeClass.SPECIAL;
        		advance();
    		}
    	}
    	else {
    		lexemeClass = LexemeClass.SPECIAL;
    		advance();
    	}
    }else{
    	// IF LETTER
    	if (("" + input.charAt(position)).matches(LETTER_PATTERN)){
    		lexemeClass = LexemeClass.NAME;
    	}
    	else if (("" + input.charAt(position)).matches(NUM_PATTERN)){
    		lexemeClass = LexemeClass.NUMBER;
    	}
    	else if (!("" + input.charAt(position)).matches(WHITESPACE_PATTERN)){
    		lexemeClass = LexemeClass.SPECIAL;
    	}
    	else {
    		lexemeClass = LexemeClass.SPECIAL;
    	}
    	
    	lexeme = "" + input.charAt(position);
    	advance();
    }
    }

}
