﻿# Интернет-магазин
В обзаце будут опубликовываться фичи, которые я сделал, и которые можно проверить в main ветке.
Также здесь будет доступна инструкция по запуску проекта.
## Реализованные фичи на 05.12.2024 (в папке pictures содержаться скриншоты рабочих окон программы, все методы которые я буду далее описывать полностью рабочие)
Я буду подробно описывать каждую фичу, а именно какие сервлеты, эндпоинты и .jsp страницы я использовал. Фичи указаны в пореядке по мере добавления в проект.
1. Добавление товаров с картинками. пользваотель может нажимать на кнопки + или - для регулирования количества картинок в товаре
    + AddProductServlet ("/product/add" [GET],"/product/add" [POST]), addProduct.jsp
2. Вывод всех товаров на экран (использована grid вёрстка для вывода карточек товаров)
    + MainPageServlet ("/" [GET]),index.jsp, modalFilter.jsp
3. Посик товара по названию
    + SearchProductServlet ("/product/search" [GET]),index.jsp, modalFilter.jsp
4. Фильтрация товаров по категиории, минимальной и максимальной цене, сортировка (фильтры и сортировка представлены модальным окном)
    + FilterProductServlet ("/product/filter" [GET]),index.jsp, modalFilter.jsp
5. Просмотр отдельной картинки по id (картинки берутся из БД)
    + GetImageServlet ("/image",[GET])
6. Просмотр отдельного товара, картинки выводятся в виде карусели
    + GetProductServlet ("/product/get", [GET]),product.jsp
7. Регистрация в системе, предусмотрена проверка на уникальный логин, и проверка на сопадение паролей (то есть пароль и пароль и подтверждения в форме должны совпадать)
    + Registrationservlet ("/registration" [GET],"/registration", [POST]), registration.jsp
8. Авторизация в системе
    + Loginservlet("/login" [GET],"/login" [POST]), login.jsp
9. Добавление товара в корзину и удалене из корзины. Если товар уже содержиться в корзине, то увеличивается количество данного товара в коризине. Когда новый товар добавляется в корзину, то цифра на значке корзина в header увеличивается на 1, если у пользователя нет товаров в корзине, то данный значок не появляется
    + AddProductTocartServlet ("/user/cart/add" [POST])
    + DeleteCartServlet ("/user/cart/delete" [POST])
10. Просмотр корзины товаров пользователя. На кажой карточке товара есть кнопки + и - для увелчения количества данного твоара и уменьшения соответственно. Если количество введённого товара больше чем на складе, то пользователь увидит об этом сообщение. Еслт количество товара было 1 и пользователь нажмёт на -, то товар просто удалиться из корзины
    + CartServlet ("/user/cart" [GET]),cart.jsp, modalOrder.jsp
    + IncrementCartServlet ("/user/cart/increment" [POST])
    + DecrementCartServlet ("/user/cart/decrement" [POST])
11. Оформление заказа. Выбор адреса из предыдущих адресов заказов для автоматического заполнения (Фича, где оплата не прошла тоже будет реализована, но немного позже)
    + CartServlet ("/user/cart" [POST]),cart.jsp, modalOrder.jsp
12. Переход в личный кабинет, где выведены логин, email, а также полная история заказов
    + ViewAccountServlet ("/user/account" [POST]), account.jsp
13. Выход из системы
    + LogoutServlet ("/logout" [GET]), account.jsp <br>
Далее планирую сделать панель админа, менеджера и разделение на роли при помощи @WebFilter согласно плану. На 04.12.2024 немного опережаю поставленные сроки (примернго на 2 дня)    
## Используемые технологии
На данный момент решил, что буду писать на Servlets, используя JSP страницы. Для вывода информации буду использовать JSTL. Для стилизации и адаптивной вёрскти CCS и Bootstrap.
Для подключения к БД будет использоваться Hibernate. БД Postgres и PgAdmin (а в будущем и само web приложение) будут находиться на Docker для упрощения запуска проекта.
Картинки будут храниться в БД, изначально хотел на сервере S3 или Google Drive, но S3 на данный момент запрещён в РБ, и к тому же надо много времени, чтобы разобраться
с документацией и реализовать загрузку файлов на сервер и получение их URL.
На Reacte возникла проблема с авторизацией, так как пока непонятно, как будет идти перенаправление на страницу авторизации, если пользователь захочет сделать действие, требующее авторизацию. А на Servlets это можно реализовать через фильтры.
## UML диаграммы представления программного средства
В папке uml можно найти 2 диаграммы: **Use Case** и **IDEF1X**.
* Use Case применяется для отображения функционала пользователя и админа. 
* IDEF1X применяется для моделировании БД, и отпределения отношений между таблицами.
## План реализации проекта и сроки 
* Создание товара и добавления в него картинок, фильтрация товаров по категории и поиск - 26.11.2024 - 31.11.2024
* Сделать аторизицаю и регистрацию (в рекомендуемом плане было написано, что это выполняется в последнюю очередь, но с сущностью user у меня
связано много других сущностей, поэтому я решил, что буду делать данный момент со второго этапа) - 31.11.2024 - 02.11.2024
* Добавление товаров в корзину и оформление заказа, просмотр истории заказов - 02.11 - 06.11.2024
* Добавление фичи выбора адреса из разных адресов доставки пользователя - 06.11.2024 - 08.11.2024
* Разделение на роли и создание таблицы пользователей для бана и отмены бана со стороны админа,
создание таблицы для работы с заказами и продуктами со стороны менеджера - 08.11.2024 -12.11.2024
* Улучшение frontend части, проверка на баги и работоспособности программного средства - 12.11.2024 - 14.11.2024
* Деплой приложения через Docker - 14.11.2024 -15.11.2024
* Полная сдача проекта 16.11.2024
## Примечание
Данный план, сроки, функциональность в Use case диаграмме, используемые технологии могут меняться. Данный README.md лишь первая версия версия описаия программы
