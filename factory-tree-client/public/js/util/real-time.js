var Realtime = (function() {
  var socket = io();
  globals.updateFromSocket = true;

  socket.on('create', function(factory) {
    factory = JSON.parse(factory);
    FactoryTree.addFactory(factory, function() {
      $('.messages').hide();
      $('#tree-container').scrollTop($('#tree-container')[0].scrollHeight);
      alertify.success('Created new factory');
    });
  });

  socket.on('update', function(factory) {
    factory = JSON.parse(factory);
    FactoryTree.updateFactory(factory, function() {
      alertify.success('Updated factory ' + factory.text);
      if (factory.rangeChanged) {
        alertify.log('Note: Factory range has changed. Removed existing nodes.');
      }
    });
  });

  socket.on('delete', function(factory) {
    factory = JSON.parse(factory);
    FactoryTree.removeFactory(factory.id, function() {
      alertify.success('Deleted factory ' + factory.text);
    });
  });

  socket.on('addChildNodes', function(factory) {
    factory = JSON.parse(factory);
    FactoryTree.addChildren(factory.nodes, factory.id, function() {
      alertify.success('Generated ' + factory.nodes.length + ' child nodes for ' + factory.text);
    });
  });
})();
