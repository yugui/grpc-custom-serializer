workspace(name = "com_github_yugui_grpc_custom_serializer")

git_repository(
    name = "org_pubref_rules_protobuf",
    remote = "https://github.com/pubref/rules_protobuf",
    tag = "v0.8.1",
)

git_repository(
    name = "com_github_gflags_gflags",
    remote = "https://github.com/gflags/gflags",
    tag = "v2.2.1",
)

load("@org_pubref_rules_protobuf//cpp:rules.bzl", "cpp_proto_repositories")

cpp_proto_repositories()

bind(
    name = "gflags",
    actual = "@com_github_gflags_gflags//:gflags",
)
