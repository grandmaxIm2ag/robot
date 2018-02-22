;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; towers prob1 : 4 discs and 3 towers
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (problem BLOCKS-4-0)
(:domain TOWERS)
(:objects 
	D C B A - block
	t1 t2 t3 - tower)
(:INIT (CLEAR A) (ON C D) (ON B C) (ON A B) (ONTOWER A t1) (ONTOWER B t1) (ONTOWER C t1) (ONTOWER D t1) (BASE D t1)
(ORDER A B) (ORDER A C) (ORDER A D) (ORDER B C) (ORDER B D) (ORDER C D) (HANDEMPTY)
(TOWEREMPTY t2) (TOWEREMPTY t3))
(:goal (AND (CLEAR A) (ON C D) (ON B C) (ON A B) (ONTOWER A t3) (ONTOWER B t3) (ONTOWER C t3) (ONTOWER D t3) 
(BASE D t3)
(ORDER A B) (ORDER A C) (ORDER A D) (ORDER B C) (ORDER B D) (ORDER C D) (TOWEREMPTY t1) (TOWEREMPTY t2)))
)