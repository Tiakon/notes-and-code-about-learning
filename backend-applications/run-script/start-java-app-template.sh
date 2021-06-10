#!/usr/bin/env bash

#!/bin/bash

#java -Djava.ext.dirs=$JAVA_HOME/jre/lib/ext:/root/kai.tian/spark2-demo/lib/ com.ngaa.bigdata.demo.HelloWorld  >> logs/java-app-$(date +"%Y%m%d").log 2>&1 &

#java -Djava.ext.dirs=$JAVA_HOME/jre/lib/ext:/root/kai.tian/spark2-demo/lib/ com.ngaa.bigdata.demo.HelloWorld

#java -Djava.ext.dirs=$JAVA_HOME/jre/lib/ext:/home/kai.tian/log-producer/lib

java -Djava.ext.dirs=$JAVA_HOME/jre/lib/ext:/root/kai.tian/spark2-demo/lib:/opt/cloudera/parcels/SPARK2/lib/spark2/jars/  com.ngaa.bigdata.demo.HiveJDBCDemo

#java -cp ./lib/ngaa-cdn-1.0.0.19-alpha.jar:./lib/hive-jdbc-1.1.0-cdh5.15.2.jar:./lib/hive-jdbc-1.1.0-cdh5.15.2.jar:./lib/hive-common-1.1.0-cdh5.15.2.jar:./lib/hive-metastore-1.1.0-cdh5.15.2.jar:./lib/hive-service-1.1.0-cdh5.15.2.jar:./lib/libthrift-0.9.3.jar:./lib/httpclient-4.5.2.jar:./lib/httpcore-4.4.6.jar:./lib/slf4j-log4j12-1.7.2.jar:./lib/slf4j-api-1.7.2.jar:./lib/log4j-1.2.12.jar  com.ngaa.bigdata.demo.HiveJDBCDemo

#java -cp ./lib/ngaa-cdn-1.0.0.18-alpha.jar  com.ngaa.bigdata.demo.HiveJDBCDemo