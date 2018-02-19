load("@io_bazel_rules_go//go:def.bzl", "go_library")
load("@io_bazel_rules_go//proto:def.bzl", "go_proto_library")

package(default_visibility = ["//visibility:private"])

load("@org_pubref_rules_protobuf//cpp:rules.bzl", "cc_proto_library")

proto_library(
    name = "greeterproto_proto",
    srcs = ["greeter.proto"],
)

filegroup(
    name = "greeter_proto_files",
    srcs = ["greeter.proto"],
    visibility = ["//:__subpackages__"],
)

## C++

cc_proto_library(
    name = "greeterproto_cpp",
    protos = [":greeter_proto_files"],
    visibility = ["//cpp:__pkg__"],
)

## Go

load("@bazel_gazelle//:def.bzl", "gazelle")

gazelle(
    name = "gazelle",
    prefix = "github.com/yugui/grpc-custom-serializer",
)

go_proto_library(
    name = "greeterproto_go_proto",
    compilers = ["@io_bazel_rules_go//proto:go_grpc"],
    importpath = "greeterproto",
    proto = ":greeterproto_proto",
)

go_library(
    name = "go_default_library",
    embed = [":greeterproto_go_proto"],
    importpath = "greeterproto",
)
