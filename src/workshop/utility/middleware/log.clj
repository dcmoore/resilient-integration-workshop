(ns workshop.utility.middleware.log
  (:require
    [workshop.utility.log :as log]
    [chee.pretty-map :refer [pretty-map]]
    [clj-stacktrace.core :refer [parse-exception]]
    [clojure.walk :as w]))


(def request-count (atom 0))
(def line-separator (System/getProperty "line.separator"))

(defn log-request-and-response [handler]
  (fn [request]
    (let [request-id (swap! request-count inc)]
      (log/info (str
        "[REQUEST] " request-id " ========================================================================================"
        line-separator
        (pretty-map request)
        line-separator))
      (let [response (handler request)]
        (log/info (str
          "[RESPONSE] " request-id " ========================================================================================"
          line-separator
          (pretty-map (assoc response :body (str (count (str (:body response))) " chars of body")))
          line-separator))
        response))))

(defn log-error [handler]
  (fn [request]
    (try
      (handler request)
      (catch Throwable e
        (log/fatal (str "[CRITICAL] " (parse-exception e)))
        {:status 500
         :headers {}
         :body "something went wrong"}))))
