(ns urbest.references.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :lang
 (fn [db _] (:lang db)))

(rf/reg-sub
 :paused?
 (fn [db [_ id]] (contains? (:paused db) id)))

(rf/reg-sub
 :demo-status
 (fn [db _] (get-in db [:demo :status])))
