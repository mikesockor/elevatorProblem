# elevatorProblem
elevator simulator

main logic is in `private static Comparator<Gen1Event> comparatorFifo = ... ` implementation
which used due Poll invocation to PriorityQueue

###Generation 1
Can accept "push button" event with floor number and outside/inside values
Prioritization:
1) internal call (call inside elevator will consider first)
2) call time (here we have fifo)