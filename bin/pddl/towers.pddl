;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; towers world
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (domain TOWERS)
  (:requirements :strips :typing)
  (:types block tower)
  (:predicates (on ?x - block ?y - block)
	       (ontower ?x - block ?y - tower)
	       (base ?x - block ?y - tower)  
	       (towerempty ?x - tower)
	       (clear ?x - block)
	       (order ?x - block ?y - block)
	       (handempty)
	       (holding ?x - block)
	       )

  (:action pick-up-last
	     :parameters (?x - block ?y - tower)
	     :precondition (and (clear ?x) (handempty) (ontower ?x ?y) (base ?x ?y))
	     :effect
	     (and (not (ontower ?x ?y))
		   (not (clear ?x))
		   (not (base ?x ?y))
		   (towerempty ?y)
		   (not (handempty))
		   (holding ?x)))

  (:action put-down-first
	     :parameters (?x - block ?y - tower)
	     :precondition (and (holding ?x) (towerempty ?y))
	     :effect
	     (and (not (holding ?x))
	       (base ?x ?y)
		   (clear ?x)
		   (handempty)
		   (not (towerempty ?y))
		   (ontower ?x ?y)))
  
  (:action stack
	     :parameters (?x - block ?y - block ?z - tower)
	     :precondition (and (holding ?x) (clear ?y) (order ?x ?y) (ontower ?y ?z))
	     :effect
	     (and (not (holding ?x))
		   (not (clear ?y))
		   (clear ?x)
		   (handempty)
		   (ontower ?x ?z)
		   (on ?x ?y)))
  
  (:action unstack
	     :parameters (?x - block ?y - block ?z - tower)
	     :precondition (and (on ?x ?y) (clear ?x) (handempty) (order ?x ?y) (ontower ?x ?z) (ontower ?y ?z))
	     :effect
	     (and (holding ?x)
		   (clear ?y)
		   (not (clear ?x))
		   (not (handempty))
		   (not (ontower ?x ?z))
		   (not (on ?x ?y)))))
