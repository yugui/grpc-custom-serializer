require 'logger'
require 'optparse'

require 'grpc'

require 'greeter_services_pb'
require 'greeter_serialization'

# Implementation of Greeter service
class GreeterServer < Greeter::Greeter::Service
  def greet(request, _)
    Greeter::ResponseProto.new(message: "Hello, #{request.name}")
  end
end


# Command line options
Options = Struct.new(:addr)


# Processes options in the arguments
def process_options(argv)
  args = Options.new('0.0.0.0:5000')

  parser = OptionParser.new do |opts|
    opts.on('--addr=ADDR', 'address to litsten') do |val|
      args.addr = val
    end
  end
  parser.parse!(argv)

  return args
end


def run(argv)
  options = process_options(argv)

  server = GRPC::RpcServer.new
  server.add_http2_port(options.addr, :this_port_is_insecure)
  server.handle(GreeterServer)

  logger.info "listening on #{options.addr}"
  server.run_till_terminated
end


def logger
  @logger ||= Logger.new(STDERR)
end


if __FILE__ == $0
  run(ARGV)
end
