var ContextMenuUtil = (function() {
  this.contextMenu = $(Constants.MENU_ELEMENT);
  var _this = this;

  $('body').on('contextmenu', '.list-group > li', function(e) {
    removeSelectedStyle(globals.selected);
    addSelectedStyle(this);
    Util.setContextMenu(e.currentTarget);
    return false;
  });

  $('body').click(function() {
    removeSelectedStyle(globals.selected);
  });

})();

var addSelectedStyle = function(element) {
  $(element).css({
    'background': Constants.SELECTED_LIST_BG_COLOR,
    'color': Constants.SELECTED_TEXT_COLOR
  });
};

var removeSelectedStyle = function(element) {
  $(element).css({
    'background': Constants.DEFAULT_LIST_BG_COLOR,
    'color': Constants.TEXT_COLOR
  });
}

var isMobileWidth = function() {
  return $(window).width() < 767;
};
