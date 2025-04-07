package anu.g35.sharebooks.data.search;

import anu.g35.sharebooks.exceptions.IllegalStringException;


/**
 * Parser: it is used to parse the search string and build the expression tree.
 * It parser the following grammar rule:
 * <exp>  ::=  <term> / <term> & <exp> / <term> | <exp>
 * <term> ::= <key> / ( <exp> )
 * <key>  ::= ISBN / TITLE / AUTHORS / CATEGORY / YEAR
 *
 * @author u7722376 Di'ao Fu
 * @since 2024-04-20
 */
public class Parser {

    // Tokenizer to tokenize the search string.
    Tokenizer tokenizer;

    /**
     * Constructor
     *
     * @param tokenizer: Tokenizer object.
     */
    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     * Build the expression tree.
     *
     * @return type: Expression.
     */
    public Expression buildExp() {
        Expression expression = parseExp();
        if ( tokenizer.hasNext()) {
            throw new IllegalStringException("Mismatched parentheses");
        }
       return expression;
    }

    /**
     * Adheres to the grammar rule:
     * <exp>    ::= <term> / <term> & <exp> / <term> | <exp>
     *
     * @return type: Expression.
     */
    private Expression parseExp() {


        Expression term = parseTerm();
        if (tokenizer.hasNext()) {
            Token token = tokenizer.current();
            if (token.getType() == Token.Type.AND) {
                tokenizer.next(); // consume '&'
                Expression expression = parseExp();
                return new AndExpression(term, expression);

            } else if (token.getType() == Token.Type.OR) {
                tokenizer.next(); // consume '|'
                Expression expression = parseExp();
                return new OrExpression(term, expression);
            }
        }
        return term;
    }


    /**
     * Adheres to the grammar rule:
     * <Term> ::= <key> | ( <exp> )
     *
     * @return type: Expression.
     */
    private Expression parseTerm() {

        Token token = tokenizer.current();
        Token.Type type = token.getType();
        if (type == Token.Type.ISBN || type == Token.Type.TITLE || type == Token.Type.AUTHORS ||
                type == Token.Type.CATEGORY || type == Token.Type.YEAR) {
            tokenizer.next(); // consume key:string
            return new KeyExpression(token);

        } else if (token.getType() == Token.Type.LBRA) {
            tokenizer.next(); // consume '('
            Expression expression = parseExp();
            if (!tokenizer.hasNext() || tokenizer.current().getType() != Token.Type.RBRA) {
                // Expected ')', Search-Invalid, give meaningful results, assume it's a typo
            } else {
                tokenizer.next(); // consume ')'
            }

            return expression;
        } else {
            // Expected key or '(', Search-Invalid, , give meaningful results
            // In the evaluate method of KeyExpression, unrecognized tokens will be ignored.
            return new KeyExpression(token);
        }
    }
}
