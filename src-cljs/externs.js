/*  This is an essential, but incomplete, spec. of environmental variables in the
    Max JSUI world that we don't want to have munged. (Contributions welcome.) */

var mgraphics = {
    init: function() { },
    move_to: function() { },
    line_to: function() { },
    rectangle: function() { },
    stroke: function() { },
    fill: function() { },
    redraw: function() { },
    relative_coords: 0,
    set_source_rgb: function() { },
    set_source_rgba: function() { },
    set_line_width: function() { },
    set_font_size: function() { },
    text_path: function() { },
    autofill: 0
};

/* This one makes no sense to me: Task() actually returns something with a .interval. */
var Task = function(f) { f.interval };

this.autowatch = 0;
this.paint = function() { };
this.box = { };
this.jsarguments = { };
this.mgraphics = { };
this.post = function() { };
this.patcher = { };
