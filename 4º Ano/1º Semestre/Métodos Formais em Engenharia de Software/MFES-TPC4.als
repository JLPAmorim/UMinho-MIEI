// Modelo abstracto de um sistema de um circuito Euleriano

abstract sig Node {}

abstract sig Edge { connects: set Node } { #connects = 2 }

sig Path { firstStep: Step }

sig Step {
	from, to: Node,
	via: Edge,
	nextStep: lone Step
} { via.connects = from + to }

fact {
	all curr:Step, next:curr.nextStep |
		next.from = curr.to
}

fun steps (p:Path): set Step {
	p.firstStep.*nextStep
}
pred path() {
	some p:Path | steps[p].via = Edge
}


run path for 5 but exactly 1 Path 