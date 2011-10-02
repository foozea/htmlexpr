;;
;;  Copyright 2011 Tetsuo Fujii.
;;
;;  Licensed under the Apache License, Version 2.0 (the "License"); you may
;;  not use this file except in compliance with the License. You may obtain
;;  a copy of the License at
;;
;;      http://www.apache.org/licenses/LICENSE-2.0
;;
;;  Unless required by applicable law or agreed to in writing, software
;;  distributed under the License is distributed on an "AS IS" BASIS,
;;  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;;  See the License for the specific language governing permissions and
;;  limitations under the License.
;;

(ns htmlexpr.core
  (:use [htmlexpr.helpers :only (esc)]))

(def
 ^{:private true
   :doc "Prefix to prevent conflicts with others."}
  prefix "html-")

(def
 ^{:private true
   :doc "List of container elements."}
  container-elements
  ["html", "head", "title", "style", "script", "noscript", "body",
   "h1", "p", "div", "ul", "ol", "li", "dl", "dt", "dd", "abbr",
   "acronym", "address", "cite", "code", "dfn", "em", "kbd", "samp",
   "strong", "var", "ins", "del", "pre", "blockquote", "q", "object",
   "param", "a", "map", "area", "table", "caption", "tr", "th", "td",
   "thead", "tbody", "tfoot", "col", "bdo", "span", "form", "textarea",
   "select", "option", "label", "fieldset", "legend", "b", "big", "i",
   "small", "sub", "sup", "tt"])

(def
 ^{:private true
   :doc "List of empty containers."}
  empty-elements
  ["base", "meta", "link", "br", "img", "colgroup", "hr", "input",
   "optgroup", "button"])

(defn- expand-attributes
  "Expands a map to attributes. If a value of map includes the characters
  [\", <, >, &], escape these. e.g) {:id \"id-A\"} => id=\"id-A\""
  [attrs]
  (apply str (map #(str " " (name %1) "=\"" (esc %2) "\"")
                  (keys attrs) (vals attrs))))

(defn- open-container-tag
  "Creates a left tag of 'elem'."
  [elem attrs]
  (let [is-map# (and attrs (map? attrs))]
    (str "<" elem
         (when is-map# (expand-attributes attrs))
         ">")))

(defn- open-empty-tag
  "Creates a empty tag of 'elem'."
  [elem attrs]
  (let [is-map# (and attrs (map? attrs))]
    (str "<" elem
         (when is-map# (expand-attributes attrs))
         " />")))

(defn- close-tag
  "Closes a tag of 'elem'."
  [elem]
  (str "</" elem ">"))

(defn- expand-expression
  "Markups 'content' as 'elem'. If 'elem' is empty-element, content will be
  ignored."
  [elem attrs content]
  (cond (some #(= elem %) empty-elements) (open-empty-tag elem attrs)
        (= 1 1) (str (open-container-tag elem attrs) content (close-tag elem))))

(defn- safe-name
  "Adds the prefix to the string, if the same name is already declared."
  [x]
  (let [elem# (symbol x)]
    (if (resolve elem#) (symbol (str prefix x)) elem#)))

(defn- apply-content
  "If the argument is Lazy-seq or else, realizes it to a string."
  [x]
  (apply str x))

(defmacro def-expander
  "Defines a function that markups a content as 'x'."
  [x]
  (let [elem# (safe-name x)]
    `(defn ~elem#
       ([attrs# content#]
          (expand-expression ~x attrs# (apply-content content#)))
       ([arg#]
          (cond (map? arg#) (expand-expression ~x arg# nil)
                (= 1 1) (expand-expression ~x nil (apply-content arg#))))
       ([] (expand-expression ~x nil nil)))))

(defmacro def-all-expanders
  "Defines all the functions to markup contents."
  []
  `(do ~@(map (fn [n] `(def-expander ~n)) container-elements)
       ~@(map (fn [n] `(def-expander ~n)) empty-elements)))

(def-all-expanders)
