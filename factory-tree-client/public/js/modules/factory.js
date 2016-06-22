var Factory = function(factoryName, lowerBound, upperBound) {
  this.id = undefined;
  this.factoryName = factoryName || '';
  this.lowerBound = lowerBound || 0;
  this.upperBound = upperBound || 0;
  this.numChildNodes = undefined;
  this.nodeId = undefined;
  this.parentId = 0;

  this.constructTag = function() {
    var htmlString = $('<div class="bounds"></div>');
    htmlString.append('<span class="lower-bound"><i class="icon-down-dir"></i>')
      .append(this.lowerBound).append("</span>");
    htmlString.append('<span class="upper-bound"><i class="icon-up-dir"></i>')
      .append(this.upperBound).append("</span>");
    return htmlString;
  };
};

Factory.prototype.constructor = Factory;

Factory.prototype.set = function(target) {
  target = $(target);
  this.nodeId = target.attr('data-nodeid');
  this.id = target.attr('data-id');
  this.factoryName = target.find('span.text').text();
  this.lowerBound = target.attr('data-lowerbound') || this.lowerBound;
  this.upperBound = target.attr('data-upperbound') || this.upperBound;
};

Factory.prototype.setNumChildNodes = function(numChildNodes) {
  this.numChildNodes = numChildNodes;
};

Factory.prototype.setId = function(id) {
  this.id = id;
};

Factory.prototype.getId = function() {
  return this.id;
};

Factory.prototype.setNodeId = function(nodeId) {
  this.nodeId = nodeId;
};

Factory.prototype.getNodeId = function() {
  return this.nodeId;
};

Factory.prototype.setParentId = function(parentId) {
  this.parentId = parentId;
};

Factory.prototype.getFactoryName = function(numChildNodes) {
  return this.factoryName;
};

Factory.prototype.getLowerBound = function() {
  return this.lowerBound;
};

Factory.prototype.getUpperBound = function() {
  return this.upperBound;
};

Factory.prototype.json = function() {
  return {
    text: this.factoryName,
    factoryName: this.factoryName,
    lowerBound: this.lowerBound,
    upperBound: this.upperBound,
    nodeId: this.nodeId,
    id: this.id,
    numChildNodes: this.numChildNodes,
    parentId: this.parentId
  };
};

Factory.prototype.createChildNodes = function(numNodesToCreate) {
  var childNodes = [];
  for (var i = 0; i < numNodesToCreate; i++) {
    var node = new Node(this.lowerBound, this.upperBound);
    childNodes.push(node.json());
  }
  return childNodes;
};
