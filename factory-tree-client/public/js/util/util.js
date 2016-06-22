var Util = (function() {
  var setFields = function(factory, formName) {
    var form = '#' + formName;
    $(form + ' #factory-name').val(factory.getFactoryName());
    $(form + ' #lower-bound').val(factory.getLowerBound());
    $(form + ' #upper-bound').val(factory.getUpperBound());
  };

  var getFields = function(formName) {
    var form = '#' + formName;
    var factoryName = $(form + ' #factory-name').val();
    var lowerBound = $(form + ' #lower-bound').val();
    var upperBound = $(form + ' #upper-bound').val();

    var factory = new Factory(factoryName, lowerBound, upperBound);
    return factory;
  };

  var resetFields = function(formName) {
    var form = '#' + formName;
    $(form + ' #factory-name').val('');
    $(form + ' #lower-bound').val('');
    $(form + ' #upper-bound').val('');
  };

  var setMenu = function(target) {
    $(Constants.CONTEXT_MODAL).modal('show');
    globals.selected = target;
  };

  var hideMenu = function() {
    $(Constants.CONTEXT_MODAL).modal('hide');
  };

  var resetMenu = function() {
    hideMenu();
    $('#number-nodes').val('');
    globals.selected = undefined;
  };

  return {
    setFormFields: function(factory, formName) {
      setFields(factory, formName);
    },
    getFormFields: function(formName) {
      return getFields(formName);
    },
    setContextMenu: setMenu,
    hideContextMenu: hideMenu,
    resetContextMenu: resetMenu,
    resetFormFields: resetFields
  }
})();
