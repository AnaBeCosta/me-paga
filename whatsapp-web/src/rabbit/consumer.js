const amqp = require('amqplib');

async function consumeFromQueue(queue, whatsappClient) {
    let conn;
    let channel;

    try {
        conn = await amqp.connect('amqp://localhost');
        channel = await conn.createChannel();

        await channel.assertQueue(queue, { durable: true });

        console.log(`📥 Aguardando mensagens na fila "${queue}"...`);

        channel.consume(queue, async (msg) => {
            if (!msg) return;

            try {
                const data = JSON.parse( msg.content.toString());
                const chatId = `${data.numero}@c.us`;

                await whatsappClient.sendMessage(chatId, data.mensagem);

                console.log(data);
                channel.ack(msg);
            } catch (err) {
                console.error('❌ Erro ao processar mensagem:', err);
                // Se quiser reencaminhar para fila de erro, pode usar channel.nack(msg, false, false)
                channel.nack(msg, false, false);
            }
        });
    } catch (err) {
        console.error('❌ Erro ao conectar no RabbitMQ:', err);
    }
}

module.exports = { consumeFromQueue };
