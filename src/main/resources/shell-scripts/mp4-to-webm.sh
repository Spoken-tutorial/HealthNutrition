#!/bin/bash

# Convert all .mp4 files recursively to .webm using VP9 codec

FFMPEG_GLOBAL_OPTIONS="
-nostdin
-v 16
-y
"

# Custom ffmpeg options
FFMPEG_OPTIONS="
-c:a libvorbis
-b:a 96k
-c:v libvpx
-b:v 1M
-crf 30
-r 8
"


oninterrupt() {
    rm -f "$eachWebmFile"
    echo "[$successcount/$totalcount] converted"
    rm -f "$mp4file"
    exit 1
}

# Ensure one argument (directory) is given
DIR="$1"
if test "$#" -ne 1 -o ! -d "$DIR"; then
    echo "Usage: $0 directory"
    echo 'Provide path of your media directory as argument to this script.'
    exit 0
fi

mp4file="$DIR/source-mp4file-paths.txt"

# Create list of mp4 files
find "$DIR" -iname \*.mp4 | sort > "$mp4file"

totalcount=$( wc -l < "$mp4file" )
count=0
successcount=0
echo "[$totalcount] files to be converted"

# Handle Ctrl-C
trap oninterrupt INT QUIT TERM

# Loop through each mp4 file
while read eachMp4File; do
    let count++

    # Change extension to .webm
    eachWebmFile="$( echo "$eachMp4File" | sed 's/\.mp4$/.webm/i' )"

    # Skip if already converted
    if test -f "$eachWebmFile" && file -b "$eachWebmFile" | grep -q WebM; then
        continue
    fi

    echo "[$count/$totalcount] converting $eachMp4File"
    ffmpeg $FFMPEG_GLOBAL_OPTIONS -i "$eachMp4File" $FFMPEG_OPTIONS "$eachWebmFile" &&
        let ++successcount || rm -f "$eachWebmFile"

    eachWebmFile=
done < "$mp4file"

echo "[$successcount/$totalcount] converted"
rm -f "$mp4file"
exit 0
