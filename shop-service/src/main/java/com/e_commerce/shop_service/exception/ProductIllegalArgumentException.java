package com.e_commerce.shop_service.exception;

public class ProductIllegalArgumentException extends RuntimeException{
    public ProductIllegalArgumentException(String message) {
        super(message);
    }

    public ProductIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductIllegalArgumentException(Throwable cause) {
        super(cause);
    }
}
