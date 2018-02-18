package(default_visibility = ["//visibility:private"])

load("@org_pubref_rules_protobuf//cpp:rules.bzl", "cc_proto_library")

cc_binary(
    name = "greeter_server",
    srcs = ["server_main.cc"],
    deps = [
        ":serialization",
        "//:greeter_proto",
        "//external:gflags",
        "//external:glog",
    ],
)

cc_binary(
    name = "greeter_client",
    srcs = ["client_main.cc"],
    deps = [
        ":serialization",
        "//:greeter_proto",
        "//external:gflags",
        "//external:glog",
    ],
)

cc_library(
    name = "serialization",
    srcs = ["serialization.cc"],
    hdrs = ["serialization.h"],
    deps = [
        "//:greeter_proto",
        "//external:glog",
        "@com_google_grpc//:grpc++",
        "@com_google_grpc//:grpc_codegen",
        "@com_google_protobuf//:protobuf",
    ],
    alwayslink = 1,
)
