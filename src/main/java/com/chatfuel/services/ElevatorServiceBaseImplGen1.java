package com.chatfuel.services;

import com.chatfuel.domain.Elevator;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class ElevatorServiceBaseImplGen1 extends ElevatorServiceBase implements ElevatorService {

    @Override
    public void elevatorInitiate(){
        setPersonComparator((p1, p2) -> (int) (p1.getTimeStamp() - p2.getTimeStamp()));
        setElevators(Collections.nCopies(super.getElevatorNumber(), new Elevator(super.getFloorTotal())));
    }

}
