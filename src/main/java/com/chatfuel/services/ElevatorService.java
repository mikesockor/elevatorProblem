package com.chatfuel.services;

import com.chatfuel.domain.Elevator;
import com.chatfuel.domain.Person;

public interface ElevatorService {

    void elevatorInitiate();
    Elevator getApplicableElevator();
    void operateElevator(Elevator elevator);
    void operatePerson(Elevator elevator, Person person);
    void operateDoorToClose(Elevator elevator);
    void operateDoorToOpen(Elevator elevator);
}
