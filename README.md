# jsui-cljs

![splash](https://github.com/downloads/cassiel/jsui-cljs/jsui-splash-2.png)

JSUI graphics in ClojureScript.

## Introduction

This is a small collection of examples of some simple MaxMSP JSUI
graphics scripting where the Javascript is generated using
[ClojureScript][cljs]:

- [`example.cross`][cross]: a pretty straight port of the one
  in the [JSUI documentation][jsmgraphics]
- ([`example.surface-work`][surface]: work-in-progress from Darwin
  Grosse's November Patch-a-Day)
- [`example.tickle`][tickle]: a more sophisticated annotation example
  for examining the objects in Max patchers
- [`example.puffdraw`][puffdraw] Darwin's "Puffdraw" example

[cross]: https://github.com/downloads/cassiel/jsui-cljs/uberdoc.html#example.cross
[surface]: https://github.com/downloads/cassiel/jsui-cljs/uberdoc.html#example.surface-work
[tickle]: https://github.com/downloads/cassiel/jsui-cljs/uberdoc.html#example.tickle
[puffdraw]: https://github.com/downloads/cassiel/jsui-cljs/uberdoc.html#example.puffdraw

## Usage

To build the example, install [Leiningen][lein] and the
[cljsbuild plugin][lein-cljsbuild]. Then:

```bash
$ lein deps
$ lein cljsbuild once dev
  (or)
$ lein cljsbuild once prod
```

There are two build targets: `dev` runs the [Google Closure][closure]
compiler backend in minimal optimisation mode, whereas `prod` turns on
further optimisation for "production" output. (This is what Google
Closure refers to as "simple" optimisation; the full-blown "advanced"
mode seems to be throwing up code generation errors in the ClojureScript
libraries.)

The `dev` mode compiles faster, but `prod` is fast enough to be usable
since we've taken out the "advanced" optimisation mode (which is slow).

The output of `dev`, in this [project][proj], is `_main-dev.js`; the
`prod` target writes `_main.js`. Once one of these is built, you can
open any of the patchers in `jsui-cljs.maxproj` in Max 6 to see the
result. (Any non-`msgraphics` code would probably run in Max 5, but
you'd need to fix up the search path and load the patcher files
manually, and Max 5's Javascript engine is much slower than that in Max
6.)

All the patcher files load the same Javascript file: the code executed
is determined by the JSUI argument. Look at [`core`][core] to see the
selection on the ClojureScript side.

[core]: http://cloud.github.com/downloads/cassiel/jsui-cljs/uberdoc.html#core

For general development, consider running

```bash
$ lein cljsbuild auto dev
```

(or `prod`) in the background: this automatically recompiles whenever any
(ClojureScript) sources are changed. If the source sets `autowatch` to
`1` (as in our examples), then changes to sources will automatically be
recompiled and reloaded into Max.

## Documentation

The example code is documented [here][docs] (using
[Marginalia][marginalia]). To build the documentation:

```bash
$ lein marg src-cljs/
```

## Notes

"Advanced" optimisation in Google Closure merits some discussion. One of
the optimisations *munges* variable names, to make them shorter and more
efficient. We need to prevent munging of names we're using implicitly
from the Max world (`mgraphics`, `autowatch` etc.), and also make sure
that we can plant names for JSUI to use which themselves won't get
munged. There's more detail [here][luke], but the upshot is that we need
to provide a mock externals file declaring stubs which shadow the things
we need, or export, at the Javascript level. We have one in
[`src-cljs/externs.js`][externs], although it's currently far from
complete.

(For "simple" optimisation, there's no top-level variable renaming.)

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
[proj]: /cassiel/jsui-cljs/blob/master/project.clj
