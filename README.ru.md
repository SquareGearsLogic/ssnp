Супер Простой Сетевой Пирсинг
======
[![Read in English](http://www.printableworldflags.com/icon-flags/24/United%20Kingdom.png)](https://github.com/SquareGearsLogic/ssnp) [![Read in Russian](http://www.printableworldflags.com/icon-flags/24/Russian%20Federation.png)](https://github.com/SquareGearsLogic/ssnp/blob/master/README.ru.md)  
![](https://travis-ci.org/SquareGearsLogic/ssnp.svg?branch=master)

Эта супер приблуда просто мапит порт с публичной подсети [FACE]
на порт приватной подсети [BACK] сервера, где она запущена.
Когда клиент цепляется на [FACE], SSNP открывает соединение
с тем, что сидит на [BACK] и передаёт данные между ними.
... Корече, если надо подцепиться к девайсу, который прячется за Прод-серваком
с вашего компа - эта приблуда сделает вам дырочку туда ^_^

Сборка
-----------
```bash
javac SSNP.java
```

Запуск
-----------
```bash
java SSNP FACE_HOST FACE_PORT BACK_HOST BACK_PORT
```

Документация
-----------
Отсутствует.

Лицензия
-----------
Apache License Version 2.0, January 2004
http://www.apache.org/licenses/