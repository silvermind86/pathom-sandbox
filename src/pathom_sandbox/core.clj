(ns pathom-sandbox.core
  (:require [com.wsscode.pathom.connect :as connect]
            [com.wsscode.pathom.core :as pathom]))

(connect/defresolver contact-resolver
  [{:keys [data]} _]
  {:user/contact {:name (:name data)
                  :email (:email data)
                  :cell-phone (or (:cellular data)
                                       (:main-phone data))}})

(connect/defresolver address-resolver
  [{:keys [data]} _]
  {:user/address {:street (:street data)
                  :city (:city data)
                  :number (:number data)
                  :phone (:phone data)}})

(def ^:private resolvers
  [contact-resolver address-resolver])

(def parser
  (pathom/parser
   {::pathom/env {::pathom/reader [pathom/map-reader
                                   connect/reader2
                                   connect/open-ident-reader]}
    ::pathom/plugins [(connect/connect-plugin {::connect/register resolvers})
                      pathom/trace-plugin]}))

(defn -main [& args]
  (let [single-query [:user/contact {:user/address [:street :city]}]]
    (parser {:data {:name "Mônica"
                    :email "mo@gmail.com"
                    :main-phone "32221234"
                    :street "mmmmm mmmm"
                    :city "letters"
                    :number 400
                    :phone "321456789"}}
            single-query)))
