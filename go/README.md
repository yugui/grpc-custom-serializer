# Custom serialization in GRPC (Go)

This pair of client/server implementations shows how to customize the
serialization method in GRPC in Go.

# How to build with go tool chain
## Prerequisites
* [Go compiler tool chain](https://golang.org/dl/)
* [protoc](https://developers.google.com/protocol-buffers/docs/downloads)
* [Go support for Protocol Buffers](https://github.com/golang/protobuf)

e.g.

```console
$ brew install go protobuf
$ go get github.com/golang/protobuf
```

## Build steps

```console
$ go get github.com/yugui/grpc-custom-serializer/go
$ cd go
$ go generate
$ go build -o greeter_client github.com/yugui/grpc-custom-serializer/go/client
$ go build -o greeter_server github.com/yugui/grpc-custom-serializer/go/server

$ ./greeter_server -logtostderr &
$ ./greeter_client -logtostderr -name=$USER
```

# How to build with Bazel

```console
$ bazel build //go/...
$ bazel-bin/go/server/darwin_amd64_stripped/server -logtostderr &
$ bazel-bin/go/client/darwin_amd64_stripped/client -logtostderr -name=$USER
```
