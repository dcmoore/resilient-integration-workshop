(defproject workshop "0.1.0"
  :description "Crappy service used in resilient integrations workshop"
  :url "https://github.com/dcmoore/workshop"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [chee "2.0.0"]
                 [clj-stacktrace "0.2.8"]
                 [com.taoensso/timbre "3.2.1"]
                 [compojure "1.1.5"]
                 [easy-bake-service "0.0.7"]
                 [environ "1.0.0"]
                 [org.clojure/data.csv "0.1.2"]
                 [org.clojure/tools.namespace "0.2.7"]
                 [ring/ring-devel "1.3.2"]
                 [ring/ring-jetty-adapter "1.3.2"]
                 [stencil "0.3.4"]]
  :plugins [[lein-environ "1.0.0"]]
  :aliases {"compile-min" ["do" ["clean"] ["compile"] ["uberjar"]]}
  :profiles {:test {:dependencies [[ring/ring-mock "0.3.0"]
                                   [speclj "3.1.0"]]
                    :plugins [[speclj "3.1.0"]]
                    :resource-paths ["spec/resources" "resources"]
                    :test-paths ["spec"]
                    :env {:env :test}}

             :development {:aot [workshop.main.development]
                           :main workshop.main.development
                           :env {:env :development}}
             :uberjar {:aot [workshop.main.development]
                       :main workshop.main.development
                       :env {:env :development}}}

  :min-lein-version "2.0.0")
