package com.chatfuel.services;

import com.chatfuel.domain.Elevator;
import com.chatfuel.domain.Person;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;

@Service
public class ElevatorServiceBaseImplGen3 extends ElevatorServiceBase implements ElevatorService {

    /**
     * Gen3 elevator, with outside & inside priority
     * change direction supported
     *
     */

    @Override
    public void elevatorInitiate(){

        Comparator<Person> personComparator = Comparator.comparing(e->{
            if (getApplicableElevator().isUnderProgress())
                return e.getDirection()==getApplicableElevator().getDirection()
                        ? Math.abs(e.getDesireFloor()-getApplicableElevator().getCurrentFloor())
                        : Math.abs(Math.abs(e.getCurrentFloor()-e.getDesireFloor()) - getApplicableElevator().getCurrentFloor());
            else
                return Math.abs(e.getCurrentFloor()-getApplicableElevator().getCurrentFloor());
        });
        personComparator = personComparator.thenComparing(Comparator.comparing(Person::getTimeStamp));
        setPersonComparator(personComparator);
        setElevators(Collections.nCopies(super.getElevatorNumber(), new Elevator(super.getFloorTotal())));
    }

}
