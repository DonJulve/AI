; ------------------------------------------------------------------------------
; MÓDULO MAIN
(defmodule MAIN
  (export deftemplate nodo)
  (export deffunction heuristica))

(deftemplate MAIN::nodo
  (multislot estado)
  (multislot camino)
  (slot heuristica)
  (slot coste (default 0))
  (slot clase (default abierto)))

(defglobal MAIN
  ?*estado-inicial* = (create$ O C O C O)
  ?*estado-final* = (create$ C C C O C))

(deffunction MAIN::heuristica ($?estado)
  (bind ?res 0)
  (loop-for-count (?i 1 5)
    (if (neq (nth$ ?i $?estado)
             (nth$ ?i ?*estado-final*))
      then (bind ?res (+ ?res 1))))
  ?res)

(deffacts MAIN::nodoInicial
  (nodo (estado ?*estado-inicial*)
        (camino)
        (heuristica (heuristica ?*estado-inicial*))
        (clase abierto)))

; Búsqueda A*
(defrule MAIN::pasa-el-mejor-a-cerrado-A*
  ?nodo <- (nodo (heuristica ?h1)
                 (coste ?c1)
                 (clase abierto))
  (not (nodo (clase abierto)
             (heuristica ?h2)
             (coste ?c2&:(< (+ ?c2 ?h2) (+ ?c1 ?h1)))))
  =>
  (modify ?nodo (clase cerrado))
  (focus OPERACIONES))

; ------------------------------------------------------------------------------
; MÓDULO OPERACIONES
(defmodule OPERACIONES
  (import MAIN deftemplate nodo)
  (import MAIN deffunction heuristica))

; Operador para dar vuelta a dos monedas contiguas
(defrule OPERACIONES::dar-vuelta
  (nodo (estado $?a ?x ?y $?b)
        (camino $?movimientos)
        (coste ?coste)
        (clase cerrado))
  =>
  (bind $?nuevo-estado (create$ $?a
                                 (if (eq ?x O)
                                     then C
                                     else O)
                                 (if (eq ?y O)
                                     then C
                                     else O)
                                 $?b))
  (assert (nodo
           (estado $?nuevo-estado)
           (camino $?movimientos (implode$ $?nuevo-estado))
           (heuristica (heuristica $?nuevo-estado))
           (coste (+ ?coste 1)))))


; ------------------------------------------------------------------------------
; MÓDULO RESTRICCIONES
(defmodule RESTRICCIONES
  (import MAIN deftemplate nodo))

; Eliminación de nodos repetidos de igual o mayor coste
(defrule RESTRICCIONES::repeticiones-de-nodo
  (declare (auto-focus TRUE))
  ?nodo1 <- (nodo (estado $?estado)
                  (camino $?camino1))
  ?nodo2 <- (nodo (estado $?estado)
                  (camino $?camino2&:(>= (length$ ?camino1) (length$ ?camino2))))
  (test (neq ?nodo1 ?nodo2))
  =>
  (retract ?nodo1))

; ------------------------------------------------------------------------------
; MÓDULO SOLUCIÓN
(defmodule SOLUCION
  (import MAIN deftemplate nodo))

(defrule SOLUCION::reconoce-solucion
  (declare (auto-focus TRUE))
  ?nodo <- (nodo (estado C C C O C)
                 (camino $?movimientos))
  =>
  (retract ?nodo)
  (assert (solucion $?movimientos)))

(defrule SOLUCION::escribe-solucion
  (solucion $?movimientos)
  =>
  (printout t "La solucion tiene " (length$ $?movimientos) " pasos" crlf)
  (printout t "O C O C O" crlf)
  (loop-for-count (?i 1 (length$ $?movimientos))
    (printout t (nth$ ?i $?movimientos) crlf))
  (watch statistics)
  
  (halt))