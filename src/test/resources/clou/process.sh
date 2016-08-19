#!/usr/bin/env bash

echo "Positional parameters: "
echo '$0 = ' $0
echo '$1 = ' $1
echo '$2 = ' $2
echo "To process all .clou files in the current directory, use this:"
echo 'for file in `ls *.clou` ; do file="${file%.*}"; echo "processing $file"; ./process.sh $file ; done'

if [ "$1" != "" ]; then
  echo "Processing textcomponent " $1 "."
  cat "$1".clou | hitAssTools.sh -a | dot -Tsvg -Gcharset=ISO-8859-1 | xmllint --format - > "$1".svg && cat "$1".clou | hitAssTools.sh -w | xmllint --format - > "$1".acr && cp "$1".* /Users/georg/vms/UbuntuWork/shared/hitass/sampleDocuments/clou/ 
  echo "Done. Please review hitass.log for detailed progress report."
else
  echo "Please enter the name of the textcomponent you want to process, without .clou in the end. E.g.: SectionBreak"
fi

