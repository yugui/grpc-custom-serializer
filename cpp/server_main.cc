#include <gflags/gflags.h>
#include <grpc++/grpc++.h>

#include "greeter.grpc.pb.h"
#include "serialization.h"

DEFINE_string(addr, "0.0.0.0:5000", "addr to listen on");

namespace greeter {
using grpc::Status;
using grpc::ServerContext;

class GreeterSerivceImpl final : public Greeter::Service {
  Status Greet(ServerContext* context, const RequestProto* request,
               ResponseProto* reply) override;
};

Status GreeterSerivceImpl::Greet(ServerContext* context,
                                 const RequestProto* request,
                                 ResponseProto* reply) {
  reply->set_message("Hello, " + request->name());
  return Status::OK;
}
}  // namespace greeter

void RunService() {
  greeter::GreeterSerivceImpl service;
  grpc::ServerBuilder builder;

  builder.AddListeningPort(FLAGS_addr, grpc::InsecureServerCredentials());
  builder.RegisterService(&service);

  const auto server(builder.BuildAndStart());
  std::cerr << "Listening on " << FLAGS_addr << std::endl;

  server->Wait();
}

int main(int argc, char** argv) {
  gflags::ParseCommandLineFlags(&argc, &argv, true);
  RunService();
  return 0;
}
