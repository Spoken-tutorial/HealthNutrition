#!/bin/bash

# Ensure UTF-8 locale for handling filenames in Indian languages
export LANG="en_US.UTF-8"
export LC_ALL="en_US.UTF-8"

BASE_DIR="Tutorial"
VIDEO_SUBDIR="Video"
VTT_DIR="Tutorial/VttFile"
TMP_DIR="$BASE_DIR/tempVideos"   # Temp folder for all intermediate files
mkdir -p "$TMP_DIR"

# Auto-detect tutorial IDs
tutorial_ids=()
for dir in "$BASE_DIR"/[0-9]*/; do
    id=$(basename "$dir")
    tutorial_ids+=("$id")
done

# Counters
total_videos=0
expected_to_convert=0
converted=0
skipped=0
skipped_novtt=0
failed=0
progress_count=0
reprocessed_partial=0

# Trap to clean temp files on interruption
cleanup() {
    echo ""
    echo "⚠️ Interrupted! Cleaning up temp files..."
    find "$TMP_DIR" -type f -iname "*_withSubtitles.tmp.mp4" -delete
    echo ""
    echo "📊 Summary so far:"
    echo " Total videos scanned : $total_videos"
    echo " Expected conversions : $expected_to_convert"
    echo " ⏩ Skipped (already converted) : $skipped"
    echo " ⏪ Reprocessed partial conversions : $reprocessed_partial"
    echo " ⚠️ Skipped (no VTT) : $skipped_novtt"
    echo " ✅ Converted : $converted"
    echo " ❌ Failed : $failed"
    exit 1
}
trap cleanup INT QUIT TERM

# Function to decide if video should be processed
should_process() {
    local video_file="$1"
    local final_output="${video_file%.mp4}_withSubtitles.mp4"

    # Skip if original has "_withSubtitles"
    if [[ "$video_file" == *_withSubtitles*.mp4 ]]; then
        return 1
    fi

    # If final output exists
    if [[ -f "$final_output" ]]; then
        # Compare file sizes to check for incomplete conversion
        orig_size=$(stat -c%s "$video_file")
        out_size=$(stat -c%s "$final_output")
        if (( out_size < orig_size )); then
            # Partial conversion detected, reprocess
            return 0
        else
            return 1
        fi
    fi

    return 0
}

# First pass: calculate expected conversions
for id in "${tutorial_ids[@]}"; do
    video_dir="$BASE_DIR/$id/$VIDEO_SUBDIR"
    vtt_file="$VTT_DIR/$id.vtt"

    [[ ! -d "$video_dir" ]] && continue

    if [[ -f "$vtt_file" ]]; then
        for video_file in "$video_dir"/*.mp4; do
            [[ ! -f "$video_file" ]] && continue

            if should_process "$video_file"; then
                ((total_videos++))
                ((expected_to_convert++))
            else
                ((skipped++))
            fi
        done
    else
        num_videos=$(find "$video_dir" -maxdepth 1 -type f -iname "*.mp4" | wc -l)
        ((skipped_novtt+=num_videos))
        ((total_videos+=num_videos))
    fi
done

echo "🎯 Total videos found (with or without VTT): $total_videos"
echo "⚡ Expected videos to convert (VTT exists & not fully converted/skipped): $expected_to_convert"

# Start processing videos
for id in "${tutorial_ids[@]}"; do
    echo ""
    echo "=== Processing Tutorial ID: $id ==="

    video_dir="$BASE_DIR/$id/$VIDEO_SUBDIR"
    vtt_file="$VTT_DIR/$id.vtt"
    ass_file="$VTT_DIR/$id.ass"

    [[ ! -d "$video_dir" ]] && { echo " ⚠️ No video directory for ID=$id, skipping..."; continue; }

    if [[ -f "$vtt_file" ]]; then
        echo " ✅ Found VTT: $vtt_file"

        # Convert VTT → ASS if missing
        if [[ ! -f "$ass_file" ]]; then
            ffmpeg -nostdin -y -hide_banner -nostats -i "$vtt_file" -c:s ass "$ass_file"
            if [[ $? -ne 0 ]]; then
                echo " ❌ Failed to convert VTT to ASS"
                continue
            fi
        else
            echo " ✅ ASS already exists: $ass_file"
        fi

        # Process videos
        for video_file in "$video_dir"/*.mp4; do
            [[ ! -f "$video_file" ]] && continue

            final_output="${video_file%.mp4}_withSubtitles.mp4"

            if should_process "$video_file"; then
                tmp_output="$TMP_DIR/$(basename "${video_file%.mp4}_withSubtitles.tmp.mp4")"
                [[ -f "$tmp_output" ]] && rm -f "$tmp_output"

                ((progress_count++))
                echo " [${progress_count}/${expected_to_convert}] 🎥 Burning subs into: $(basename "$video_file")"

                ffmpeg -nostdin -y -hide_banner -nostats -i "$video_file" -vf "ass=$ass_file" "$tmp_output"
                if [[ $? -eq 0 ]]; then
                    mv "$tmp_output" "$final_output"

                    # Count reprocessed partials
                    orig_size=$(stat -c%s "$video_file")
                    out_size=$(stat -c%s "$final_output")
                    if (( out_size < orig_size )); then
                        ((reprocessed_partial++))
                    fi

                    echo " ✅ Done: $final_output"
                    ((converted++))
                else
                    echo " ❌ Failed: $video_file"
                    rm -f "$tmp_output"
                    ((failed++))
                fi
            else
                ((skipped++))
            fi
        done
    else
        echo " ⚠️ No VTT file found for ID=$id → skipping all videos in this folder"
        num_videos=$(find "$video_dir" -maxdepth 1 -type f -iname "*.mp4" | wc -l)
        ((skipped_novtt+=num_videos))
    fi
done

# Remove temp folder if empty
[[ -d "$TMP_DIR" ]] && [[ -z "$(ls -A "$TMP_DIR")" ]] && rmdir "$TMP_DIR"

echo ""
echo "📊 Final Summary:"
echo " Total videos scanned : $total_videos"
echo " Expected conversions : $expected_to_convert"
echo " ✅ Converted : $converted"
echo " ⏪ Reprocessed partial conversions : $reprocessed_partial"
echo " ⏩ Skipped (already converted) : $skipped"
echo " ⚠️ Skipped (no VTT) : $skipped_novtt"
echo " ❌ Failed : $failed"
