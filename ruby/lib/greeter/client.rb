require 'grpc'

require 'greeter/greeter_services_pb'

# Wrapper of Greeter stub
class GreeterClient
  def initialize(addr)
    @stub = Greeter::Greeter::Stub.new(addr, :this_channel_is_insecure)
  end

  def greet(name)
    response = @stub.greet(Greeter::RequestProto.new(name: name))
    return response.message
  end
end
