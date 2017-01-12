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

(def users (atom []))
(def excavations (atom []))
(def stored-buckets (atom []))

(defn- write-to-csv [file-name values]
  (case file-name
    :users (swap! users conj (str (apply str (interpose "," values))))
    :excavations (swap! excavations conj (str (apply str (interpose "," values))))
    :stored-buckets (swap! stored-buckets conj (str (apply str (interpose "," values))))))

(defn read-from-csv [match-key collection]
  (csv/read-csv (or (first (filter #(= match-key (ffirst (csv/read-csv %))) collection)) "")))

(defn penalize-dirt [units]
  (if (= 0 units)
    -100
    units))

(defn excavate [request]
  (let [bucket-id (generate-uuid)
        gold-units (rand-int 5)]
    (write-to-csv :excavations [bucket-id (penalize-dirt gold-units)])
    (if (= 0 gold-units)
      (json-response {:bucket-id bucket-id
                      :dirt {:units (rand-int 5)}})
      (json-response {:bucket-id bucket-id
                      :gold {:units gold-units}}))))

(defn- store-gold [user-id bucket-id]
  (if-let [user-id (ffirst (read-from-csv user-id @users))]
    (if-let [bucket-id (ffirst (read-from-csv bucket-id @excavations))]
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
  (last (first (read-from-csv user-id @users))))

(defn- get-gold-total [bucket-ids-for-user]
  (apply +
    (for [bucket-id bucket-ids-for-user]
      (Integer. (or (last (first (read-from-csv bucket-id @excavations))) "0")))))

(defn bucket-ids-for-user [user-id]
  (set (map #(last (last (csv/read-csv (or % ""))))
            (filter #(= user-id (ffirst (csv/read-csv %))) @stored-buckets))))

(defn- read-totals [user-id]
  (if-let [bucket-ids-for-user (bucket-ids-for-user user-id)]
    (json-response {:user-name (get-user-name user-id)
                    :gold-total (get-gold-total bucket-ids-for-user)})
    (json-bad-response {:error "You have no stored buckets"})))

(defn totals [request]
  (if (nil? (get-user-name (:user-id (:query-params request))))
    (json-bad-response {:error "Must have a registered 'userId' in query params"})
    (read-totals (:user-id (:query-params request)))))

(defn register [request]
  (if-let [user-name (:user-name (:query-params request))]
    (let [uuid (generate-uuid)]
      (write-to-csv :users [uuid user-name])
      (json-response {:user uuid
                      :name user-name}))
    (json-bad-response {:error "Must have 'userName' in query params"})))

(defn bro-stats-dawg [request]
  (let [user-ids (set (map #(ffirst (csv/read-csv %)) @users))]
    (if (not (empty? user-ids))
      (json-response {:users (vec (sort-by :gold-total > (map #(do {:user-name (get-user-name %)
                                                                    :gold-total (get-gold-total (bucket-ids-for-user %))})
                                                              user-ids)))})
      (json-bad-response {:error "Nobody registered yet"}))))
