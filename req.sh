#!/bin/bash

# Request giphy api through local server
# curl -v 'https://api.giphy.com/v1/gifs/search?q=Im+excited&api_key=dc6zaTOxFJmzC' --connect-to api.giphy.com:443:127.0.0.1:8443
curl  'https://api.giphy.com/v1/gifs/search?q=Im+excited&api_key=dc6zaTOxFJmzC' --connect-to api.giphy.com:443:127.0.0.1:8443 | jq "."