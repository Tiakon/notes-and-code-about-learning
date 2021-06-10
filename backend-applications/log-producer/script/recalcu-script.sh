#!/usr/bin/env bash

input_path=/user/admin/db/cdnportal/monitor_alone/customer_id=31/day=20190528/iterm_types=11110

#过滤出异常数据分布的文件（假设最大延迟为1h）
hdfs dfs -ls ${input_path} | awk -F ' ' '$7~/17:5[0-9]|18:[0-4][0-9]|18:50/ {print $7,$8} '> 01_fileName
sort 01_fileName > 02_fileNameSorted
sed "/iqiyicn/d" 02_fileNameSorted | awk -F ' ' '{print $2}' >  03_fileNameSortedAndDeleted

# 异常数据本地备份
if [[ $(mkdir date-bak) -eq 0 ]]
then
    for i in $(cat 03_fileNameSortedAndDeleted);
    do hdfs dfs -get ${i} ./date-bak;
        if [[ $? -eq 0 ]]
        then
            echo download ok!
        else
            echo download not ok...
        fi;
    done
fi

#移走数据库中的异常数据分布的文件
tmp_exception_date_path=/tmp/iqiyi_exception_ct_17_50-18_50/
$(hdfs dfs -mkdir ${tmp_exception_date_path})
if [[ $? -eq 0 ]]
then
    for i in $(cat 03_fileNameSortedAndDeleted);
    do $(hdfs dfs -mv ${i} ${tmp_exception_date_path})
        if [[ $? -eq 0 ]]
        then
            # 注意 感叹号 与 引号之间的空格
            echo -e "move ok! "
        else
            echo -e "move not ok..."
        fi;
    done
fi
#for i in $(cat 03_fileNameSortedAndDeleted); do $(hdfs dfs -mv ${i} /tmp/iqiyi_exception_ct_17_50-18_50/ );if [[ $? -eq 0 ]];then echo -e "move ok!";else echo move not ok...;fi;done

#校验
#ll date-bak | grep 20190528_31_monitor5min_iqiyict  |wc -l
#hdfs dfs -ls /tmp/iqiyi_exception_ct_17_50-18_50 | grep 20190528_31_monitor5min_iqiyict  | wc -l

#过滤出正常数据

#重新计算异常点数据

#重新上传异常点数据

#END











