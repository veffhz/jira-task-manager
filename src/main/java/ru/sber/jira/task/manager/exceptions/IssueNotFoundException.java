package ru.sber.jira.task.manager.exceptions;

public class IssueNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Issue not found!";

    public IssueNotFoundException() {
        super(MESSAGE);
    }

    public IssueNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public IssueNotFoundException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
