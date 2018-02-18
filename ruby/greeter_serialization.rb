require 'greeter_services_pb'

Greeter::Greeter::Service.class_eval do
  rpc_descs.each do |name, desc|
    desc.marshal_method = :encode_json
    desc.unmarshal_method = :decode_json
  end
end
