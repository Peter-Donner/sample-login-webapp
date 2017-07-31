(ns cljs.user
  (:require [sample-login-webapp.core]
            [sample-login-webapp.system :as system]))

(def go system/go)
(def reset system/reset)
(def stop system/stop)
(def start system/start)
