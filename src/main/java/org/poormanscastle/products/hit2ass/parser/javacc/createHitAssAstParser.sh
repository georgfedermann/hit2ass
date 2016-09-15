#!/usr/bin/env bash

rm *.java || true
javacc HitAssAstParser.jj && dos2unix *.java
for file in `ls *.java`; do sed -i -e 's/\/\*\*/\/\*/g' $file ; done
# the following step will probably be taken care of by the IDE
# javac *.java

