(ns sample-login-webapp.config
  (:require [cemerick.friend :as friend]
            (cemerick.friend [workflows :as workflows]
                             [credentials :as creds])
            [environ.core :refer [env]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.session.cookie :refer [cookie-store]]
            [sample-login-webapp.roles]))

; a dummy in-memory user "database"
(def users {"root" {:username "root"
                    :password (creds/hash-bcrypt "admin_password")
                    :roles #{:sample-login-webapp.roles/admin}}
            "jane" {:username "jane"
                    :password (creds/hash-bcrypt "user_password")
                    :roles #{:sample-login-webapp.roles/user}}})

(defn config []
  {:http-port  (Integer. (or (env :port) 10555))
   :middleware [[friend/authenticate
                 {:login-uri "/login"
                  :default-landing-uri "/authenticated"
                  :credential-fn (partial creds/bcrypt-credential-fn users)
                  :workflows [(workflows/interactive-form)]}]
                [wrap-defaults api-defaults]
                wrap-with-logger
                [wrap-session {:store (cookie-store {:key "a 16-byte secret"})}]
                wrap-gzip]})
