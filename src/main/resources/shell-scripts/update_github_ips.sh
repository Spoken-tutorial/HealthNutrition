#!/usr/bin/env bash

set -euo pipefail

DEBUG="no"

# Config
ZONE=$(firewall-cmd --get-default-zone)
PORT=22

OLD_IPS=$(firewall-cmd --zone="$ZONE" --list-rich-rules | awk -v PORT="$PORT" '
    $0 ~ "port port=\"" PORT "\"" && $0 ~ "protocol=\"tcp\"" && $0 ~ "source address=" {
      match($0, /source address="([^ "]+)"/, m);
      if (m[1] != "") print m[1];
    }' | sort -u)
echo "Old IPs: $(wc -w <<<"$OLD_IPS")"

echo "Fetching GitHub Actions IP ranges..."
NEW_IPS=$(curl -s https://api.github.com/meta | jq -r '.actions[]' | sort -u)
if test -z "$NEW_IPS"; then
  echo "Failed to fetch new IPs, exiting."
  exit 1
fi
echo "New IPs: $(wc -w <<<"$NEW_IPS")"

# Compute diffs
TO_REMOVE=$(comm -23 <(printf "%s\n" $OLD_IPS | sort -u) <(printf "%s\n" $NEW_IPS | sort -u))
TO_ADD=$(comm -13 <(printf "%s\n" $OLD_IPS | sort -u) <(printf "%s\n" $NEW_IPS | sort -u))

# Remove IPs no longer in list
for IP in $TO_REMOVE; do
  [[ "$IP" == *:* ]] && FAMILY='ipv6' || FAMILY='ipv4'
  RULE="rule family='${FAMILY}' source address='${IP}' port port='${PORT}' protocol='tcp' accept"
  [[ "$DEBUG" != "yes" ]] || echo "Removing rule for $IP"
  firewall-cmd --zone="$ZONE" --remove-rich-rule="$RULE" >/dev/null || true
done

# Add only new IPs
for IP in $TO_ADD; do
  [[ "$IP" == *:* ]] && FAMILY='ipv6' || FAMILY='ipv4'
  RULE="rule family='${FAMILY}' source address='${IP}' port port='${PORT}' protocol='tcp' accept"
  [[ "$DEBUG" != "yes" ]] || echo "Adding rule for $IP"
  firewall-cmd --zone="$ZONE" --add-rich-rule="$RULE" >/dev/null
done

# Persist if changes
if [[ -n "$TO_ADD$TO_REMOVE" ]]; then
  echo "Persisting changes."
  firewall-cmd --runtime-to-permanent >/dev/null
  echo "✅ Firewalld updated (added $(wc -w <<<"$TO_ADD") new, removed $(wc -w <<<"$TO_REMOVE") old)"
else
  echo "No changes to persist."
fi
