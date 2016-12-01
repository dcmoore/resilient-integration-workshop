(ns workshop.core
  (:require
    [workshop.utility.middleware.log :refer [log-error log-request-and-response]]
    [workshop.utility.middleware.failer :refer [random-failure]]
    [workshop.utility.middleware.passthrough-middleware :refer [right-most-matching-handler]]
    [workshop.utility.middleware.sleeper :refer [random-sleep]]
    [workshop.v1.routes :refer [v1-handler admin-routes]]
    [compojure.core :refer [defroutes routes context GET]]
    [compojure.route :refer [not-found]]
    [easy-bake-service.middleware.normalize :refer [wrap-normalize]]))


(defroutes application-routes
  (context "/v1" [] v1-handler)
  (not-found {:headers {"Content-Type" "text/json; charset=utf-8"}
              :body "{\"error\": \"Bro, why you trying to hit an endpoint that doesn't exist?\"}"}))

(defn wrap-origin [handler]
  (fn [request]
    (let [response (handler request)]
      (assoc response
        :headers
        (merge (:headers response) {"Access-Control-Allow-Origin" "*"
                                    "Access-Control-Allow-Headers" "Origin, X-Requested-With, Content-Type, Accept"})))))

(def base-handler
  (-> application-routes
      log-error
      random-sleep
      random-failure
      (right-most-matching-handler admin-routes)
      wrap-origin
      wrap-normalize
      log-request-and-response))
