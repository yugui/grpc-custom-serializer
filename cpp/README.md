# Custom serialization in GRPC (C++)

This pair of client/server implementations shows how to customize the
serialization method in GRPC in C++.

# How to build with Make
## Prerequisites
* Make
* C++ compiler tool chain
* [gflags](https://gflags.github.io/gflags/)
* [protoc and libprotobuf](https://developers.google.com/protocol-buffers/docs/downloads)
* [GRPC](https://grpc.io/docs/quickstart/cpp.html)

e.g.

```console
$ brew install gflags protobuf grpc
```

## Build steps

```console
$ cd cpp
$ make
$ ./greeter_server &
$ ./greeter_client --name=$USER
```

# How to build with Bazel

```console
$ bazel build //cpp:all
$ bazel-bin/cpp/greeter_server &
$ bazel-bin/cpp/greeter_client --name=$USER
```
