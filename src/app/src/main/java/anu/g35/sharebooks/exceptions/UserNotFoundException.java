package anu.g35.sharebooks.exceptions;

/**
 * Exception for when username and password do not match any user
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-18
 */
public class UserNotFoundException extends Exception{
    private static final long serialVersionUID = 1L;
    public UserNotFoundException(String message) {
        super(message);
    }
}
