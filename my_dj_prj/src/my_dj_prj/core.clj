(ns my-dj-prj.core
  (:require
   [datajure.dsl :as dtj]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn -main []

  (dtj/set-backend "tablecloth")

  (def data {:age [31 25 18 18 25]
             :name ["a" "b" "c" "c" "d"]
             :salary [200 500 200 370 3500]})
  (dtj/dataset data)

  (dtj/print (dtj/dataset data))

  (-> data
      (dtj/dataset)
      (dtj/query [[:salary #(< 300 %)] [:age #(> 20 %)]] [])
      (dtj/print))

  (-> data
      (dtj/dataset)
      (dtj/query [[:sum :salary #(< 1000 %)]] [:age :sum :salary] [:group-by :age])
      (dtj/print))

  (-> data
      (dtj/dataset)
      (dtj/query [] [:age :sum :salary :sd :salary] [:group-by :age :sort-by :sd :salary >])
      (dtj/print))

  (-> data
      (dtj/dataset)
      (dtj/query [] [:age :name :sum :salary] [:group-by :age :name])
      (dtj/print))

  (-> data
      (dtj/dataset)
      (dtj/query [[:salary #(< 0 %)] [:age #(< 24 %)]] [])
      (dtj/print))

  (-> data
      (dtj/dataset)
      (dtj/query [[:sum :salary #(< 0 %)] [:age #(< 0 %)]] [:name :age :salary :sum :salary :sd :salary] [:group-by :name :age :sort-by :salary])
      (dtj/print)))

