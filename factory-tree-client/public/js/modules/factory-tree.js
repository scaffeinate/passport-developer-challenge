var FactoryTree = (function() {
  var tree = undefined;

  var populateTree = function(treeData) {
    this.tree = $(Constants.TREE_ELEMENT);
    this.tree.treeview({
      data: treeData,
      showBorder: Constants.SHOW_BORDER,
      showTags: Constants.SHOW_TAGS,
      nodeIcon: Constants.NODE_ICON,
      expandIcon: Constants.EXPAND_ICON,
      collapseIcon: Constants.COLLAPSE_ICON,
      color: Constants.TEXT_COLOR
    });

    globals.treeData = treeData;
  };

  var createFactoryNode = function(factory) {
    factory = $.extend(true, {}, factory);

    this.tree.treeview('addNode', {
      node: factory,
      parent: 0
    });
  };

  var createChildNodes = function(children, parent) {
    children = $.extend(true, [], children);
    this.tree.treeview('addNodes', {
      nodes: children,
      parent: parent
    });
  };

  var removeFactoryNode = function(id) {
    this.tree.treeview('removeNode', {
      id: id
    });
  };

  var updateFactoryNode = function(factory) {
    this.tree.treeview('updateNode', {
      node: factory
    });
  }

  return {
    populate: function(treeData) {
      populateTree(treeData);
    },
    addFactory: function(factory, func) {
      createFactoryNode(factory);
      if (func) {
        func();
      }
    },
    addChildren: function(children, parent, func) {
      createChildNodes(children, parent);
      if (func) {
        func();
      }
    },
    removeFactory: function(id, func) {
      removeFactoryNode(id);
      if (func) {
        func();
      }
    },
    updateFactory: function(factory, func) {
      updateFactoryNode(factory);
      if (func) {
        func();
      }
    }
  };
})();
