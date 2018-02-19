'use strict';

const greeter = require('../src/server');

const grpc = require('grpc');

function main() {
  const server = new grpc.Server();
  greeter.registerGreeterService(server);
  server.bind('0.0.0.0:5000', grpc.ServerCredentials.createInsecure());

  console.log(`listening on ${addr}`)
  server.start();
}

main();
