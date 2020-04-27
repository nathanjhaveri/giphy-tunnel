Giphy Tunnel
============

## About 

A Simple tcp server that proxies requests to api.giphy.com.  This server can
preserve the privacy of the client, while still allowing encrypted traffic so
that the server itself can't snoop on the request specifics. 

See the [signal blog](https://signal.org/blog/giphy-experiment/) for more info
about the motivation/idea.

## To run



## Request to server

Here is an example request to this sever

```
curl 'https://api.giphy.com/v1/gifs/search?q=Im+excited&api_key=dc6zaTOxFJmzC' --connect-to api.giphy.com:443:127.0.0.1:8443
```
