#!/usr/bin/env bash

#filePath=/user/admin/data/cdn/aiqiyi/20190527

filePath=$0
hdfs dfs -ls ${filePath} | awk -F ' ' 'BEGIN{sum=0}{sum+=$5}END{print sum/1024/1024/1024 "G"}'