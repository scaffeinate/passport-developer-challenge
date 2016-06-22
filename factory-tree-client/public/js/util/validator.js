var Validator = (function() {

  var validateFactory = function(form, newRecord) {
    for (var i = 0; i < form.elements.length; i++) {
      var field = $(form.elements[i]);
      var type = field.attr('type');
      if (type == 'text' || type == 'number') {
        if (isEmpty(field.val())) {
          alertify.error(field.attr('id') + ' is blank.');
          return false;
        }
      }

      if (type == 'number') {
        if ($.isNumeric(field.val())) {
          if (parseInt(field.val()) > Constants.MAX_VALUE) {
            alertify.error(field.attr('id') + ' greater than maximum value.');
            return false;
          }
        } else {
          alertify.error(field.attr('id') + ' not a number.');
          return false;
        }
      }

      if (type == 'text') {
        if (field.val().length > Constants.MAX_LENGTH) {
          alertify.error(field.attr('id') + ' must be lesser than ' + Constants.MAX_LENGTH + ' characters.');
          return false;
        }
      }
    }

    var lowerBound = parseInt($(form).find('#lower-bound').val());
    var upperBound = parseInt($(form).find('#upper-bound').val());

    if (lowerBound >= upperBound) {
      alertify.error('lower-bound should be less than upper-bound');
      return false;
    }

    return true;
  }

  var isEmpty = function(field) {
    if (field == null || field.toString().trim() == '') {
      return true;
    }
  };

  return {
    validate: function(form, newRecord) {
      return validateFactory(form, newRecord);
    }
  };
})();
