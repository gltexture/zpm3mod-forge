package ru.gltexture.zpm3.engine.exceptions;

public class ZPRuntimeException extends ZPException {
    public ZPRuntimeException() {
    }

    public ZPRuntimeException(String ex) {
        super(ex);
    }

    public ZPRuntimeException(String ex, Exception e) {
        super(ex, e);
    }

    public ZPRuntimeException(Exception ex) {
        super(ex);
    }
}
