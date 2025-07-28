#!/bin/bash

# Ensure UTF-8 locale for handling filenames in Indian languages
export LANG="en_US.UTF-8"
export LC_ALL="en_US.UTF-8"

oninterrupt() {
    echo ""
    echo "Interrupted. Cleaning up..."
    find "$TMPDIR" -type f -iname "*.mp4" -exec rm -f {} +
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
TMPDIR="$DIR/.tmp_convert"
mkdir -p "$TMPDIR"
trap oninterrupt INT QUIT TERM

# Find only original .mp4 files, skip already converted or temp files
find "$DIR" -type f -iname "*.mp4" \
    ! -iname "*_360p.mp4" \
    ! -iname "*_480p.mp4" \
    ! -path "*/.tmp_convert/*" \
    | sort > "$mp4file"

totalcount=$(wc -l < "$mp4file")
count=0
successcount=0
echo "[$totalcount] files to process"

# Read each file
while IFS= read -r eachMp4File || [[ -n "$eachMp4File" ]]; do
    let count++

    base="${eachMp4File%.*}"
    ext="${eachMp4File##*.}"

    target360="${base}_360p.$ext"
    target480="${base}_480p.$ext"
    temp360="$TMPDIR/$(basename "${base}_360p.$ext")"
    temp480="$TMPDIR/$(basename "${base}_480p.$ext")"

    # Skip if both converted versions exist
    if [[ -f "$target360" && -f "$target480" ]]; then
        echo "[$count/$totalcount] Skipping (already converted): $eachMp4File"
        continue
    fi

    echo "[$count/$totalcount] Processing: $eachMp4File"
    did_convert=false

    # Convert to 360p
    if [[ ! -f "$target360" ]]; then
        echo "    -> Converting to 360p"
        ffmpeg -nostdin -y -v error -i "$eachMp4File" -vf "scale=-2:360" -c:a copy "$temp360" \
            && mv "$temp360" "$target360" \
            && { echo "    ✅ 360p converted: $target360"; did_convert=true; } \
            || { echo "    ❌ Failed 360p"; rm -f "$temp360"; }
    else
        echo "    -> Skipping 360p (already exists)"
    fi

    # Convert to 480p
    if [[ ! -f "$target480" ]]; then
        echo "    -> Converting to 480p"
        ffmpeg -nostdin -y -v error -i "$eachMp4File" -vf "scale=-2:480" -c:a copy "$temp480" \
            && mv "$temp480" "$target480" \
            && { echo "    ✅ 480p converted: $target480"; did_convert=true; } \
            || { echo "    ❌ Failed 480p"; rm -f "$temp480"; }
    else
        echo "    -> Skipping 480p (already exists)"
    fi

    [[ "$did_convert" == true ]] && let ++successcount
done < "$mp4file"

echo ""
echo "✅ Done: [$successcount/$totalcount] files had at least one new conversion"
rm -f "$mp4file"
rm -rf "$TMPDIR"
exit 0
