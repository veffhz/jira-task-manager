# Jira-task-manager 

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

* Тестовый Jira проект доступен по ссылке https://test-task-2020.atlassian.net/browse/PR

* По данной ссылке разрешен анонимный доступ к issues:  
https://test-task-2020.atlassian.net/jira/software/c/projects/PR/issues

* В профиле пользователя test_user_811@bk.ru (в Jira) 
  включен английский язык - иначе ответы api jira будут на русском ("задача" вместо "task", например).  
  При дальнейшем развитиии сервиса нужно будет включить русский язык в enum Language. 

* Аtlassian предоставляет зависимость для тестов `jira-rest-java-client-test`, но она тянет много библиотек и сейчас закомментирована.
  При дальнейшем развитии сервиса нужно будет использовать ее вместо моков класса Mocks

### настройки сервиса (application.yml)
* Порт 8082 указан в секции server, port
* Лог файл пишется в директории logs диреткории приложения (секция logging в application.yml)
* Параметры Jira задаются в секции jira-manager: url, username, apiToken (вместо пароля), language (в данный момент только en)

#### требования:
Java 8, 11; Maven (опционально).

### сборка и запуск проекта
`mvn clean package` (если Maven не установлен `./mvnw clean package`, или `mvnw.cmd clean package` для win)

Протестировать api можно через swagger (OpenApi):  
http://localhost:8082/swagger-ui/index.html

Api содержит два эндпоинта - один для загрузки json, 
другой для загрузки json файла (пример файла есть в ресурсах проекта - InputFile.json)