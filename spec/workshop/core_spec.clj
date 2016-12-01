(ns workshop.core-spec
  (:use
    [speclj.core]
    [ring.mock.request]
    [workshop.core]))

(describe "base-handler"
  (it "returns a 400 in the case of nobody being registered"
    (should=
      400
      (:status (base-handler (request :get "/v1/bro-stats-dawg"))))))
