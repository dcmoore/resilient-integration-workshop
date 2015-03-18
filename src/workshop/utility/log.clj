(ns workshop.utility.log
  (:require
    [taoensso.timbre :as timbre]
    [taoensso.timbre.appenders.rotor :refer [rotor-appender]]))


(defn info [message]
  (timbre/info message))

(defn error [message]
  (timbre/error message))

(defn fatal [message]
  (timbre/fatal message))

(defn config-logger! [{:keys [filename to-standard-out?]}]
  (let [megabytes #(* % 1048576)]
    (timbre/set-config! [:appenders :rotor] rotor-appender)
    (timbre/set-config! [:appenders :standard-out :enabled?] to-standard-out?)
    (timbre/set-config! [:shared-appender-config :rotor] {:path (str "log/" filename ".log") :max-size (megabytes 10) :backlog 5})))
