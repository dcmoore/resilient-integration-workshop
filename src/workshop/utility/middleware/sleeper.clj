(ns workshop.utility.middleware.sleeper)


(defn semi-random-sleep-time []
  (case (rand-int 20)
    0 (rand-int 60000)
    1 (rand-int 3000)
    0))

(defn random-sleep [handler]
  (fn [request]
    (Thread/sleep (semi-random-sleep-time))
    (handler request)))
