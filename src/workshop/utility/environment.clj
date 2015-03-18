(ns workshop.utility.environment)


(def ^{:doc "You should set this value once on app load. Call the environment function to get the value that was set."}
  -env (atom :unconfigured))

(defn environment []
  (when (= @-env :unconfigured)
    (throw (ex-info "Environment hasn't been configured yet." {})))
  @-env)
