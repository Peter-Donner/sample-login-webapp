(ns sample-login-webapp.routes
  (:require [cemerick.friend :as friend]
            [clojure.java.io :as io]
            [compojure.core :refer [GET routes]]
            [compojure.route :refer [resources not-found]]
            [ring.util.response :refer [response redirect]]))

(def authorized-routes
  (routes
   (friend/wrap-authorize
    (GET "/authenticated" _
         (-> "public/index.html"
             io/resource
             io/input-stream
             response
             (assoc :headers {"Content-Type" "text/html; charset=utf-8"})))
    #{:sample-login-webapp.roles/user})))

(defn home-routes [endpoint]
  (routes
   (GET "/" _ (redirect "/authenticated"))
   (GET "/login" _
        (-> "public/login.html"
            io/resource
            io/input-stream
            response
            (assoc :headers {"Content-Type" "text/html; charset=utf-8"})))
   (resources "/")
   authorized-routes
   (not-found (fn [req] (str "Not Found: " (:uri req))))))
