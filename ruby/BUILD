load("@org_pubref_rules_protobuf//ruby:rules.bzl", "ruby_proto_compile")

genrule(
    name = "placed_proto",
    srcs = ["//:greeter_proto_files"],
    outs = ["lib/greeter/greeter.proto"],
    cmd = " && ".join([
        "mkdir -p $(@D)",
        "cp $< $@",
    ]),
)

ruby_proto_compile(
    name = "greeterproto_ruby",
    args = [
        "-I",
        "lib",
    ],
    protos = ["lib/greeter/greeter.proto"],
)
