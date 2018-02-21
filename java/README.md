# Custom serialization in gRPC (Java)

This pair of client/server implementations shows how to customize the
serialization method in gRPC in Java.

# How to run 
## Prerequisites
* JDK
* [maven](https://maven.apache.org/)

e.g.

```console
$ brew cask install java
$ brew install maven
```

## Build steps

```console
$ cd java
$ mvn compile
```

## Run

```console
$ mvn exec:java -Dexec.mainClass=com.github.yugui.grpc_custom_serializer.GreeterServer&
$ mvn exec:java -Dexec.mainClass=com.github.yugui.grpc_custom_serializer.GreeterClient -Dexec.args="-user $USER"
```

# How to run with Bazel

```console
$ bazel build //java:all
$ bazel-bin/java/src/main/java/com/github/yugui/grpc_custom_serializer/GreeterServer &
$ bazel-bin/java/src/main/java/com/github/yugui/grpc_custom_serializer/GreeterClient -- -user $USER
```
