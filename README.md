htmlexpr
=====

A small library for writing XHTML with Clojure.

Install
-----

Add the following dependency to your `project.clj` file:

  [htmlexpr "0.1.0"]

Usage
-----

To write a (X)HTML, you can write something like this.

    user=> (use 'htmlexpr.core)
    nil
    user=> (html
           (body
             (div
               (p "Hello, World!!"))))
    "<html><body><div><p>Hello, World!!</p></div></body></html>"

Attributes are specified with a map.

    user=> (img {:id "img-test" :src "(somewhere)"} )
    "<img src=\"(somewhere)\" id=\"img-test\" />"

You can use loops or something.

    user=> (p (for [n (range 3)] (span "a")))
    "<p><span>a</span><span>a</span><span>a</span></p>""

`meta` and `map` are declared with prefix `html-` in order to conflict with
`clojure.core/meta` and `clojure.core/map`.

    user=> (html-meta)
    "<meta />"
    user=> (html-map)
    "<map></map>"

License
-----

Copyright (c) 2011 Tetsuo Fujii and licensed under the Apache 2.0 license.

For more details, please check the LICENSE file.
