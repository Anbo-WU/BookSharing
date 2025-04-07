package anu.g35.sharebooks.data.search;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import anu.g35.sharebooks.exceptions.IllegalStringException;

/**
 * Tokenizer class to extract tokens from the search string
 * The search string is split into tokens based on the following rules:
 * 1. The search string can contain the following symbols: & | ( )
 * 2. The search string can contain the following keys: isbn, title, authors, categories, published_year
 * 3. The search string can contain the following values: any string
 * 4. The search string can contain multiple keys and values separated by colon(:)
 *
 * @author u7722376 Di'ao Fu
 * @since 2024-04-19
 */
public class Tokenizer {
    // String to be transformed into tokens each time next() is called.
    private String buffer;

    // The current token. The next token is extracted when next() is called.
    private Token currentToken;

    /**
     * Constructor of Tokenizer
     * The constructor extracts the first token and save it to currentToken
     *
     * @param text input search string
     */
    public Tokenizer(String text) {
        buffer = text;
        next();
    }

    /**
     * Extract the next token from the buffer
     * The token is saved to currentToken
     *
     * @throws IllegalStringException if the search string is illegal
     */
    public void next() {
        // remove whitespace
        buffer = buffer.trim();

        // if there's no string left, set currentToken null and return
        if (buffer.isEmpty()) {
            currentToken = null;
            return;
        }

        // Extract the first token
        int tokenLen = 1;
        char firstChar = buffer.charAt(0);
        if (firstChar == '&')
            currentToken = new Token("&", Token.Type.AND);
        else if (firstChar == '|')
            currentToken = new Token("|", Token.Type.OR);
        else if (firstChar == '(')
            currentToken = new Token("(", Token.Type.LBRA);
        else if (firstChar == ')')
            currentToken = new Token(")", Token.Type.RBRA);
        else {
            tokenLen = buildKeyToken();
        }

        // Remove the extracted token from buffer
        buffer = buffer.substring(tokenLen);
    }

    int buildKeyToken() {

        int tokenLen = 0;
        // Search-Invalid, give meaningful results
        if (!buffer.contains(":")) {
            rebuildBuffer();
        }

        while (tokenLen == 0) {
            // Split the string by colon
            // Check if the string contains any of the symbols "&|()"
            int index = indexOfSymbols(buffer);
            String[] strArray;
            // If there is no symbol, split the string by colon
            if (index == -1) {
                tokenLen = buffer.length();
                strArray = buffer.split(":");
            } else {
                tokenLen = index;
                strArray = buffer.substring(0, index).split(":");
            }

            if (strArray.length != 2) {
                rebuildBuffer();
                tokenLen = 0;
                continue;
            }

            // Extract key and value
            String key = strArray[0].trim().toLowerCase();
            String value = strArray[1].trim();

            // Search-Invalid, give meaningful results
            if (!key.equals("isbn") && !key.equals("title") && !key.equals("authors") &&
                    !key.equals("category") && !key.equals("year")){
                rebuildBuffer();
                tokenLen = 0;
                continue;
            }

            // ISBN, TITLE, AUTHORS, CATEGORY, YEAR
            switch (key) {
                case "isbn":
                    currentToken = new Token(value, Token.Type.ISBN);
                    break;
                case "title":
                    currentToken = new Token(value, Token.Type.TITLE);
                    break;
                case "authors":
                    currentToken = new Token(value, Token.Type.AUTHORS);
                    break;
                case "category":
                    currentToken = new Token(value, Token.Type.CATEGORY);
                    break;
                case "year":
                    currentToken = new Token(value, Token.Type.YEAR);
                    break;
            }
        }
        return tokenLen;
    }

    void rebuildBuffer() {
        //  buffer should start with a key, introduce a key
        // string length is 13 and all characters are numbers, it's ISBN
        if (buffer.length() == 13 && buffer.matches("[0-9]+")) {
            buffer = "ISBN:" + buffer;

            // string length is 4 and all characters are numbers, it's YEAR
        } else if (buffer.length() == 4 && buffer.matches("[0-9]+"))
            buffer = "YEAR:" + buffer;

            // Otherwise, it's TITLE or AUTHORS
        else {
            String regex = "[&|():]";
            buffer = buffer.replaceAll(regex, " ");
            buffer = "TITLE:" + buffer + "|AUTHORS:" + buffer;
        }
    }

    /**
     * Get the current token extracted by next()
     *
     * @return the current token
     */
    public Token current() {
        return currentToken;
    }


    /**
     * Check if there is a next token
     *
     * @return true if there is a next token, false otherwise
     */
    public boolean hasNext() {
        return currentToken != null;
    }

    /**
     * Find the index of the first occurrence of any of the symbols "&|()"
     *
     * @param str input string
     * @return index of the first occurrence of any of the symbols "&|()", -1 if not found
     */
    private static int indexOfSymbols(String str) {
        // Construct the regular expression
        String regex = "[&|()]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        // Find the matching character
        if (matcher.find()) {
            return matcher.start();
        } else {
            return -1;
        }
    }
}
