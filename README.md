## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

## Список выполненных задач:

<details>
    <summary><H3>Задача 1</H3></summary>
    Разобраться со структурой проекта (onboarding).
</details>

- [x] Сделано

<details>
    <summary><H3>Задача 2</H3></summary>
    Удалить социальные сети: vk, yandex. <span style="border: 1px solid #4CAF50; background-color: #4CAF50; color: white; padding: 2px 4px; border-radius: 3px;">Easy task</span>
</details>

- [x] Сделано

<details>
    <summary><H3>Задача 3</H3></summary>
    Вынести чувствительную информацию в отдельный проперти файл:

    - логин
    - пароль БД
    - идентификаторы для OAuth регистрации/авторизации
    - настройки почты

Значения этих проперти должны считываться при старте сервера из переменных окружения
машины. <span style="border: 1px solid #4CAF50; background-color: #4CAF50; color: white; padding: 2px 4px; border-radius: 3px;">
Easy task</span>
</details>

- [x] Сделано

<details>
    <summary><H3>Задача 4</H3></summary>
    Переделать тесты так, чтоб во время тестов использовалась <strong>in memory БД (H2)</strong>, а не PostgreSQL. Для этого нужно определить 2 бина, и выборка какой из них использовать должно определяться активным профилем Spring. H2 не поддерживает все фичи, которые есть у PostgreSQL, поэтому тебе прийдется немного упростить скрипты с тестовыми данными.
</details>

- [ ]  В процессе

<details>
    <summary><H3>Задача 5</H3></summary>
    Написать тесты для всех публичных методов контроллера <span style="border: 1px solid black; padding: 2px 4px; border-radius: 3px;">ProfileRestController</span>. Хоть методов только 2, но тестовых методов должно быть больше, т.к. нужно проверить success and unsuccess path.
</details>

- [x] Сделано

<details>
    <summary><H3>Задача 6</H3></summary>
    Сделать рефакторинг метода <span style="border: 1px solid black; padding: 2px 4px; border-radius: 3px;">com.javarush.jira.bugtracking.attachment.FileUtil#upload</span> чтоб он использовал современный подход для работы с файловой системмой. <span style="border: 1px solid #4CAF50; background-color: #4CAF50; color: white; padding: 2px 4px; border-radius: 3px;">Easy task</span>
</details>

- [x] Сделано

<details>
    <summary><H3>Задача 7</H3></summary>
    Добавить новый функционал: добавления тегов к задаче (REST API + реализация на сервисе). Фронт делать необязательно. Таблица <span style="border: 1px solid black; padding: 2px 4px; border-radius: 3px;">task_tag</span> уже создана.

</details>

- [x] Сделано

<details>
    <summary><H3>Задача 8</H3></summary>
Добавить подсчет времени сколько задача находилась в работе и тестировании. Написать 2 метода на уровне сервиса, которые параметром принимают задачу и возвращают затраченное время:
<ul>
  <li>Сколько задача находилась в работе (ready_for_review минус in_progress ).</li>
  <li>Сколько задача находилась на тестировании (done минус ready_for_review).</li>
</ul>
Для написания этого задания, нужно добавить в конец скрипта инициализации базы данных changelog.sql 3 записи в таблицу ACTIVITY
<p><span style="border: 1px solid black; padding: 2px 4px; border-radius: 3px;">insert into ACTIVITY ( ID, AUTHOR_ID, TASK_ID, UPDATED, STATUS_CODE ) values ...</span>
<p>Со статусами:
<ul>
  <li>время начала работы над задачей – <strong>in_progress</strong></li>
  <li>время окончания разработки - <strong>ready_for_review</strong></li>
  <li>время конца тестирования - <strong>done</strong></li>
</ul>
</details>

- [x] Сделано

<details>
    <summary><H3>Задача 9</H3></summary>
Написать <span style="border: 1px solid black; padding: 2px 4px; border-radius: 3px;">Dockerfile</span> для основного сервера
</details>

- [x] Сделано

<details>
    <summary><H3>Задача 10</H3></summary>
Написать <span style="border: 1px solid black; padding: 2px 4px; border-radius: 3px;">docker-compose</span> файл для запуска контейнера сервера вместе с БД и nginx. Для nginx используй конфиг-файл <span style="border: 1px solid black; padding: 2px 4px; border-radius: 3px;">config/nginx.conf</span>. При необходимости файл конфига можно редактировать. <span style="border: 1px solid #f1950d; background-color: #f1940b; color: white; padding: 2px 4px; border-radius: 3px;">Hard task</span>

<details>
    <summary><H3>Запуск приложения через Docker</H3></summary>
    <ol>
        <li>Клонировать себе на машину проект</li>
        <li>Убедиться, что свободны следующие порты:
            <ul>
                <li>80 - Nginx</li>
                <li>8080 - Java - приложение</li>
                <li>5050 - pgAdmin</li>
                <li>5432 - PostgreSQL</li>
            </ul>
        </li>
        <li>Выполнить команду в консоли docker-compose up</li>
        <li>Дождаться билда приложения (1-3 минуты) и запуска всех контейнеров</li>
        <li>Открыть приложение в браузере по адресу http://localhost или http://localhost:80</li>
    </ol>
    <H4>Примечания:</H4>
    <ul>
    <li>Доступ к приложению напрямую через порт: 8080 закрыт. Доступ возможен только через nginx порт</li>
    <li>pgAdmin находится по адресу http://localhost:5050
            <ul>
                <li>login: admin@admin.com</li>
                <li>password: root</li>
            </ul>
    </li>
    <li>Во время запуска приложения произойдет популяция БД для работы. Если точнее – накатится структура и словари. Чтоб «посмотреть» как работает приложение нужно выполнить скрипт data.sql из resources/data4dev.</li>
    <li>При изменении кода необходимо пересобрать приложение командой docker-compose down и docker-compose build</li>
    </ul>

</details>


</details>

- [x] Сделано

<details>
    <summary><H3>Задача 11</H3></summary>
    Добавить локализацию минимум на двух языках для шаблонов писем (mails) и стартовой страницы <span style="border: 1px solid black; padding: 2px 4px; border-radius: 3px;">index.html</span>.
</details>

- [ ]  В процессе

<details>
    <summary><H3>Задача 12</H3></summary>
    Переделать механизм распознавания «свой-чужой» между фронтом и беком с <span style="border: 1px solid black; padding: 2px 4px; border-radius: 3px;">JSESSIONID</span> на <span style="border: 1px solid black; padding: 2px 4px; border-radius: 3px;">JWT</span>. Из сложностей – тебе придётся переделать отправку форм с фронта, чтоб добавлять хедер аутентификации. <span style="border: 1px solid #b47adf; background-color: #b379df; color: white; padding: 2px 4px; border-radius: 3px;">Extra-hard task</span>
</details>

- [ ]  В процессе

