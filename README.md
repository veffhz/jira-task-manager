# jira-task-manager 

Сервис для создания задач в Jira на основе исходных данных в JSON файле.
JSON файл имеет простую структуру и содержит информацию (имя и тип) о задачах в проекте:

```json
{
  "ProjectKey": "PR",
  "Issues": [
    {
      "type": "task",
      "name": "Issue 1"
    },
    {
      "type": "task",
      "name": "Issue 2"
    },
    {
      "type": "task",
      "name": "Issue 3"
    }
  ]
}
```

Anonymous user allow access to issue list:
https://test-task-2020.atlassian.net/jira/software/c/projects/PR/issues



jira-manager.language: en - Иначе ответы api будет на русском!

http://localhost:8082/swagger-ui/index.html