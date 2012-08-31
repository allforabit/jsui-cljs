autowatch = 1;

var r = {
    f: function f() { post("f\n"); }
};

function plant() {
    this.f = r.f;
}

plant();
f();
post(this);
