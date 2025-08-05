const amqp = require('amqplib');
const whatsappClient = require('../whatsapp/client');

async function consumeFromQueue(queue) {
    const conn = await amqp.connect('amqp://localhost');
    const channel = await conn.createChannel();

    await channel.assertQueue(queue, { durable: true });

    channel.consume(queue, async (msg) => {
        if (msg !== null) {
            const data = JSON.parse(msg.content.toString());
            console.log('Received:', data);

            try {
                await whatsappClient.sendMessage(data.to, data.message);
                console.log('Message sent to:', data.to);
            } catch (err) {
                console.error('Error sending WhatsApp message:', err);
            }

            channel.ack(msg);
        }
    });
}

module.exports = { consumeFromQueue };
