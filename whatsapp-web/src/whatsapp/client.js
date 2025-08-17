const { Client, LocalAuth } = require('whatsapp-web.js');
const qrcode = require('qrcode-terminal');
const express = require('express');
const bodyParser = require('body-parser');

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
  client.sendMessage("5515996415201", "taserto");
  console.log('✅ Cliente WhatsApp está pronto!');
});

client.on('auth_failure', (msg) => {
  console.error('❌ Falha na autenticação:', msg);
  client.destroy();     // encerra cliente atual
  client.initialize();
});

client.on('disconnected', (reason) => {
  console.log('🔌 Cliente desconectado:', reason);
});

client.initialize();
module.exports = client;
