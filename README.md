Super Simple Net Piercing
======
[![Read in English](http://www.printableworldflags.com/icon-flags/24/United%20Kingdom.png)](https://github.com/SquareGearsLogic/ssnp) [![Read in Russian](http://www.printableworldflags.com/icon-flags/24/Russian%20Federation.png)](https://github.com/SquareGearsLogic/ssnp/blob/master/README.ru.md)  
![](https://travis-ci.org/SquareGearsLogic/ssnp.svg?branch=master)

This super tool simply maps a port on publick subnetwork [FACE]
to private subnetwork [BACK], of the server where it runs.
When client connects to [FACE], SSNP opens connection to
whatever sits on [BACK] and transmits data between them.
...So if you wanna connect to device that hides behind Prod Server
from your desk - this tool will pierce a hole for you ^_^

Building
-----------
```bash
javac SSNP.java
```

Running
-----------
```bash
java SSNP FACE_HOST FACE_PORT BACK_HOST BACK_PORT
```

Documentation
-----------
Is missing.

License
-----------
Apache License Version 2.0, January 2004
http://www.apache.org/licenses/