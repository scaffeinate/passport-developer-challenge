$(document).ready(function() {

  HttpUtil.getURL(function(url) {
    HttpUtil.getFactories(url, function(treeData) {
      FactoryTree.populate(treeData);
      $('.loader').hide();

      if (treeData[0].nodes.length == 0) {
        $('.messages').show();
      }
    });

    Util.resetContextMenu();

    $('#create-factory').submit(function(e) {
      e.preventDefault();
      globals.updateFromSocket = false;
      $('.modal-loader').show();

      var factory = Util.getFormFields('create-factory');
      factory.setParentId(0);

      if (Validator.validate(this)) {
        HttpUtil.createFactory(url, factory, function(data) {
          FactoryTree.addFactory(data, function() {
            $('.modal-loader').hide();
            $('#create-factory-modal').modal('hide');
            $('#tree-container').scrollTop($('#tree-container')[0].scrollHeight);
            $('.messages').hide();
            Util.resetFormFields('create-factory');
          });
        });
      } else {
        $('.modal-loader').hide();
      }
    });

    $('#edit-factory').click(function(e) {
      globals.updateFromSocket = false;
      var factory = new Factory();
      factory.set(globals.selected);
      $('#update-factory-modal').modal('show');
      Util.setFormFields(factory, 'update-factory');
      Util.hideContextMenu();
    });

    $('#update-factory').submit(function(e) {
      e.preventDefault();
      globals.updateFromSocket = false;
      $('.modal-loader').show();

      var factory = Util.getFormFields('update-factory');
      factory.setNodeId($(globals.selected).attr('data-nodeid'));
      factory.setId($(globals.selected).attr('data-id'));
      factory.parentId = 0;

      if (Validator.validate(this)) {
        HttpUtil.updateFactory(url, factory, function(data) {
          FactoryTree.updateFactory(data, function() {
            globals.selected = undefined;
            $('.modal-loader').hide();
            $('#update-factory-modal').modal('hide');
          });
        });
      } else {
        $('.modal-loader').hide();
      }
    });

    $('#delete-factory').click(function(e) {
      globals.updateFromSocket = false;
      var factory = new Factory();
      factory.set(globals.selected);
      Util.hideContextMenu();

      alertify.confirm('Delete Node?', function() {
        HttpUtil.removeFactory(url, factory.getId(), function() {
          FactoryTree.removeFactory(factory.getId(), function() {
            Util.resetContextMenu();
          });
        });
      });
    });

    $('#create-nodes').submit(function(e) {
      e.preventDefault();
      globals.updateFromSocket = false;

      $('.modal-loader').show();

      var numberNodes = $('#number-nodes').val();
      var targetNode = globals.selected;

      var factory = new Factory();
      factory.set(targetNode);
      var numberNodesCount = parseInt(numberNodes);

      if (Validator.validate(this)) {
        if (numberNodesCount <= Constants.CHILD_NODES_LIMIT) {
          HttpUtil.createChildNodes(url, factory.getId(), numberNodesCount, function(childNodes) {
            FactoryTree.addChildren(childNodes, factory.getId(), function() {
              Util.resetContextMenu();
              $('.modal-loader').hide();
            });
          });
        } else {
          alertify.error('You can insert a maximum of ' + Constants.CHILD_NODES_LIMIT + ' nodes');
          $('.modal-loader').hide();
        }
      } else {
        $('.modal-loader').hide();
      }
    });
  });
});
