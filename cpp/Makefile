CXXFLAGS += -std=c++11
LDFLAGS += -L/usr/local/lib `pkg-config --libs protobuf grpc++ grpc gflags` -lglog

PROTO=../greeter.proto
GENFILES=greeter.pb.cc \
	 greeter.pb.h \
	 greeter.grpc.pb.cc \
	 greeter.grpc.pb.h
GRPC_PLUGIN=`which grpc_cpp_plugin`
TARGETS=greeter_server greeter_client
SERVER_OBJS=server_main.o greeter.pb.o greeter.grpc.pb.o serialization.o
CLIENT_OBJS=client_main.o greeter.pb.o greeter.grpc.pb.o serialization.o

all: $(TARGETS)
greeter_server: $(SERVER_OBJS)
	$(CXX) $(CXXFLAGS) $(LDFLAGS) -o greeter_server $(SERVER_OBJS)
greeter_client: $(CLIENT_OBJS)
	$(CXX) $(CXXFLAGS) $(LDFLAGS) -o greeter_client $(CLIENT_OBJS)

server_main.o: server_main.cc serialization.h greeter.pb.h greeter.grpc.pb.h
client_main.o: client_main.cc serialization.h greeter.pb.h greeter.grpc.pb.h
serialization.o: serialization.cc serialization.h greeter.pb.h
$(GENFILES): $(PROTO)
	protoc -I.. --plugin=protoc-gen-grpc=$(GRPC_PLUGIN) \
		--grpc_out=. --cpp_out=. $(PROTO)

clean:
	@rm -f $(GENFILES) $(TARGETS) $(SERVER_OBJS) $(CLIENT_OBJS)
