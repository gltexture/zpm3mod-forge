package ru.gltexture.zpm3.engine.exceptions;

public class ZPNullException extends ZPException {
    public ZPNullException() {
    }

    public ZPNullException(String ex) {
        super(ex);
    }

    public ZPNullException(String ex, Exception e) {
        super(ex, e);
    }

    public ZPNullException(Exception ex) {
        super(ex);
    }
}
