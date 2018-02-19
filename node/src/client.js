'use strict';

const messages = require('../proto/greeter_pb');
const services = require('../proto/greeter_grpc_pb');

const {promisify} = require('util');
const grpc = require('grpc');

/**
 * Wrapper of the client stub of Greeter serivce.
 */
class GreeterClient {
  constructor(addr) {
    this.client = new services.GreeterClient(addr,
      grpc.credentials.createInsecure());
  }

  /**
   * @param {string} name - the name to greet to.
   * @returns {Promise} Promise object represents the greeting message
   *   from the server.
   */
  greet(name) {
    const request = new messages.RequestProto();
    request.setName(name);
    return new Promise((resolve, reject) => {
      this.client.greet(request, (err, response) => {
        if (err) {
          reject(err);
          return;
        }
        resolve(response.getMessage());
      });
    });
  }
};

module.exports = {
  GreeterClient: GreeterClient,
};
