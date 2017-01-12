(ns workshop.controller-spec
  (:use
    [speclj.core]
    [workshop.controller]))

(describe "dirt penalty situation"
  (it "should not penalize someone for storing gold"
    (should=
      2
      (penalize-dirt 2)))

  (it "should penalize someone for storing dirt"
    (should=
      -100
      (penalize-dirt 0)))

  (it "should not penalize someone for storing negative units"
    (should=
      -1
      (penalize-dirt -1))))
