'use strict';

const greeter = require('../src/client');

async function main() {
  const client = new greeter.GreeterClient('0.0.0.0:5000');
  try {
    console.log(await client.greet('a'));
  } catch (err) {
    console.error("Failed to call greeter: %s", err);
  }
}

main();
