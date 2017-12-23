package com.chatfuel;

import com.chatfuel.domain.Elevator;
import com.chatfuel.domain.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;

public class ElevatorServiceImplGen3Test {

    private Elevator elevator;

    @Before
    public void prepare(){

        Comparator<Person> personComparator = Comparator.comparing(e->{
            if (elevator.isUnderProgress())
                return e.getDirection()==elevator.getDirection()
                        ? Math.abs(e.getDesireFloor()-elevator.getCurrentFloor())
                        : Math.abs(Math.abs(e.getCurrentFloor()-e.getDesireFloor()) - elevator.getCurrentFloor());
            else
                return Math.abs(e.getCurrentFloor()-elevator.getCurrentFloor());
        });
        personComparator = personComparator.thenComparing(Comparator.comparing(Person::getTimeStamp));
        elevator = new Elevator(100);
        elevator.setQueue(new PriorityQueue<>(personComparator));
    }

    @Test
    public void comparatorGen2InProgress1() {

        elevator.setCurrentFloor(0);
        elevator.setUnderProgress(true);

        Person person1 = new Person(0,100);
        person1.setInProgress(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Person person2 = new Person(3,0);
        person2.setInProgress(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        elevator.addPersonToQueue(person1);
        elevator.addPersonToQueue(person2);

        assertEquals(person2, elevator.getQueue().poll());
        assertEquals(person1, elevator.getQueue().poll());

    }

}
