package anu.g35.sharebooks.data.model;

import androidx.annotation.NonNull;

/**
 * A generic class that holds a result success w/ data or an error exception.
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-15
 */
public class Result<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    private Result() {
    }

    @NonNull
    @Override
    public String toString() {
        if (this instanceof Result.Success) {
            Success<?> success = (Success<?>) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof Result.Error) {
            Error<?> error = (Error<?>) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "Unknown result type";
    }

    // Success sub-class
    public final static class Success<T> extends Result<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error<T> extends Result<T> {
        private final Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}