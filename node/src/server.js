'use strict';

const messages = require('../proto/greeter_pb');
const services = require('../proto/greeter_grpc_pb');

/**
 * An implementation of Greeter service.
 */
class GreeterServer {
  greet(call, callback) {
    const response = new messages.ResponseProto();
    response.setMessage(`Hello, ${call.request.getName()}`);
    callback(null, response);
  }
};

/**
 * Registers an instance of GreeterServer to the given server.
 * @param {grpc.Server} server - a grpc server.
 */
function registerGreeterService(server) {
  server.addService(services.GreeterService, new GreeterServer());
}

module.exports = {
  GreeterServer: GreeterServer,
  registerGreeterService: registerGreeterService,
};

