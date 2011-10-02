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

(ns htmlexpr.test.helpers
  (:use [htmlexpr.helpers])
  (:use [clojure.test]))

(deftest escape-strings
  (is (= (esc "\"") "&quot;"))
  (is (= (esc "<") "&lt;"))
  (is (= (esc ">") "&gt;"))
  (is (= (esc "&") "&amp;"))
  (is (= (esc "string") "string")))

