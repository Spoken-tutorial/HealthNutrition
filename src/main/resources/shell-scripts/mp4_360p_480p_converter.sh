#!/bin/bash

# Ensure UTF-8 locale for handling filenames in Indian languages
export LANG="en_US.UTF-8"
export LC_ALL="en_US.UTF-8"

oninterrupt() {
    echo ""
    echo "Interrupted. Cleaning up..."
    [[ -n "$current360" && -f "$current360" && ! -s "$current360" ]] && rm -f "$current360"
    [[ -n "$current480" && -f "$current480" && ! -s "$current480" ]] && rm -f "$current480"
    echo "[$successcount/$totalcount] files fully or partially converted before interruption."
    rm -f "$mp4file"
    exit 1
}

# Ensure one argument (directory) is given
DIR="$1"
if [[ "$#" -ne 1 || ! -d "$DIR" ]]; then
    echo "Usage: $0 directory"
    echo 'Provide path of your media directory as argument to this script.'
    exit 0
fi

mp4file="$DIR/source-mp4file-paths.txt"
trap oninterrupt INT QUIT TERM

# Find all .mp4 files (UTF-8 safe)
find "$DIR" -type f -iname "*.mp4" | sort > "$mp4file"

totalcount=$(wc -l < "$mp4file")
count=0
successcount=0
echo "[$totalcount] files to process"

# Read each file
while IFS= read -r eachMp4File || [[ -n "$eachMp4File" ]]; do
    let count++

    # Skip files that already include _360p or _480p
    if [[ "$eachMp4File" == *_360p.* || "$eachMp4File" == *_480p.* ]]; then
        echo "[$count/$totalcount] Skipping (already converted): $eachMp4File"
        continue
    fi

    base="${eachMp4File%.*}"
    ext="${eachMp4File##*.}"

    current360="${base}_360p.$ext"
    current480="${base}_480p.$ext"

    did_convert=false

    echo "[$count/$totalcount] Processing: $eachMp4File"

    # Convert to 360p
    if [[ ! -f "$current360" ]]; then
        echo "    -> Converting to 360p"
        ffmpeg -nostdin -y -v error -i "$eachMp4File" -vf "scale=-2:360" -c:a copy "$current360" \
            || { echo "    !! Failed 360p, cleaning"; rm -f "$current360"; continue; }
        did_convert=true
    else
        echo "    -> Skipping 360p (already exists)"
    fi

    # Convert to 480p
    if [[ ! -f "$current480" ]]; then
        echo "    -> Converting to 480p"
        ffmpeg -nostdin -y -v error -i "$eachMp4File" -vf "scale=-2:480" -c:a copy "$current480" \
            || { echo "    !! Failed 480p, cleaning"; rm -f "$current480"; continue; }
        did_convert=true
    else
        echo "    -> Skipping 480p (already exists)"
    fi

    [[ "$did_convert" == true ]] && let ++successcount

    current360=""
    current480=""
done < "$mp4file"

echo "[$successcount/$totalcount] files had at least one new conversion"
rm -f "$mp4file"
exit 0
