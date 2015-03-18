(ns workshop.main.development
  (:gen-class)
  (:require
    [workshop.core :refer [base-handler]]
    [workshop.utility.environment :refer [-env]]
    [workshop.utility.log :refer [config-logger!]]
    [ring.adapter.jetty :refer [run-jetty]]
    [ring.middleware.lint :refer [wrap-lint]]
    [ring.middleware.reload :refer [wrap-reload]]))


(def development-handler
  (->
    base-handler
    wrap-lint
    wrap-reload))

(defn -main [& args]
  (config-logger! {:to-standard-out? true :filename "development"})
  (reset! -env :dev)

  (let [port (Integer/parseInt (or (first args) "4000"))]
    (run-jetty development-handler {:port port})))
