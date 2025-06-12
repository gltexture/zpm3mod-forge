package ru.gltexture.zpm3.engine.exceptions;

public class ZPIOException extends ZPException {
    public ZPIOException() {
    }

    public ZPIOException(String ex) {
        super(ex);
    }

    public ZPIOException(String ex, Exception e) {
        super(ex, e);
    }

    public ZPIOException(Exception ex) {
        super(ex);
    }
}
