#!/usr/bin/env bash

TOOLS_TARGET_PATH=~/bin/
TOOLS_JAR_NAME=HitAssTools-jar-with-dependencies.jar
JAVACC_PATH=./src/main/java/org/poormanscastle/products/hit2ass/parser/javacc/
TARGET_FOLDER=target/

HIT_CLOU_LIBRARY_PATH=./src/test/resources/clou
HIT_CLOU_DEPLOYED_MODULE_LIBRARY=/Users/georg/vms/UbuntuWork/shared/hitass/deployedModuleLibrary/TestLibrary.acr
HIT_CLOU_ENCODING=UTF-8

print_help() {
    echo "build-tool v0.1 brought to you by poor man's castle."
    echo "Usage: ./build.sh arg"
    echo "Default action is to execute maven builds and copy the artefacts to specified locations on the file system."
    echo
    echo "t  build grammar tools package and deploy it to $TOOLS_TARGET_PATH"
    echo "d  build grammar tools package and deploy it to NEXUS snapshot repository"
    echo "h  print this help"
}

case $1 in
  t)
    echo "Building hit/ass tools package"
    pushd $JAVACC_PATH
    ./createHitAssAstParser.sh
    popd
    mvn -Pprod -Dhit2ass.clou.pathToDeployedModuleLibrary=$HIT_CLOU_DEPLOYED_MODULE_LIBRARY -Dhit2ass.clou.encoding=$HIT_CLOU_ENCODING -DargLine="-Dhit2ass.clou.path=$HIT_CLOU_LIBRARY_PATH" clean package assembly:single install && cp ${TARGET_FOLDER}${TOOLS_JAR_NAME} ${TOOLS_TARGET_PATH}
    ;;
  d)
    echo "Building hit/ass tools package and deploying to NEXUS snapshot repository"
    pushd $JAVACC_PATH
    ./createHitAssAstParser.sh
    popd
    mvn -Pprod -DargLine="-Dhit2ass.clou.path>=$HIT_CLOU_LIBRARY_PATH" clean package assembly:single deploy && cp ${TARGET_FOLDER}${TOOLS_JAR_NAME} ${TOOLS_TARGET_PATH}
    ;;
  h)
    print_help
    ;;
  *)
    print_help
    ;;
esac


