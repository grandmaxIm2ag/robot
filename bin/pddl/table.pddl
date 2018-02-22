(define (problem CoreGame)
(:domain Robot)
(:objects 
	a51 a52 a53 a54 a55 - node
	a41 a42 a43 a44 a45 - node
	a31 a32 a33 a34 a35 - node
	a21 a22 a23 a24 a25 - node
	a11 a12 a13 a14 a15 - node
	pl1 pl2 pl3 pl4 pl5 - Pallet)
(:init
	;; Initial configuration
	(at-robby a13)
	(gripperempty)
	(at-adversary a53)
	(clear pl1)
	(at pl1 a31)
	(clear pl2)
	(at pl2 a44)
	(clear pl3)
	(at pl3 a11)
	(clear pl4)
	(at pl4 a24)
	(clear pl5)
	(at pl5 a32)

	;; Graph construction
	(connected dock a11)
	(connected dock a12)
	(connected dock a13)
	(connected dock a14)
	(connected dock a15)
	(connected a11 a12)
	(connected a11 dock)
	(connected a11 a21)
	(connected a12 a11)
	(connected a12 dock)
	(connected a12 a22)
	(connected a12 a13)
	(connected a13 a12)
	(connected a13 dock)
	(connected a13 a23)
	(connected a13 a14)
	(connected a14 a13)
	(connected a14 dock)
	(connected a14 a24)
	(connected a14 a15)
	(connected a15 a14)
	(connected a15 dock)
	(connected a15 a25)
	(connected a21 a11)
	(connected a21 a31)
	(connected a21 a22)
	(connected a22 a21)
	(connected a22 a12)
	(connected a22 a32)
	(connected a22 a23)
	(connected a23 a22)
	(connected a23 a13)
	(connected a23 a33)
	(connected a23 a24)
	(connected a24 a23)
	(connected a24 a14)
	(connected a24 a34)
	(connected a24 a25)
	(connected a25 a24)
	(connected a25 a15)
	(connected a25 a35)
	(connected a31 a21)
	(connected a31 a41)
	(connected a31 a32)
	(connected a32 a31)
	(connected a32 a22)
	(connected a32 a42)
	(connected a32 a33)
	(connected a33 a32)
	(connected a33 a23)
	(connected a33 a43)
	(connected a33 a34)
	(connected a34 a33)
	(connected a34 a24)
	(connected a34 a44)
	(connected a34 a35)
	(connected a35 a34)
	(connected a35 a25)
	(connected a35 a45)
	(connected a41 a31)
	(connected a41 a51)
	(connected a41 a42)
	(connected a42 a41)
	(connected a42 a32)
	(connected a42 a52)
	(connected a42 a43)
	(connected a43 a42)
	(connected a43 a33)
	(connected a43 a53)
	(connected a43 a44)
	(connected a44 a43)
	(connected a44 a34)
	(connected a44 a54)
	(connected a44 a45)
	(connected a45 a44)
	(connected a45 a35)
	(connected a45 a55)
	(connected a51 a41)
	(connected a51 a52)
	(connected a52 a51)
	(connected a52 a42)
	(connected a52 a53)
	(connected a53 a52)
	(connected a53 a43)
	(connected a53 a54)
	(connected a54 a53)
	(connected a54 a44)
	(connected a54 a55)
	(connected a55 a54)
	(connected a55 a45)
)

(:goal (and
		(at-dock pl1)
		(at-dock pl2)
		(at-dock pl3)
		(at-dock pl4)
		(at-dock pl5)
	)
))