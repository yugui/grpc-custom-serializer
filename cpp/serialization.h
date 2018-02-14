#ifndef GREETER_CPP_SERIALIZATION_H
#define GREETER_CPP_SERIALIZATION_H

#include <grpc++/impl/codegen/config_protobuf.h>
#include <grpc++/impl/codegen/proto_utils.h>

namespace greeter {
class RequestProto;
class ResponseProto;
}  // namespace greeter

namespace grpc {
template <>
class SerializationTraits<greeter::RequestProto> {
 public:
  static Status Serialize(const grpc::protobuf::Message& msg,
                          grpc_byte_buffer** bp, bool* own_buffer);
  static Status Deserialize(grpc_byte_buffer* buffer,
                            grpc::protobuf::Message* msg);
};

template <>
class SerializationTraits<greeter::ResponseProto> {
 public:
  static Status Serialize(const grpc::protobuf::Message& msg,
                          grpc_byte_buffer** bp, bool* own_buffer);
  static Status Deserialize(grpc_byte_buffer* buffer,
                            grpc::protobuf::Message* msg);
};
}  // namespace grpc

#endif  // GREETER_CPP_SERIALIZATION_H
