#!/usr/bin/env bash

JAR_HOME=/home/georg/bin/
JAR_NAME=HitAssTools-jar-with-dependencies.jar

HIT_CLOU_LIBRARY_PATH=`pwd`

ARGS=
while getopts "ac:d:hmt:vwx" opt; do
  case $opt in
    a)
      ARGS="a $ARGS"
      ;;
    c)
      # echo "-c was triggered!" >&2
      ARGS="c $OPTARG $ARGS"
      ;;
    d)
      ARGS="d $OPTARG $ARGS"
      ;;
    h)
      # echo "-h was triggered!" >&2
      ARGS="h $ARGS"
      ;;
    m)
      ARGS="m $ARGS"
      ;;
    t)
      ARGS="t $OPTARG $ARGS"
      ;;
    v)
      # echo "-v was triggered!" >&2
      ARGS="v $ARGS"
      ;;
    w)
      ARGS="w $ARGS"
      ;;
    x)
      ARGS="x $ARGS"
      ;;
    :)
      # echo "Option -$OPTARG requires an argument." >&2
      ARGS="c invalid"
      ;;
    \?)
      # echo "Invalid Option: -$OPTARG" >&2
      exit 1
      ;;
  esac
done

#echo $ARGS
java -Dhit2ass.clou.path=$HIT_CLOU_LIBRARY_PATH -Dfile.encoding=UTF-8 -cp "${JAR_HOME}${JAR_NAME}" org.poormanscastle.products.hit2ass.cli.HitAssTools $ARGS

