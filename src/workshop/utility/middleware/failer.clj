(ns workshop.utility.middleware.failer)


(defn semi-random-failure []
  (case (rand-int 20)
    0 500
    1 502
    2 504
    200))

(defn random-failure [handler]
  (fn [request]
    (case (semi-random-failure)
      500 {:status 500 :body "whoops, stuff is broken" :headers {"Content-Type" "application/json"}}
      502 {:status 502 :body {:error "bad gateway"} :headers  {"Content-Type" "application/json"}}
      504 (do (Thread/sleep 30000) {:status 504 :body {:error "gateway timeout"} :headers  {"Content-Type" "application/json"}})
      (handler request))))
