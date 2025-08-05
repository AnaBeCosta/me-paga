const { consumeFromQueue } = require("./rabbit/consumer");

async function start() {
  console.log("Inicializando consumidor...");
  await consumeFromQueue("me-paga-rabbit");
}

start();
