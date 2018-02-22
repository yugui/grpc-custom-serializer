package com.github.yugui.grpc_custom_serializer;

import static com.github.yugui.grpc_custom_serializer.GreeterDescriptions.METHOD_GREET;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;

import com.github.yugui.grpc_custom_serializer.GreeterOuterClass.RequestProto;
import com.github.yugui.grpc_custom_serializer.GreeterOuterClass.ResponseProto;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.AbstractStub;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public final class GreeterClient {
  private static final Logger logger = Logger.getLogger(GreeterClient.class.getName());

  private final ManagedChannel channel;
  private final GreeterStub stub;

  public GreeterClient(String host, int port) {
    channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
    stub = new GreeterStub(channel);
  }

  public String greet(String name) {
    logger.info("invoking greet.Greeter.Greet with name=" + name);
    RequestProto request = RequestProto.newBuilder().setName(name).build();
    try {
      return stub.greet(request).getMessage();
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "Failed to invoke greeter.Greeter.Greet: {0}", e.getStatus());
      throw e;
    }
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public static void main(String[] args) throws Exception {
    final Options options = new Options();
    options.parse(args);

    GreeterClient client = new GreeterClient(options.host, options.port);
    String message;
    try {
      message = client.greet(options.name);
      System.out.println(message);
    } catch (Exception e) {
      logger.log(Level.WARNING, "Failed to greet: {0}", e.getMessage());
      return;
    } finally {
      client.shutdown();
    }
  }

  private static final class GreeterStub extends AbstractStub<GreeterStub> {
    protected GreeterStub(Channel channel) {
      super(channel);
    }

    private GreeterStub(Channel channel, CallOptions options) {
      super(channel, options);
    }

    @Override
    protected GreeterStub build(Channel channel, CallOptions options) {
      return new GreeterStub(channel, options);
    }

    ResponseProto greet(RequestProto request) {
      return blockingUnaryCall(getChannel(), METHOD_GREET, getCallOptions(), request);
    }
  }

  private static final class Options {
    @Option(name = "-name", usage = "name to greet to")
    private String name = "world";

    @Option(name = "-host", usage = "server host")
    private String host = "localhost";

    @Option(name = "-port", usage = "server port")
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
