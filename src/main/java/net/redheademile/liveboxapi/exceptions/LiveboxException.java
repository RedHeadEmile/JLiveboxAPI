package net.redheademile.liveboxapi.exceptions;

public class LiveboxException extends Exception {
    public LiveboxException(String cause) {
        super(cause);
    }

    public LiveboxException(Exception cause) {
        super(cause);
    }
}
