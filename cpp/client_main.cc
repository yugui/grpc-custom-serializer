#include <memory>
#include <string>

#include <gflags/gflags.h>
#include <glog/logging.h>
#include <grpc++/grpc++.h>

#include "greeter.grpc.pb.h"
#include "serialization.h"

DEFINE_string(addr, "0.0.0.0:5000", "addr to connect to");
DEFINE_string(name, "world", "target to greet to");

namespace greeter {
class GreeterClient {
 public:
  GreeterClient(std::shared_ptr<grpc::Channel> channel)
      : stub_(Greeter::NewStub(channel)) {}

  std::string Greet(const std::string& name);

 private:
  std::unique_ptr<Greeter::Stub> stub_;
};

std::string GreeterClient::Greet(const std::string& name) {
  RequestProto request;
  request.set_name(name);

  ResponseProto response;
  grpc::ClientContext context;
  grpc::Status status = stub_->Greet(&context, request, &response);
  if (status.ok()) {
    return response.message();
  } else {
    LOG(ERROR) << "Failed to call greeter.Greeter.Greet: "
               << status.error_code() << ":" << status.error_message();
    return "";
  }
}
}  // namespace greeter

void Run() {
  const auto channel(
      grpc::CreateChannel(FLAGS_addr, grpc::InsecureChannelCredentials()));
  greeter::GreeterClient client(channel);

  std::cout << client.Greet(FLAGS_name) << std::endl;
}

int main(int argc, char** argv) {
  google::InitGoogleLogging(argv[0]);
  gflags::ParseCommandLineFlags(&argc, &argv, true);

  Run();
  return 0;
}
