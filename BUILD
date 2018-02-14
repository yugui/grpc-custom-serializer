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
