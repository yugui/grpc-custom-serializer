workspace(name = "com_github_yugui_grpc_custom_serializer")

# For protobuf
git_repository(
    name = "org_pubref_rules_protobuf",
    remote = "https://github.com/pubref/rules_protobuf",
    tag = "v0.8.1",
)

# For C++
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

# For golang

http_archive(
    name = "io_bazel_rules_go",
    sha256 = "4d8d6244320dd751590f9100cf39fd7a4b75cd901e1f3ffdfd6f048328883695",
    url = "https://github.com/bazelbuild/rules_go/releases/download/0.9.0/rules_go-0.9.0.tar.gz",
)

load(
    "@io_bazel_rules_go//go:def.bzl",
    "go_rules_dependencies",
    "go_register_toolchains",
    "go_repository",
)

# Overrides go_rules_dependencies() for the new Codec API.
#
# TODO(yugui) remove this rule once the new Gazelle dependency rule
# catches google.golang.org/grpc/encoding.RegisterCodec.
go_repository(
    name = "org_golang_google_grpc",
    commit = "3926816d541db48f3e4c1c87cff75ceeb205309e",
    importpath = "google.golang.org/grpc",
)

http_archive(
    name = "bazel_gazelle",
    sha256 = "0103991d994db55b3b5d7b06336f8ae355739635e0c2379dea16b8213ea5a223",
    url = "https://github.com/bazelbuild/bazel-gazelle/releases/download/0.9/bazel-gazelle-0.9.tar.gz",
)

go_rules_dependencies()

go_register_toolchains()

load("@bazel_gazelle//:deps.bzl", "gazelle_dependencies")

gazelle_dependencies()

# For Java
load("@org_pubref_rules_protobuf//java:rules.bzl", "java_proto_repositories")

java_proto_repositories()

maven_jar(
    name = "args4j_args4j",
    artifact = "args4j:args4j:2.33",
    repository = "http://central.maven.org/maven2/",
    sha1 = "bd87a75374a6d6523de82fef51fc3cfe9baf9fc9",
)
