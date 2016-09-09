#!/usr/bin/env bash

JAR_HOME=/home/georg/bin/
JAR_NAME=HitAssTools-jar-with-dependencies.jar

HIT_CLOU_LIBRARY_PATH=`pwd`
# Nota bene: the rather unusual naming of the encoding is not a typo, but rather how javacc expects it!
HIT_CLOU_ENCODING=ISO8859_1

ARGS=
while getopts "ac:hvwx:" opt; do
  case $opt in
    a)
      ARGS="a $ARGS"
      ;;
    c)
      # echo "-c was triggered!" >&2
      ARGS="c $OPTARG $ARGS"
      ;;
    h)
      # echo "-h was triggered!" >&2
      ARGS="h $ARGS"
      ;;
    v)
      # echo "-v was triggered!" >&2
      ARGS="v $ARGS"
      ;;
    w)
      ARGS="w $ARGS"
      ;;
    x)
      ARGS="x $OPTARG $ARGS"
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
java -Dhit2ass.clou.encoding=$HIT_CLOU_ENCODING -Dhit2ass.clou.path=$HIT_CLOU_LIBRARY_PATH -Dfile.encoding=ISO-8859-1 -cp "${JAR_HOME}${JAR_NAME}" org.poormanscastle.products.hit2ass.cli.HitAssTools $ARGS

