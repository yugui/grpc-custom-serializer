package com.github.yugui.grpc_custom_serializer;

import com.github.yugui.grpc_custom_serializer.GreeterOuterClass.RequestProto;
import com.github.yugui.grpc_custom_serializer.GreeterOuterClass.ResponseProto;

import io.grpc.MethodDescriptor;
import io.grpc.protobuf.ProtoUtils;

final class GreeterDescriptions {
  static final MethodDescriptor<RequestProto, ResponseProto> METHOD_GREET =
      GreeterGrpc.METHOD_GREET
          .toBuilder(
              ProtoUtils.jsonMarshaller(RequestProto.getDefaultInstance()),
              ProtoUtils.jsonMarshaller(ResponseProto.getDefaultInstance()))
          .build();
}
