# Postmark Java API

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/sejpalsaurabh/postmark)
[![Join the chat at https://gitter.im/sejpalsaurabh/postmark](https://img.shields.io/gitter/room/gitterHQ/gitter.svg)](https://gitter.im/sejpalsaurabh/postmark)
[![GitHub version](https://badge.fury.io/gh/sejpalsaurabh%2Fpostmark.svg)](https://badge.fury.io/gh/sejpalsaurabh%2Fpostmark)

> An implementation of [Postmark REST API](http://developer.postmarkapp.com/) in Java.

You can browse [JavaDoc for Project](http://sejpalsaurabh.github.io/postmark-api-doc/)

You need to create a file named ```postmark.properties```
and need to provide server and account tokens into that.

Sample ```postmark.properties``` file.
```
server-key      =   <Postmark Server Token>
account-key     =   <Postmark Account Token>
from            =   <From Email>
reply-to        =   <Reply To Email>
isHTML          =   true or false
isTracked       =   true or false
```

Also we have used log4j for logging purpose into ```postmark``` Project,
Sample ```log4j.properties``` file.
```
log4j.rootLogger=DEBUG, Appender1,Appender2

log4j.appender.Appender1=org.apache.log4j.ConsoleAppender
log4j.appender.Appender1.layout=org.apache.log4j.PatternLayout
log4j.appender.Appender1.layout.ConversionPattern=%-7p %d [%t] %c %x - %m%n

log4j.appender.Appender2=org.apache.log4j.FileAppender
log4j.appender.Appender2.File=<Log File Path>
log4j.appender.Appender2.layout=org.apache.log4j.PatternLayout
log4j.appender.Appender2.layout.ConversionPattern=%-7p %d [%t] %c %x - %m%n
```

Above is the minimal basic configuration to use postmark lib.

###Completed Things
###Clients
- [ ] Bounce
- [ ] Email
- [ ] Message
- [X] Server
- [X] Sender Signature
- [ ] Stats
- [ ] Template
- [X] Inbound Rule
- [X] Tag


You can find JavaDoc for this Project on http://sejpalsaurabh.github.io/postmark-api-doc/