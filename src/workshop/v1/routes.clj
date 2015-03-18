(ns workshop.v1.routes
  (:require
    [compojure.core :refer [defroutes context POST GET]]
    [workshop.controller :as controller]))


(defroutes v1-handler
  (POST "/excavate" request (controller/excavate request))
  (POST "/excavate/" request (controller/excavate request))
  (POST "/register" request (controller/register request))
  (POST "/register/" request (controller/register request))
  (POST "/store" request (controller/store request))
  (POST "/store" request (controller/store request))
  (POST "/store/" request (controller/store request))
  (GET "/totals" request (controller/totals request))
  (GET "/totals/" request (controller/totals request)))
