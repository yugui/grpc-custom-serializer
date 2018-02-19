#!/usr/bin/ruby

require 'logger'
require 'optparse'

require 'greeter/client'
require 'greeter/json_serialization'

# Command line options
Options = Struct.new(:addr, :name)


# Processes options in the arguments
def process_options(argv)
  args = Options.new('0.0.0.0:5000', 'world')
  parser = OptionParser.new do |opts|
    opts.on('--addr=ADDR', 'address to litsten') do |val|
      args.addr = val
    end

    opts.on('--name=NAME', 'name to greet to') do |val|
      args.name = val
    end
  end
  parser.parse!(argv)

  return args
end


def run(argv)
  args = process_options(argv)

  Greeter::Greeter::Service.use_json_marshaler
  client = GreeterClient.new(args.addr)
  puts client.greet(args.name)
rescue => ex
  logger.error "Failed to greet #{ex}"
end


def logger
  @logger ||= Logger.new(STDERR)
end


if __FILE__ == $0
  run(ARGV)
end
