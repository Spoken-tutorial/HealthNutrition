#!/bin/bash

# configuration
NAME="HealthNutrition"
SERVICE="tomcat"
CONFIG_DIR="/root/tomcat/$NAME"
SOURCE_DIR="/tmp"
TARGET_DIR="/beta_st/tomcat.new/webapps"
RESTART="yes"
STATUS="no"
# end configuration

WAR_FILE="$NAME.war"
SOURCE_WAR_FILE="$SOURCE_DIR/$WAR_FILE"
TARGET_WAR_FILE="$TARGET_DIR/$WAR_FILE"

# Step 1: Stop the service
if [[ "$RESTART" = yes ]]; then
  echo "Stopping $SERVICE service..."
  service $SERVICE stop || {
    echo "Failed to stop $SERVICE service."
  }
fi

# Step 2: Navigate to the directory
echo "Navigating to source directory: $CONFIG_DIR"
cd $CONFIG_DIR || {
  echo "Failed to navigate to $CONFIG_DIR. Exiting."
  exit 1
}

# Step 3: Update the WAR file
echo "Updating WAR file: $SOURCE_WAR_FILE"
zip -r $SOURCE_WAR_FILE WEB-INF/ || {
  echo "Failed to create WAR file. Exiting."
  exit 1
}

# Step 4: Save the old WAR file with a timestamp
if [[ -f "$TARGET_WAR_FILE" ]]; then
  OLD_WAR_FILE="$TARGET_DIR.old/$WAR_FILE.$(date +"%Y%m%d.%H%M")"
  echo "Backing up existing WAR file to $OLD_WAR_FILE"
  mkdir -p "$TARGET_DIR.old"
  cp -f "$TARGET_WAR_FILE" "$OLD_WAR_FILE" || {
    echo "Failed to back up existing WAR file. Exiting."
    exit 1
  }
fi

# Step 5: Remove application directory
if [[ "$RESTART" = yes ]]; then
  echo "Removing existing application directory: $TARGET_DIR/$NAME"
  rm -rf "$TARGET_DIR/$NAME" || {
    echo "Failed to remove existing application directory. Exiting."
    exit 1
  }
fi

# Step 6: Copy the WAR file to the target directory
echo "Copying $SOURCE_WAR_FILE to $TARGET_DIR"
cp -f $SOURCE_WAR_FILE $TARGET_DIR || {
  echo "Failed to copy WAR file to $TARGET_DIR. Exiting."
  exit 1
}

# Step 7: Start the service
if [[ "$RESTART" = yes ]]; then
  echo "Starting $SERVICE service..."
  service $SERVICE start || {
    echo "Failed to start $SERVICE service. Exiting."
    exit 1
  }
fi

sleep 20

# Step 8: Check the status of the service
if [[ "$RESTART" = yes && "$STATUS" = yes ]]; then
  echo "Checking $SERVICE service status..."
  service $SERVICE status || {
    echo "$SERVICE service is not running properly. Please check the logs."
    exit 1
  }
fi

# Step 9: Display the last 30 lines of the tomcat log file
tail -n 20 $TARGET_DIR/../logs/catalina.out

echo "Deployment completed successfully."
