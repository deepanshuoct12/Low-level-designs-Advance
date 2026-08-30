package org.project.exceptions;

public class InvalidPriceRangeException extends RuntimeException {
    public InvalidPriceRangeException(String message) {
        super(message);
    }

    public InvalidPriceRangeException(double minPrice, double maxPrice) {
        super("Invalid price range: minPrice (" + minPrice + ") cannot be greater than maxPrice (" + maxPrice + ")");
    }
}
