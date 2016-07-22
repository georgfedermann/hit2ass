#!/usr/bin/env bash

echo "Positional parameters: "
echo '$0 = ' $0
echo '$1 = ' $1
echo '$2 = ' $2

if [ "$1" != "" ]; then
  echo "Processing textcomponent " $1 "."
  cat "$1".clou | hitAssTools.sh -a | dot -Tsvg -Gcharset=ISO-8859-1 | xmllint --format - > "$1".svg && cat "$1".clou | hitAssTools.sh -w | xmllint --format - > "$1".acr && cp --recursive . /mnt/hgfs/shared/hitass/sampleDocuments/clou/
  echo "Done. Please review hitass.log for detailed progress report."
else
  echo "Please enter the name of the textcomponent you want to process, without .clou in the end. E.g.: SectionBreak"
fi

