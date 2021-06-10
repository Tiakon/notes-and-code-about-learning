#!/usr/bin/env bash
#方式一
java -cp ./lib/log-producer-1.0.4-SNAPSHOT.jar:./lib/slf4j-log4j12-1.7.2.jar:./lib/slf4j-api-1.7.2.jar:./lib/log4j-1.2.12.jar\
cn.tiakon.core.LogProducerStarter\
10 >> logs/access.out 2>&1 &
#方式二
java -Djava.ext.dirs=/home/kai.tian/log-producer/lib  cn.tiakon.core.LogProducerStarter 5 >> /dev/null 2>&1 &

#方式三
java -Djava.ext.dirs=${JAVA_HOME}/jre/lib/ext:lib \
-Dlog4j.configuration="file:/root/kai.tian/log-producer/conf/log4j.properties" \
cn.tiakon.logproducer.core.LogProducerStarter producer-conf.properties  \
>> logs/log-producer.out 2>&1 &


