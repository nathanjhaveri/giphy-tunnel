#!/bin/bash
# Examples of requests to test out this server

# Request giphy api through local server
# curl 'https://api.giphy.com/v1/gifs/search?q=Im+excited&api_key=dc6zaTOxFJmzC' --connect-to api.giphy.com:443:127.0.0.1:8443

# Verbose logging and output formatting
# curl -v 'https://api.giphy.com/v1/gifs/search?q=Im+excited&api_key=dc6zaTOxFJmzC' --connect-to api.giphy.com:443:127.0.0.1:8443 | jq "."

# Test multiple simultaneous requests
# ab -n 100 -c 10 -H 'Host: api.giphy.com' 'https://127.0.0.1:8443/v1/gifs/search?q=Im+excited&api_key=dc6zaTOxFJmzC'

