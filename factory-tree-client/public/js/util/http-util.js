var HttpUtil = (function() {

  var getURL = function(callback) {
    $.get('/serverHost', function(host) {
      callback(host);
    });
  }

  var getFactories = function(url, callback) {
    $.getJSON(url, function(treeData) {
      callback(treeData);
    });
  };

  var createFactory = function(url, factory, callback) {
    $.ajax({
      url: url,
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify(factory.json()),
      dataType: 'json',
      success: function(data) {
        callback(data);
      },
      error: function(xhr, status, error) {
        alertify.error(JSON.parse(xhr.responseText).message);
      }
    });
  };

  var updateFactory = function(url, factory, callback) {
    var url = url + factory.getId()
    $.ajax({
      url: url,
      type: 'PUT',
      contentType: 'application/json',
      data: JSON.stringify(factory.json()),
      dataType: 'json',
      success: function(data) {
        callback(data);
      },
      error: function(xhr, status, error) {
        alertify.error(JSON.parse(xhr.responseText).message);
      }
    });
  };

  var removeFactory = function(url, id, callback) {
    var url = url + id;
    $.ajax({
      url: url,
      contentType: 'application/json',
      type: 'DELETE',
      success: function(result) {
        callback();
      },
      error: function(xhr, status, error) {
        alertify.error('Unable to delete factory');
      }
    });
  };

  var createChildNodes = function(url, id, numChildNodes, callback) {
    var url = url + id + '/childNodes?numChildNodes=' + numChildNodes;
    $.ajax({
      url: url,
      type: 'POST',
      contentType: 'application/json',
      dataType: 'json',
      success: function(data) {
        callback(data.nodes);
      },
      error: function(xhr, status, error) {
        alertify.error(JSON.parse(xhr.responseText).message);
      }
    });
  };

  return {
    getURL: function(func) {
      getURL(func);
    },
    getFactories: function(url, func) {
      getFactories(url, func);
    },
    createFactory: function(url, factory, func) {
      createFactory(url, factory, func);
    },
    updateFactory: function(url, factory, func) {
      updateFactory(url, factory, func);
    },
    removeFactory: function(url, id, func) {
      removeFactory(url, id, func);
    },
    createChildNodes: function(url, id, numChildNodes, func) {
      createChildNodes(url, id, numChildNodes, func);
    }
  };
})();
