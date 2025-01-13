# Добавление Hibernate в Проект. План выполнения
* Доработка проекта (удаление ненужных анатаций @Transactionsl, создание большего количества собственных исключений и т. д.), приведение кода в нормальный вид - 13.01.2025
* Далее буду дейтсвовать по приведённому плану задания (создание класса AbstractHibernateDao<K, T>,подключение пула соединений, создание DTO и мапперов) - 14.01.2025 - 16.01.2025
* Реализация фич, приведенных в плане проекта
    +   Вывод всех записей из таблицы.
    +   Поиск записи по ID.
    +   Фильтрация данных (например, поиск пользователей старше определенного возраста).
    +   Пагинация для списков данных.
    +   Выполнение запросов с использованием HQL и Criteria API.
    +   Реализуйте транзакции для методов, где это необходимо.
    +   Реализуйте систему ролей (user, admin, guest) и возможность авторизации и аутентификации пользователя.
  В проекте уже есть таблица всех продуктов интернет-магазина, остаётся лишь добавить к данной фичи, которые описаны выше - 16.01.2025 - 18.01.2025
* Написание JUnit тестов - 17.01.2025
* Проверки программного средства на работоспособность, поиск возможных ошибок и их исправление - 17.01.2025 - 20.01.2025
* Полная сдача проекта - 21.01.2025
Прмечание. Сроки реализации пунктов плана и фичи в проекте могут меняться.
## Интернет-магазин
Для запуска проекта нужно скопировать его локально на компьютер
```bash
git clone https://github.com/Vladislav3421730/online-store
```
Затем нужно перейти в папку с проектом web-app (можно открыть в любом редакторе кода, можно через терминал)
```bash
cd online-store/web-app
```
Затем нужно создать папку для data/postgres для корректного считывания данных с БД
```bash
mkdir data\postgres\pg_tblspc
```
Затем запустить команду докера. Нужно, чтобы Docker был установлен локально на вашем компьютере
```bash
docker compose up
```
Данными ничего заполнять не нужно, информация в бд будет доступна благодаря volume в docker-compose файле.
Затем перейти на host
```bash
localhost:8080
```
Теперь проект можно полноценно тестировать и проверять что там работает, и какие фичи я реализовал. Логины и пароли для входа будут представлены ниже.
## Реквизиты для входа
1. Если хотите войти с ролями USER, MANAGER, ADMIN
      + Логин:***`vlad@gmail.com`***, Пароль: ***q1w2e3***
2. Если хотите войти с ролью ADMIN
      + Логин:***`admin@gmail.com`***, Пароль: ***q1w2e3***
3. Если хотите войти с ролью MANAGER
      + Логин:***`manager@gmail.com`***, Пароль: ***q1w2e3***
4. Если хотите войти с ролю USER
      + Логин:***`user@gmail.com`***, Пароль: ***q1w2e3***
      + Логин:***`user2@gmail.com`***, Пароль: ***q1w2e3***
      + Логин:***`user3@gmail.com`***, Пароль: ***q1w2e3***
## Реализованные фичи в проекте (в папке pictures содержатся скриншоты рабочих окон программы, все методы которые я буду далее описывать полностью рабочие)
Я буду подробно описывать каждую фичу, а именно какие сервлеты, эндпоинты и .jsp страницы я использовал. Фичи указаны в порядке по мере добавления в проект.
1. Добавление товаров с картинками. пользваотель может нажимать на кнопки + или - для регулирования количества картинок в товаре.
    + AddProductServlet ("manager/product/add" [GET],"/product/add" [POST]), addProduct.jsp
2. Вывод всех товаров на экран (использована grid вёрстка для вывода карточек товаров). в header.jsp есть жёлтая кнопка ***OnlineShop***, при нажатии на неё можно перейти на главную страницу
    + MainPageServlet ("/" [GET]),index.jsp, modalFilter.jsp
3. Поиск товара по названию
    + SearchProductServlet ("/product/search" [GET]),index.jsp, modalFilter.jsp
4. Фильтрация товаров по категории, минимальной и максимальной цене, сортировка (фильтры и сортировка представлены модальным окном)
    + FilterProductServlet ("/product/filter" [GET]),index.jsp, modalFilter.jsp
5. Просмотр отдельной картинки по id (картинки берутся из БД)
    + GetImageServlet ("/image",[GET])
6. Просмотр отдельного товара, картинки выводятся в виде карусели
    + GetProductServlet ("/product/get", [GET]),product.jsp
7. Регистрация в системе, предусмотрена проверка на уникальный логин, телефон и проверка на сопадение паролей (то есть пароль и пароль и подтверждения в форме должны совпадать)
    + Registrationservlet ("/registration" [GET],"/registration", [POST]), registration.jsp
8. Авторизация в системе
    + Loginservlet("/login" [GET],"/login" [POST]), login.jsp
9. Добавление товара в корзину и удалене из корзины. Если товар уже содержится в корзине, то увеличивается количество данного товара в коризине. Когда новый товар добавляется в корзину, то цифра на значке корзина в header увеличивается на 1, если у пользователя нет товаров в корзине, то данный значок не появляется
    + AddProductTocartServlet ("/user/cart/add" [POST])
    + DeleteCartServlet ("/user/cart/delete" [POST])
10. Просмотр корзины товаров пользователя. На кажой карточке товара есть кнопки + и - для увеличения количества данного твоара и уменьшения соответственно. Если количество введённого товара больше чем на складе, то пользователь увидит об этом сообщение. Если количество товара было 1 и пользователь нажмёт на -, то товар просто удалиться из корзины
    + CartServlet ("/user/cart" [GET]),cart.jsp, modalOrder.jsp
    + IncrementCartServlet ("/user/cart/increment" [POST])
    + DecrementCartServlet ("/user/cart/decrement" [POST])
