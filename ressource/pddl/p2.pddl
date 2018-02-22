;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; towers prob2 : 3 discs and 3 towers
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (problem BLOCKS-4-0)
(:domain TOWERS)
(:objects 
	D C B - block
	t1 t2 t3 - tower)
(:INIT (CLEAR B) (ON C D) (ON B C) (ONTOWER B t1) (ONTOWER C t1) (ONTOWER D t1) (BASE D t1)
(ORDER B C) (ORDER B D) (ORDER C D) (HANDEMPTY) (TOWEREMPTY t2) (TOWEREMPTY t3))
(:goal (AND (CLEAR B) (ON C D) (ON B C) (ONTOWER B t3) (ONTOWER C t3) (ONTOWER D t3) 
(BASE D t3) (ORDER B C) (ORDER B D) (ORDER C D) (TOWEREMPTY t1) (TOWEREMPTY t2)))
)