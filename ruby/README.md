# Custom serialization in gRPC (Ruby)

This pair of client/server implementations shows how to customize the
serialization method in gRPC in Ruby.

# How to run 
## Prerequisites
* [bundler](http://bundler.io/)
* [protoc](https://developers.google.com/protocol-buffers/docs/downloads)

e.g.

```console
$ brew install ruby protobuf
$ gem install bundler
```

## Build steps

```console
$ cd ruby
$ bundle install
$ bundle exec rake
```

## Run

```console
$ bundle exec ruby -I. greeter_server.rb &
$ bundle exec ruby -I. greeter_client.rb --name=$USER
```
