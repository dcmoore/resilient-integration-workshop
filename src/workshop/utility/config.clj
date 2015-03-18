(ns workshop.utility.config
  (:require [clojure.java.io :as io]))


(def config-home (System/getenv "CONFIG_HOME"))

(defn reader [file-name]
  (read-string (slurp
    (if (nil? config-home)
      (io/resource (str "config/" file-name ".clj"))
      (str config-home "/" file-name ".clj")))))
