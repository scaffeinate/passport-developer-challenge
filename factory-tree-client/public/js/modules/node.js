var Node = function(min, max) {
  this.min = min;
  this.max = max;

  this.getRandomInt = function() {
    var random = Math.floor(Math.random() * (this.max - this.min + 1) + this.min);
    return random;
  };
};

Node.prototype.json = function() {
  return {
    text: this.getRandomInt(),
    icon: Constants.NODE_ICON
  };
};
