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

(ns htmlexpr.test.core
  (:use [htmlexpr.core])
  (:use [clojure.test]))

(deftest html-elements
  (testing "container-elements"
    (is (= (html) "<html></html>"))
    (is (= (body) "<body></body>"))
    (is (= (div) "<div></div>"))
    (is (= (div "foobar") "<div>foobar</div>")))
  (testing "empty-elements"
    (is (= (html-meta) "<meta />"))
    (is (= (img) "<img />"))
    (is (= (br) "<br />")))
    (is (= (img "foobar") "<img />"))
  (testing "attributes"
    (is (= (p {:id "foo" :class "bar"} "baz")
           "<p class=\"bar\" id=\"foo\">baz</p>"))
    (is (= (img {:id "foo" :class "bar" :src "somewhere"})
           "<img class=\"bar\" src=\"somewhere\" id=\"foo\" />"))))

(deftest contains-sequence
  (testing "list"
    (is (= (p ["foo", "bar", "baz"]) "<p>foobarbaz</p>")))
  (testing "loop"
    (is (= (p (for [n (range 3)] (span (str n))))
           "<p><span>0</span><span>1</span><span>2</span></p>"))))

