# jsui-cljs

![splash](https://github.com/downloads/cassiel/jsui-cljs/jsui-splash.png)

JSUI graphics in ClojureScript.

## Introduction

This is an example of some MaxMSP JSUI graphics scripts where the
Javascript is generated using [ClojureScript][cljs].

## Usage

To build the Javascript example, install [Leiningen][lein] and the
[cljsbuild plugin][lein-cljsbuild]. Then:

```bash
$ lein deps
$ lein cljsbuild once dev
  (or)
$ lein cljsbuild once prod
```

There are two build targets: `dev` runs the [Google Closure][closure]
compiler backend in minimal optimisation mode, whereas `prod` turns on
all optimisation for "production" output. The `dev` mode compiles much faster,
and is also helpful for debugging, whenever the output of `prod` can
potentially throw up problems (most likely due to *munging*, discussed
below) which are easier to solve by having a non-`prod` script to
compare with. (In any case, it's an educational exercise to examine the
output of compilation in both modes.)

The output of `dev`, in this project, is `_main-dev.js`; the `prod`
target writes `_main.js`. Once one of these is built, you can open
`jsui-cljs.maxproj` in Max 6 to see the result. (The code will probably
run in Max 5, but you'll need to fix up the search path and load the
patcher file manually, and Max 5's Javascript engine is much slower than
that in Max 6.)

For general development, consider running

```bash
$ lein cljsbuild auto dev
```

in the background: this automatically recompiles whenever any (Clojure)
sources are changed. If the source sets `autowatch` to `1` (as in our
example), then changes to ClojureScript sources will automatically be
recompiled and reloaded into Max.

## Documentation

The example is documented [here][docs] (using [Marginalia][marginalia]).

## Notes

Optimisation in Google Closure merits some discussion. One of the
optimisations *munges* variable names, to make them shorter and more
efficient. We need to prevent munging of names we're using implicitly
from the Max world (`mgraphics`, `autowatch` etc.), and also make sure
that we can plant names for JSUI to use which themselves won't get
munged. There's more detail [here][luke], but the upshot is that we need
to provide a mock externals file declaring stubs which shadow the things
we need, or export, at the Javascript level. We have one in
[src-cljs/externs.js][externs], although it's currently far from complete.

## License

Copyright © 2012 Nick Rothwell, nick@cassiel.eu

Distributed under the Eclipse Public License, the same as Clojure.

[jsmgraphics]: http://www.cycling74.com/docs/max6/dynamic/c74_docs.html#jsmgraphics
[cljs]: https://github.com/clojure/clojurescript
[lein]: https://github.com/technomancy/leiningen
[lein-cljsbuild]: https://github.com/emezeske/lein-cljsbuild
[closure]: https://developers.google.com/closure/compiler/
[docs]: https://github.com/downloads/cassiel/jsui-cljs/uberdoc.html
[marginalia]: http://fogus.me/fun/marginalia/
[luke]: http://lukevanderhart.com/2011/09/30/using-javascript-and-clojurescript.html
[externs]: /cassiel/jsui-cljs/blob/master/src-cljs/externs.js
