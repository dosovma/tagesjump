## Тестовое задание от TagesJump
* Исполнитель - Михаил Досов
* Дата - 06.03.2021

### Задание описано в [spec.docx](https://github.com/dosovma/tagesjump/blob/master/SPEC.docx) 

### Описание REST
[Description in Postman](https://documenter.getpostman.com/view/13586382/Tz5jf1Sw#23cda774-4830-4157-8754-266464358c74)

#### Get user by id
`curl --location --request GET 'http://localhost:8080/user/1`

#### Update user by id with full data in json (id and token are ignored)
`curl --location --request POST 'http://localhost:8080/user/1' \
--data-raw '{
"id": 1,
"token": "someToken",
"name": "updatedMike",
"age": 29
}'`

#### Update user by id with only age in json  
`curl --location --request POST 'http://localhost:8080/user/2' \
--data-raw '{
"age": 25
}'`

### Комментарии по заданию
* В репозитории используются AtomicInteger и ConcurrentHashMap, так как одновременно несколько клиентов (потоков) 
  могут обновлять/создавать данные в памяти.
* AtomicInteger на текущий момент никак не используется. Добавил его, чтобы задать генерацию id для новых данных, 
  если понадобится реализация с созданием, а не только обновлением.
* Начинается AtomicInteger с 1000, чтобы оставить свободные id под моки.
* Уровень сервиса не стал делать, потому что логики на данный момент крайне мало, дергаю репозиторий прямо из контролера.
* Пункт задания про запрет редактирования ID и Token реализовал просто игнорированием этих данных в json. Можно сделать по-другому:
    * Сделать Transfer object, в котором будет только 2 поля (Name и Age), принимать в контроллере только Json нужного формата.
    * Можно проверять попытку изменения запрещенных данных и кидать exception.
* Equals и hashCode переопределил стандартно с участием всех полей класса, так как в реализации нет Hibernate.
* Так как Equals и hashCode стандартные, то в тестах можно использовать обычное сравнение через isEqualTo(), 
  а не рекурсивное сравнение по всем полям usingRecursiveComparison().