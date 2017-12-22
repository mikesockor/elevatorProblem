package com.chatfuel.services;

import com.chatfuel.domain.Elevator;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ElevatorServiceBaseImplGen2 extends ElevatorServiceBase implements ElevatorService {

    @Override
    public void elevatorInitiate(){
        setPersonComparator((p1, p2) -> (int) (p1.getCurrentFloor() - p2.getCurrentFloor()));
        setElevators(Collections.nCopies(super.getElevatorNumber(), new Elevator(super.getFloorTotal(),getPersonComparator())));
    }

}
