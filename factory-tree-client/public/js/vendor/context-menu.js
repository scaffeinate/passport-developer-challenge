(function($, window) {

  $.fn.contextMenu = function(settings) {

    return this.each(function() {

      // Open context menu
      $(this).on("contextmenu", function(e) {
        // return native menu if pressing control
        if (e.ctrlKey) return;

        //open menu
        var $menu = $(settings.menuSelector)
          .data("invokedOn", $(e.target))
          .show()
          .off('click')
          .on('click', 'a', function(e) {
            $menu.hide();

            var $invokedOn = $menu.data("invokedOn");
            var $selectedMenu = $(e.target);

            settings.menuSelected.call(this, $invokedOn, $selectedMenu);
          });

        return false;
      });
    });
  };
})(jQuery, window);
