;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; towers prob4 : 5 discs and 4 towers
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (problem BLOCKS-4-0)
(:domain TOWERS)
(:objects 
	D C B A H - block
	t1 t2 t3 t4 - tower)
(:INIT (CLEAR H) (ON H A) (ON C D) (ON B C) (ON A B) (ONTOWER A t1) (ONTOWER B t1) (ONTOWER C t1) (ONTOWER D t1) 
(ONTOWER H t1) (BASE D t1) (ORDER H C) (ORDER H D) (ORDER H B) (ORDER H A) (ORDER A B) (ORDER A C) (ORDER A D)
 (ORDER B C) (ORDER B D) (ORDER C D) (HANDEMPTY) (TOWEREMPTY t2) (TOWEREMPTY t3) (TOWEREMPTY t4))
(:goal (AND (CLEAR H) (ON C D) (ON B C) (ON A B) (ONTOWER A t3) (ONTOWER B t3) (ONTOWER C t3) (ONTOWER D t3) 
(BASE D t3) (ORDER H C) (ORDER H D) (ORDER H B) (ORDER H A) (ON H A) (ONTOWER H t3) (TOWEREMPTY t4)
(ORDER A B) (ORDER A C) (ORDER A D) (ORDER B C) (ORDER B D) (ORDER C D) (TOWEREMPTY t1) (TOWEREMPTY t2)))
)