require 'grpc'

require 'greeter/greeter_services_pb'

# Implementation of Greeter service
class GreeterServer < Greeter::Greeter::Service
  def greet(request, _)
    Greeter::ResponseProto.new(message: "Hello, #{request.name}")
  end
end
