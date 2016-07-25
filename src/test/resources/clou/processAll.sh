#!/usr/bin/env bash
echo "Processing all .clou files in `pwd` "
for file in `ls *.clou` ; do file="${file%.*}"; echo "processing $file"; ./process.sh $file ; done
cp --recursive * /mnt/hgfs/shared/hitass/sampleDocuments/clou/
