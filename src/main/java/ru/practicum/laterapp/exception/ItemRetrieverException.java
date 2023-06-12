package ru.practicum.laterapp.exception;

public class ItemRetrieverException extends RuntimeException {
    public ItemRetrieverException(String message) {
        super(message);
    }
    public ItemRetrieverException(String message, Exception e) {
        super(message, e);
    }
}
