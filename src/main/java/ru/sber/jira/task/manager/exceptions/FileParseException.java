package ru.sber.jira.task.manager.exceptions;

public class FileParseException extends RuntimeException {

    private static final String MESSAGE = "File Parse error!";

    public FileParseException() {
        super(MESSAGE);
    }

    public FileParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileParseException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
