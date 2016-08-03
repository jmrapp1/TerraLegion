#!/usr/bin/env bash

function progress(){
    local pid=$1
    while [ $(ps -eo pid | grep ${pid}) ];
    do
        echo -n "."
        sleep 1
    done
    echo "FINISHED"
}

function outAndRm(){
    local file=$1
    echo "--------LOG START--------"
    grep -v '^$\|^\.\.\.' ${file}
    echo "---------LOG END---------"
    rm -f file
}

function doGradleCmd(){
    echo -n "Running $1 "
    $(eval $1 > gradlelog 2>&1; echo $? > cmdResult) & progress $!
    outAndRm gradlelog
    [[ $(<cmdResult) = 0 ]] || exit 1;
}
