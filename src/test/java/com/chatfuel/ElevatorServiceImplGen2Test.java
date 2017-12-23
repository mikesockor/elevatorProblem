package com.chatfuel;

import com.chatfuel.domain.Elevator;
import com.chatfuel.domain.Person;
import org.junit.Before;
import org.junit.Test;
import java.util.Comparator;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;

public class ElevatorServiceImplGen2Test {

    private Elevator elevator;

    @Before
    public void prepare(){

        Comparator<Person> personComparator = Comparator.comparing(e->{
            if (elevator.isUnderProgress())
                return e.getDirection()==elevator.getDirection() ? Math.abs(e.getDesireFloor()-elevator.getCurrentFloor()) : 0;
            else
                return Math.abs(e.getCurrentFloor()-elevator.getCurrentFloor());
        });
        personComparator = personComparator.thenComparing(Comparator.comparing(Person::getTimeStamp));
        elevator = new Elevator(20);
        elevator.setQueue(new PriorityQueue<>(personComparator));
    }

    @Test
    public void comparatorGen2SameFloor() {

        elevator.setCurrentFloor(0);

        Person person1 = new Person(0,10);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Person person2 = new Person(0,11);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person person3 = new Person(0,13);

        elevator.addPersonToQueue(person1);
        elevator.addPersonToQueue(person2);
        elevator.addPersonToQueue(person3);

        assertEquals(person1, elevator.getQueue().poll());
        assertEquals(person2, elevator.getQueue().poll());
        assertEquals(person3, elevator.getQueue().poll());

    }

    @Test
    public void comparatorGen2DiffFloor() {

        elevator.setCurrentFloor(0);

        Person person1 = new Person(5,10);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Person person2 = new Person(1,11);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person person3 = new Person(7,13);

        elevator.addPersonToQueue(person1);
        elevator.addPersonToQueue(person2);
        elevator.addPersonToQueue(person3);

        assertEquals(person2, elevator.getQueue().poll());
        assertEquals(person1, elevator.getQueue().poll());
        assertEquals(person3, elevator.getQueue().poll());

    }

    @Test
    public void comparatorGen2DiffFloorMiddleCurrent() {

        elevator.setCurrentFloor(6);

        Person person1 = new Person(5,15);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Person person2 = new Person(1,17);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person person3 = new Person(7,20);

        elevator.addPersonToQueue(person1);
        elevator.addPersonToQueue(person2);
        elevator.addPersonToQueue(person3);

        assertEquals(person1, elevator.getQueue().poll());
        assertEquals(person3, elevator.getQueue().poll());
        assertEquals(person2, elevator.getQueue().poll());

    }

    @Test
    public void comparatorGen2InProgress1() {

        elevator.setCurrentFloor(5);
        elevator.setUnderProgress(true);

        Person person1 = new Person(5,10);
        person1.setInProgress(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Person person2 = new Person(3,13);
        person2.setInProgress(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person person3 = new Person(1,19);
        person3.setInProgress(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person person4 = new Person(2,7);

        elevator.addPersonToQueue(person1);
        elevator.addPersonToQueue(person2);
        elevator.addPersonToQueue(person3);
        elevator.addPersonToQueue(person4);

        assertEquals(person1, elevator.getQueue().poll());
        assertEquals(person2, elevator.getQueue().poll());
        assertEquals(person3, elevator.getQueue().poll());

    }

}
