(ns workshop.utility.middleware.passthrough-middleware)

(defn right-most-matching-handler [handler potential-passthrough-routes]
  (fn [request]
    (let [response (potential-passthrough-routes request)]
      (if (nil? response)
        (handler request)
        response))))
