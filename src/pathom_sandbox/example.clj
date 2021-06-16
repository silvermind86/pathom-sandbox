(ns pathom-sandbox.example
  (:require [com.wsscode.pathom.connect :as connect]
            [com.wsscode.pathom.core :as pathom]))

(connect/defresolver my-resolver [inputs]
  {::connect/input #{}
   ::connect/output [:my/output]}
  (let [some-data (+ 1 2)]
    {:my/output some-data}))

(connect/defresolver my-resolver2 [{output :my/output}]
  {::connect/output [:my/output2]}
  (let [some-data (some-> output inc)]
    (when some-data
      {:my/output2 some-data})))

(connect/defresolver address [_]
  {:person/address "Rua das Flores, 118"})

(def people
  {1 {:person/name "Antunes"
      :person/age 38
      :person/email "ant@gmail.com"}
   2 {:person/name "Lisandra"
      :person/age 0.8}
   3 {:person/name "Mathusalen"
      :person/age 9000}
   4 {:person/name "LolCat"}})

(connect/defresolver personal-data [{:keys [person/id]}]
  {::connect/output [:person/name :person/age]}
  (people id))

(def resolvers [my-resolver my-resolver2 address personal-data])

(def parser
  (pathom/parser
   {::pathom/env {::pathom/reader [pathom/map-reader
                                   connect/reader2
                                   connect/open-ident-reader]}
    ::pathom/plugins [(connect/connect-plugin {::connect/register resolvers})
                      pathom/trace-plugin]}))

(def entity (atom {:person/id 4}))

@entity

(parser {::pathom/entity entity} [:person/name])
(parser {} [{[:person/id 4] [:person/name :person/age]}])
