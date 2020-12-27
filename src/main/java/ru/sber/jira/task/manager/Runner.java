package ru.sber.jira.task.manager;

import com.atlassian.jira.rest.client.api.domain.IssueType;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ru.sber.jira.task.manager.service.JiraService;

@Slf4j
@Component
public class Runner implements ApplicationRunner {

    private final JiraService jiraService;

    @Autowired
    public Runner(JiraService jiraService) {
        this.jiraService = jiraService;
    }

    void createTestIssue() {
        IssueType issueType = jiraService.getIssueTypeByName("task");

        jiraService.createIssue(
                "PR", issueType, "Something went wrong"
        );
    }

    @Override
    public void run(ApplicationArguments args) {
        createTestIssue();
        createTestIssue();
        createTestIssue();
    }
}
