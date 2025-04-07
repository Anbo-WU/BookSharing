package anu.g35.sharebooks.data.search;

import java.util.Objects;

/**
 * Token class to save extracted token from tokenizer.
 * The following are the different types of tokens:
 *
 * ISBN: ISBN of the book
 * TITLE: Title of the book
 * AUTHORS: Authors of the book
 * CATEGORY: Category of the book
 * YEAR: Year the book was published
 * AND: &
 * OR: |
 * LBRA: (
 * RBRA: )
 *
 * @author u7722376 Di'ao Fu
 * @since 2024-04-19
 */
public class Token {

    // The following enum defines different types of tokens.
    public enum Type {
        ISBN("ISBN"),
        TITLE("TITLE"),
        AUTHORS("AUTHORS"),
        CATEGORY("CATEGORY"),
        YEAR("YEAR"),
        AND("&"),
        OR("|"),
        LBRA("("),
        RBRA(")");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }



    // Token representation in String form.
    private final String token;

    // Type of the token.
    private final Type type;

    public Token(String token, Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", type=" + type +
                '}';
    }

}
