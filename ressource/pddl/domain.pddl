;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Robots world
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (domain Robot)
  (:requirements :strips :typing)
  (:types pallet node)
  (:constants dock - node)
  (:predicates (at ?x - pallet ?y - node)
	       (at-robby ?y - node)
	       (at-dock ?y - pallet)
	       (connected ?x ?y - node)
	       (at-adversary ?y - node)   
	       (clear ?x - pallet)
	       (gripperempty)
	       (holding ?x - pallet)
	       )
   
   (:action move
       :parameters  (?from ?to - node)
       :precondition (and (at-robby ?from) (connected ?from ?to))
       :effect (and  (at-robby ?to)
		     (not (at-robby ?from))))
   
   (:action pick
       :parameters (?pl - pallet ?pos - node)
       :precondition  (and  (at ?pl ?pos) (at-robby ?pos) (gripperempty) (clear ?pl))
       :effect (and (holding ?pl)
		    (not (at ?pl ?pos))
		    (not (clear ?pl)) 
		    (not (gripperempty))))

   (:action deliver
       :parameters  (?pl - pallet)
       :precondition  (and  (holding ?pl) (at-robby dock))
       :effect (and (at-dock ?pl)
		    (gripperempty)
		    (not (holding ?pl)))))
