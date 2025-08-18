const { Client, LocalAuth } = require('whatsapp-web.js');
const qrcode = require('qrcode-terminal');
const { consumeFromQueue } = require('./rabbit/consumer');

const client = new Client({
  authStrategy: new LocalAuth(),
  puppeteer: {
    headless: true,
    args: ['--no-sandbox', '--disable-setuid-sandbox'],
  },
});

client.on('qr', (qr) => {
  console.log('⚠️ Escaneie o QR code abaixo com o WhatsApp:');
  qrcode.generate(qr, { small: true });
});

client.on('ready', () => {
    console.log('✅ Cliente está pronto!');

    // Agora que o cliente está pronto, podemos começar a consumir mensagens da fila.
    const queueName = 'me-paga-rabbit';
    console.log(`🚀 Iniciando consumidor da fila "${queueName}"...`);
    consumeFromQueue(queueName, client).catch(err => {
        console.error('❌ Falha ao iniciar o consumidor da fila.', err);
    });
});

client.on('message_create', message => {
	if (message.body === '!ping') {
		// send back "pong" to the chat the message was sent in
		client.sendMessage(message.from, 'pong');
	}
});


client.on('auth_failure', (msg) => {
  console.error('❌ Falha na autenticação:', msg);
  client.destroy();     // encerra cliente atual
  client.initialize();
});

client.on('disconnected', (reason) => {
  console.log('🔌 Cliente desconectado:', reason);
});

console.log("🚀 Inicializando aplicação...");
client.initialize();
