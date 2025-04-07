package anu.g35.sharebooks.exceptions;

/**
 * The following exception should be thrown if a search string is not valid.
 *  * @author u7703248 Chuang Ma
 *  * @since 2024-04-18
 */
public class IllegalStringException extends IllegalArgumentException {
    public IllegalStringException(String errorMessage) {
        super(errorMessage);
    }
}