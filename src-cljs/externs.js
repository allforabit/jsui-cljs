/*  This is an essential, but incomplete, spec. of environmental variables in the
    Max JSUI world that we don't want to have munged. (Contributions welcome.) */

/* For referring to mgraphics via js/mgraphics (Nick sez don't do it!): */

var mgraphics = {
    init: function() { },
    move_to: function() { },
    line_to: function() { },
    stroke: function() { },
    relative_coords: 0,
    autofill: 0
};

this.autowatch = 0;
this.paint = function() { };
this.box = { };
this.jsarguments = { };
/* For referring to mgraphics via (.-mgraphics me): */
this.mgraphics = { };
this.post = function() { };
