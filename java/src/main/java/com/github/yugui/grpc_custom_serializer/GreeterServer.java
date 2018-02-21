package com.github.yugui.grpc_custom_serializer;

import static com.github.yugui.grpc_custom_serializer.GreeterDescriptions.METHOD_GREET;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;

import com.github.yugui.grpc_custom_serializer.GreeterOuterClass.RequestProto;
import com.github.yugui.grpc_custom_serializer.GreeterOuterClass.ResponseProto;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerCallHandler;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.ServerCalls.UnaryMethod;
import io.grpc.stub.StreamObserver;

public class GreeterServer {

  private static Logger logger =
      Logger.getLogger(GreeterServer.class.getName());

  private final int port;
  private final Server server;

  public GreeterServer(int port) {
    this.port = port;
    server = ServerBuilder.forPort(port).addService(new GreeterImpl()).build();
  }

  public void start() throws IOException {
    logger.log(Level.FINE, "Starting listening on {0}", port);
    server.start();
    logger.log(Level.INFO, "Listening on {0}", port);

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.err.println("shutting down the GreeterServer");
        GreeterServer.this.stop();
        System.err.println("GreeterServer shut down");
      }
    });
  }

  public void stop() {
    server.shutdown();
  }

  private void blockUntilShutdown() throws InterruptedException {
    server.awaitTermination();
  }

  public static void main(String[] args) throws Exception {
    final Options options = new Options();
    options.parse(args);
    
    final GreeterServer server = new GreeterServer(options.port);
    try {
      server.start();
    } catch (IOException e) {
      logger.log(Level.WARNING, "Failed to start the server", e);
      return;
    }

    server.blockUntilShutdown();
  }

  public static class GreeterImpl implements BindableService {
    public ServerServiceDefinition bindService() {
      final ServerCallHandler<RequestProto, ResponseProto> greet =
          asyncUnaryCall(new UnaryMethod<RequestProto, ResponseProto>() {
            @Override
            public void invoke(RequestProto request,
                StreamObserver<ResponseProto> responseObserver) {
              GreeterImpl.this.greet(request, responseObserver);
            }
          });
      return ServerServiceDefinition
          .builder(GreeterGrpc.getServiceDescriptor().getName())
          .addMethod(METHOD_GREET, greet).build();
    }

    public void greet(RequestProto request,
        StreamObserver<ResponseProto> responseObserver) {
      logger.log(Level.FINER, "request: {0}", request);
      ResponseProto response = ResponseProto.newBuilder()
          .setMessage("Hello " + request.getName()).build();
      logger.log(Level.FINER, "response: {0}", response);
          
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    }
  }

  private static class Options {
    @Option(name = "-port", usage = "port to listen")
    private int port = 5000;
    
    private void parse(String[] args) {
      CmdLineParser parser = new CmdLineParser(this);
      try {
        parser.parseArgument(args);
      } catch (CmdLineException e) {
        System.err.println(e.getMessage());
        parser.printUsage(System.err);
        System.exit(1);
      }
    }
  }
}