11. Оформление заказа. Выбор адреса из предыдущих адресов заказов для автоматического заполнения. Реализована фича, где с вероятностью 17.24% оплата не пройдёт
    + CartServlet ("/user/cart" [POST]),cart.jsp, modalOrder.jsp
12. Переход в личный кабинет, где выведены логин, email, а также полная история заказов
    + ViewAccountServlet ("/user/account" [POST]), account.jsp
13. Выход из системы
    + LogoutServlet ("/logout" [GET]), account.jsp
14. Переход на страницу админа
    + AdminPanelServlet ("/admin/panel" [GET]),adminPanel.jsp
15. Бан и отмена бана со стороны админа, поиск пользователей
    + BunUserServlet ("/admin/bun" [POST]), adminPanel.jsp
    + UserSearchservlet ("/admin/search", [GET]), adminPanel.jsp
16. Администратор может назначить пользователю роль Менеджер и удалить её
    + AdminMadeManagerServlet ("/admin/role/manager"), adminPanel.jsp
17. Переход на страницу менеджера для заказов
    + ManangerViewsOrders ("/manager/orders", [GET]), managerOrders.jsp
18. Переход на страницу для просмотра отдельного заказа
    + ManagerViewOrder ("/manager/order", [GET]), order.jsp
19. Преход на страницу менеджера для работы с продуктами
    + ManagerProductServlet ("/manager/products", [GET]), managerProducts.jsp
20. Удаление продукта по его id
    + DeleteProductServlet ("/manager/product/delete", [POST]),  managerProducts.jsp
21. Редактирование продукта. Можно добавить новые картинки, изменить старые, удалить картинки, картикни будут выводиться в том же порядке, что и были, не смотря на изменения.
Это обеспечивается за счёт анатации  @OrderBy("id ASC")
    + ProductEditservlet ("/manager/product/edit", [POST]), managerProducts.jsp
22. Изменение статуса проекта. Всего доступно 4 статуса: принят, в обработке, в пути, оствлен
    + StatusChangeServlet ("/manager/status/change" [POST]), order.jsp, modalStatus.jsp
23. Поиск заказов по email пользователя
    + ManagerOrderSearchServlet ("/manager/order/search", [GET]), managerOrders.jsp

Стоит сказать, что я также добавил 3 фильтра @WebFilter, для менеджера, админа и для невторизованного пользователя. Например он нажал в корзину, и его перебрасывает на окно авторизации.
В ветке dev случайно ввёл сообщение на русском и не смог отменить (пробовал git commit --ammend), к сожалению возникли проблемы с ветками. Данный коммит так и остался с сообщением с русскими буквами в main ветке.
В моём проекте нет DTO классов. Я пытался делать через них, переписал весь код, но появились сотни ошибок, непонятно как сохранять состояние объектов hibernate, как передавать DTO в сессию, когда это нужно, и как его потом обновлять. Также возникали проблемы с циклическими зависимостями в Mapper. В общем из-за этих факторов и из-за сжатых сроков я решил не добавлять DTO классы, чтобы не переписывать половину кода.
Данные храняться в volumes локально на компьютере, понимаю что такой подход не совсем верный.Я сделал так, 
чтобы проект можно было сразу же запустить и проверить его работу уже с введёнными данными. В будущем можно купить доступ к удалённой БД (например у Yandex Cloud) и уже туда отправлять данные. 
## Используемые технологии
Решил, что буду писать на Servlets, используя JSP страницы. Для вывода информации буду использовать JSTL. Для стилизации и адаптивной вёрскти CCS и Bootstrap.
Для подключения к БД будет использоваться Hibernate. БД Postgres и PgAdmin (а в будущем и само web приложение) будут находиться на Docker для упрощения запуска проекта. Для формирования SQL запросов 
для фильтрации буду использовать CriteriaAPI. Картинки будут храниться в БД, изначально хотел на сервере S3 или Google Drive, но S3 на данный момент запрещён в РБ, и к тому же надо много времени, чтобы разобраться
с документацией и реализовать загрузку файлов на сервер и получение их URL.
На Reacte возникла проблема с авторизацией, так как пока непонятно, как будет идти перенаправление на страницу авторизации, если пользователь захочет сделать действие, требующее авторизацию. А на Servlets это можно реализовать через фильтры.
## UML диаграммы представления программного средства
В папке uml можно найти 2 диаграммы: **Use Case** и **IDEF1X**.
* Use Case применяется для отображения функционала пользователя,админа и менеджера. 
* IDEF1X применяется для моделировании БД, и отпределения отношений между таблицами.
## План реализации проекта и сроки 
* Создание товара и добавления в него картинок, фильтрация товаров по категории и поиск - 26.11.2024 - 31.11.2024
* Сделать аторизицаю и регистрацию (в рекомендуемом плане было написано, что это выполняется в последнюю очередь, но с сущностью user у меня
связано много других сущностей, поэтому я решил, что буду делать данный момент со второго этапа) - 31.11.2024 - 02.11.2024
* Добавление товаров в корзину и оформление заказа, просмотр истории заказов - 02.11 - 06.11.2024
* Добавление фичи выбора адреса из разных адресов доставки пользователя - 06.11.2024 - 08.11.2024
* Разделение на роли и создание таблицы пользователей для бана и отмены бана со стороны админа,
создание таблицы для работы с заказами и продуктами со стороны менеджера - 08.11.2024 -11.11.2024
* Улучшение frontend части, проверка на баги и работоспособности программного средства - 11.11.2024 - 12.11.2024
* Деплой приложения через Docker и заполнене бд данными - 12.11.2024
* Полная сдача проекта 13.11.2024
