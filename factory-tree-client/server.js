var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var redis = require('redis');
var conf = require('config');
var port = process.env.PORT || 3000;

var redisHost = conf.get('redisHost');
var redisPort = conf.get('redisPort');
var redisPassword = conf.get('redisPassword');
var serverHost = conf.get('serverHost');

app.use('/static', require('express').static(__dirname + '/public'));

app.get('/', function(req, res) {
  res.sendFile(__dirname + '/app/index.html');
});

app.get('/serverHost', function(req, res) {
  res.send(serverHost);
});

var subCreate = redis.createClient(redisPort, redisHost);
var subUpdate = redis.createClient(redisPort, redisHost);
var subDelete = redis.createClient(redisPort, redisHost);
var subChildNodes = redis.createClient(redisPort, redisHost);

if (redisPassword) {
  subCreate.auth(redisPassword);
  subUpdate.auth(redisPassword);
  subDelete.auth(redisPassword);
  subChildNodes.auth(redisPassword);
}

subCreate.subscribe('factory-create');
subCreate.on('message', function(channel, factory) {
  io.emit('create', factory);
});

subUpdate.subscribe('factory-update');
subUpdate.on('message', function(channel, factory) {
  io.emit('update', factory);
});

subDelete.subscribe('factory-delete');
subDelete.on('message', function(channel, factory) {
  io.emit('delete', factory);
});

subChildNodes.subscribe('factory-child');
subChildNodes.on('message', function(channel, factory) {
  io.emit('addChildNodes', factory);
});

io.on('connection', function(socket) {});

http.listen(port, function() {
  console.log('listening on *:3000');
});
