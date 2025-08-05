const amqp = require('amqplib');

async function sendToQueue(queue, message) {
    const conn = await amqp.connect('amqp://localhost');
    const channel = await conn.createChannel();

    await channel.assertQueue(queue, { durable: true });
    channel.sendToQueue(queue, Buffer.from(JSON.stringify(message)));

    console.log(`Sent message to queue "${queue}"`);
    await channel.close();
    await conn.close();
}

module.exports = { sendToQueue };
