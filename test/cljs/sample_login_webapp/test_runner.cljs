(ns sample-login-webapp.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [sample-login-webapp.core-test]
   [sample-login-webapp.common-test]))

(enable-console-print!)

(doo-tests 'sample-login-webapp.core-test
           'sample-login-webapp.common-test)
