"use strict";

use(function () {
    var multifield = resource.getChild("multifield").listChildren();

    return {
        multifield: multifield,
    };
});