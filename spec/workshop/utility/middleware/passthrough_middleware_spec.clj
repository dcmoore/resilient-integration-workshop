(ns workshop.utility.middleware.passthrough-middleware-spec
  (:use
    [speclj.core]
    [workshop.utility.middleware.passthrough-middleware]))

(def request nil)
(def response-a "suh-dude")
(def response-b "suh-bro")

(describe "passthrough-middleware"
  (it "returns response from the rightmost handler assuming it returns a response"
    (should=
     response-a
      ((right-most-matching-handler (fn [_] response-b) (fn [_] response-a)) request)))

  (it "returns left handler if right returns nil"
    (should=
      response-b
      ((right-most-matching-handler (fn [_] response-b) (fn [_] nil)) request))))

