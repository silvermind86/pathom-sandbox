(defproject pathom-sandbox "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.wsscode/pathom "2.3.1"]]
  :profiles {:uberjar {:aot :all}             
             :lint {:dependencies [[clj-kondo "2020.07.29"]]}
             :kaocha {:dependencies [[org.clojure/tools.cli "1.0.206"]
                                     [lambdaisland/kaocha "1.0.641"]
                                     [lambdaisland/kaocha-cloverage "1.0.56"]]}}

  :aliases {"kaocha" ["with-profile" "+kaocha" "run"
                      "-m" "kaocha.runner"
                      "--plugin" "cloverage"]
            "lint" ["with-profile" "+lint" "run"
                    "-m" "clj-kondo.main"
                    "--lint" "src:test"]
            "chlorine" ["with-profile" "+dev" "repl"
                        ":start" ":port" "5555"]}
  :repl-options {:init-ns pathom-sandbox.core})
