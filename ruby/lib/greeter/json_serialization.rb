require 'grpc'

module GRPC::GenericService::Dsl
  def use_json_marshaler(*method_names)
    targets = rpc_descs
    unless method_names.empty?
      targets = targets.select {|name,| method_names.include?(name)}
    end
    targets.each do |name, desc|
      desc.marshal_method = :encode_json
      desc.unmarshal_method = :decode_json
    end
    self
  end
end
