(ns workshop.controller
  (:require
    [clojure.data.csv :as csv]
    [clojure.java.io :as io]))


(defn- generate-uuid []
  (str (java.util.UUID/randomUUID)))

(defn- json-response [body]
  {:status 200 :body body :headers {"Content-Type" "application/json"}})

(defn- json-bad-response [body]
  {:status 400 :body body :headers {"Content-Type" "application/json"}})

(def users (atom ""))
(def excavations (atom ""))
(def stored-buckets (atom ""))

(defn write-to-csv [file-name values]
  (case file-name
    :users (swap! users str (str (apply str (interpose "," values)) "\n"))
    :excavations (swap! excavations str (str (apply str (interpose "," values)) "\n"))
    :stored-buckets (swap! stored-buckets str (str (apply str (interpose "," values)) "\n"))))

(defn excavate [request]
  (let [bucket-id (generate-uuid)
        gold-units (rand-int 5)]
    (write-to-csv :excavations [bucket-id gold-units])
    (if (= 0 gold-units)
      (json-response {:bucket-id bucket-id
                      :dirt {:units (rand-int 5)}})
      (json-response {:bucket-id bucket-id
                      :gold {:units gold-units}}))))

(defn- store-gold [user-id bucket-id]
  (if-let [user-id (ffirst (filter #(= user-id (first %)) (csv/read-csv @users)))]
    (if-let [bucket-id (ffirst (filter #(= bucket-id (first %)) (csv/read-csv @excavations)))]
      (do
        (write-to-csv :stored-buckets [user-id bucket-id])
        {:status 200 :body "true"})
      (json-bad-response {:error "cmon bro, that's not a valid bucket"}))
    (json-bad-response {:error "cmon bro, you need to register first"})))

(defn store [request]
  (if-let [user-id (:user-id (:query-params request))]
    (if-let [bucket-id (:bucket-id (:query-params request))]
      (store-gold user-id bucket-id)
      (json-bad-response {:error "Must have 'bucketId' in query params"}))
    (json-bad-response {:error "Must have 'userId' in query params"})))

(defn- get-user-name [user-id]
  (last (first (filter #(= user-id (first %)) (csv/read-csv @users)))))

(defn- get-gold-total [registered-buckets]
  (apply +
    (for [bucket registered-buckets]
      (let [bucket-id (last bucket)]
        (Integer. (or (last (first (filter #(= bucket-id (first %)) (csv/read-csv @excavations)))) "0"))))))

(defn- read-totals [user-id]
  (if-let [registered-buckets (set (filter #(= user-id (first %)) (csv/read-csv @stored-buckets)))]
    (json-response {:user-name (get-user-name user-id)
                    :gold-total (get-gold-total registered-buckets)})
    (json-bad-response {:error "You have no stored buckets"})))

(defn totals [request]
  (if-let [user-id (:user-id (:query-params request))]
    (read-totals user-id)
    (json-bad-response {:error "Must have 'userId' in query params"})))

(defn register [request]
  (if-let [user-name (:user-name (:query-params request))]
    (let [uuid (generate-uuid)]
      (write-to-csv :users [uuid user-name])
      (json-response {:user uuid
                      :name user-name}))
    (json-bad-response {:error "Must have 'userName' in query params"})))
