Giphy Tunnel
============

## About

A Simple tcp server that proxies requests to api.giphy.com.  This server can
preserve the privacy of the client, while still allowing encrypted traffic so
that the server itself can't snoop on the request.

See the [signal blog](https://signal.org/blog/giphy-experiment/) for more info
about the motivation/idea.

## How to run
### To build

From the root directory run:
```
mvn install
```

### To run

```
java -cp target/giphy-tunnel-1.0-SNAPSHOT.jar net.jhaveri.GiphyTunnelServer
```

## Request to server

Client should make requests to this server as if they were talking to
the [giphy api](https://developers.giphy.com/docs/sdk). For example:

```
curl 'https://api.giphy.com/v1/gifs/search?q=Im+excited&api_key=dc6zaTOxFJmzC' --connect-to api.giphy.com:443:127.0.0.1:8443
```

For more examples see [req.sh](./req.sh)

## Design

Goals:
- Minimal dependencies
- Easy to understand, build and run
- Handle multiple requests in parallel, while also limiting max load
- Continue to serve requests even if some fail

Non-goals:
- Production ready configuration/logging/monitoring (e.g. changing port requires changing code)
- Robust error handling

The basic design is a simple TCP server that connects the requests from a
client to the requests/responses of an upstream server.  The proxy server sets
up a fixed size thread pool, and uses it as a way to limit the number of
simultaneous requests.  Sockets have timeouts of 5s to avoid tying up the
thread pool.

If given more time here are next steps for this server:

TODO:
- Use non-blocking io rather than a thread pool, measure if it improves perf/resource usage
- Integration tests that start the server and validate it is working
- Tune simultaneous request limits based on giphy perf / throughput confort of our own server
- Add configuration for port/pool size/upstream server url rather than constants
- Graceful shutdown (e.g. allow outstanding requests to finish, display stats to user)

