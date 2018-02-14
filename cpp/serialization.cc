#include <iostream>

#include <google/protobuf/util/json_util.h>
#include <grpc++/impl/codegen/config_protobuf.h>
#include <grpc++/impl/codegen/slice.h>
#include <grpc/byte_buffer.h>

#include "greeter.pb.h"
#include "serialization.h"

namespace {
grpc::Status GenericJSONSerialize(const grpc::protobuf::Message& msg,
                                  grpc_byte_buffer** bp, bool* own_buffer) {
  std::string buf;
  const auto status = google::protobuf::util::MessageToJsonString(msg, &buf);
  if (!status.ok()) {
    std::cerr << "Failed to serialize message: " << status.error_code() << ":"
              << status.error_message() << std::endl;
    return grpc::Status(grpc::StatusCode::INTERNAL,
                        "Failed to serialize message");
  }

  auto buf_slice = grpc::SliceFromCopiedString(buf);
  *bp = ::grpc_raw_byte_buffer_create(&buf_slice, 1);
  grpc_slice_unref(buf_slice);
  *own_buffer = true;

  return grpc::Status::OK;
}

grpc::Status GenericJSONDeserialize(grpc_byte_buffer* buffer,
                                    grpc::protobuf::Message* msg) {
  grpc_byte_buffer_reader reader;
  if (!grpc_byte_buffer_reader_init(&reader, buffer)) {
    std::cerr << "Failed to initialize a buffer reader from "
              << "an incoming message" << std::endl;
    return grpc::Status(grpc::StatusCode::INTERNAL, "Failed to read message");
  }

  auto buf_slice = grpc_byte_buffer_reader_readall(&reader);
  const auto status = google::protobuf::util::JsonStringToMessage(
      grpc::StringFromCopiedSlice(buf_slice), msg);
  grpc_slice_unref(buf_slice);
  grpc_byte_buffer_reader_destroy(&reader);

  if (!status.ok()) {
    std::cerr << "Failed to deserialize message: " << status.error_code() << ":"
              << status.error_message() << std::endl;
    return grpc::Status(grpc::StatusCode::INTERNAL,
                        "Failed to deserialize message");
  }
  return grpc::Status::OK;
}
}  // namespace

namespace grpc {
using greeter::RequestProto;
using greeter::ResponseProto;

Status SerializationTraits<RequestProto>::Serialize(
    const grpc::protobuf::Message& msg, grpc_byte_buffer** bp,
    bool* own_buffer) {
  return GenericJSONSerialize(msg, bp, own_buffer);
}

Status SerializationTraits<RequestProto>::Deserialize(
    grpc_byte_buffer* buffer, grpc::protobuf::Message* msg) {
  return GenericJSONDeserialize(buffer, msg);
}

Status SerializationTraits<ResponseProto>::Serialize(
    const grpc::protobuf::Message& msg, grpc_byte_buffer** bp,
    bool* own_buffer) {
  return GenericJSONSerialize(msg, bp, own_buffer);
}

Status SerializationTraits<ResponseProto>::Deserialize(
    grpc_byte_buffer* buffer, grpc::protobuf::Message* msg) {
  return GenericJSONDeserialize(buffer, msg);
}
}  // namespace grpc
