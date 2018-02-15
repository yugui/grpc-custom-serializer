load("@io_bazel_rules_go//go:def.bzl", "go_library")
load("@io_bazel_rules_go//proto:def.bzl", "go_proto_library")

package(default_visibility = ["//visibility:private"])

load("@org_pubref_rules_protobuf//cpp:rules.bzl", "cc_proto_library")

filegroup(
    name = "greeter_proto_files",
    srcs = ["greeter.proto"],
    visibility = ["//:__subpackages__"],
)

cc_proto_library(
    name = "greeter_proto",
    protos = [":greeter_proto_files"],
    visibility = ["//:__subpackages__"],
)

load("@bazel_gazelle//:def.bzl", "gazelle")

gazelle(
    name = "gazelle",
    prefix = "github.com/yugui/grpc-custom-serializer",
)

proto_library(
    name = "greeterproto_proto",
    srcs = ["greeter.proto"],
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
