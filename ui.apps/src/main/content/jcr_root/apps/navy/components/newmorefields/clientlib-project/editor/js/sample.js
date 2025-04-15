(function(document, $, Coral) {
    "use strict";

    $(document).on("foundation-contentloaded", function(e) {
        $(".cmp-list__editor coral-select.cq-dialog-dropdown-showhide", e.target).each(function(i, element) {
            var target = $(element).data("cq-dialog-dropdown-showhide");

            if (target) {
                Coral.commons.ready(element, function(component) {
                    showHide(component, target); // Initial run
                    component.on("change", function() {
                        showHide(component, target); // Run on change
                    });
                });
            }
        });
    });

    function showHide(component, target) {
        var value = component.value;
        $(target).each(function() {
            var $targetElement = $(this);
            if ($targetElement.data("showhidetargetvalue") === value) {
                $targetElement.removeClass("hide");
            } else {
                $targetElement.addClass("hide");
            }
        });
    }
})(document, Granite.$, Coral);