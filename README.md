# elevatorProblem
elevator simulator

http://localhost:8080/gen2/initiate
```json
{
    "floorTotal": 20,
    "floorHeight": 2,
    "speed": 2,
    "doorsDelay": 2000,
    "elevatorNumber": 1
}
```

http://localhost:8080/gen2/call
```json
{
    "currentFloor": 5,
    "desireFloor": 10
}
```
```json
{
    "currentFloor": 9,
    "desireFloor": 12
}
```

Output

```
com.chatfuel.domain.Elevator@180256b5 Current floor is: 3
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 3
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 4
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 5
com.chatfuel.domain.Elevator@180256b5 Doors open
com.chatfuel.domain.Elevator@180256b5 Doors are closing
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 5
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 6
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 7
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 8
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 9
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 10
com.chatfuel.domain.Elevator@180256b5 Doors open
com.chatfuel.domain.Elevator@180256b5 Doors are closing
com.chatfuel.domain.Elevator@180256b5▼ Current floor is: 10
com.chatfuel.domain.Elevator@180256b5▼ Current floor is: 9
com.chatfuel.domain.Elevator@180256b5 Doors open
com.chatfuel.domain.Elevator@180256b5 Doors are closing
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 9
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 10
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 11
com.chatfuel.domain.Elevator@180256b5▲ Current floor is: 12
com.chatfuel.domain.Elevator@180256b5 Doors open
```

___________________________________________________________
upcoming future
gen4, which support elevator choice with pre calculated queue
before come in elevator you should set your desire floor
like in Sheraton towers hotel

https://github.com/mikesockor/elevatorProblem/blob/master/src/main/resources/995.jpg