#!/usr/bin/env bash

rm *.java || true
javacc HitAssAstParser.jj && dos2unix *.java
# the following step will probably be taken care of by the IDE
# javac *.java

