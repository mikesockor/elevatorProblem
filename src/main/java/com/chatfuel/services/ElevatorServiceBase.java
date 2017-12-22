package com.chatfuel.services;

import com.chatfuel.domain.Direction;
import com.chatfuel.domain.Elevator;
import com.chatfuel.domain.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.IntStream;

public class ElevatorServiceBase {

    @Setter @Getter private int floorTotal;
    @Setter @Getter private double floorHeight;
    @Setter @Getter private int speed;
    @Setter @Getter private int doorsDelay;
    @Setter @Getter private int elevatorNumber;
    @Setter @Getter private Comparator<Person> personComparator;// = (p1, p2) -> (int) (p1.getCurrentFloor() - p2.getCurrentFloor());
    @Setter @Getter private List<Elevator> elevators;

    /**
     * Implement here advanced choosing elevator in case of multi
     *
     */

    public Elevator getApplicableElevator(){
        //TODO avoid get by index
        return elevators.stream().findAny().get();
    }

    public void operateElevator(Elevator elevator) {

        Person personForOperate = elevator.getQueue().poll();
        if (personForOperate!=null)
            operatePerson(elevator, personForOperate);

    }

    public void operatePerson(Elevator elevator, Person person){

        int requiredFloor = person.isInProgress() ? person.getDesireFloor() : person.getCurrentFloor();

        IntSummaryStatistics inStream = IntStream
                .of(requiredFloor, elevator.getCurrentFloor())
                .summaryStatistics();

        elevator.setDirection(elevator.getCurrentFloor()==inStream.getMin() ? Direction.UP : Direction.DOWN);

        Runnable runnable = () -> {

            operateDoorToClose(elevator);

            IntStream.rangeClosed(inStream.getMin(), inStream.getMax())
                    .boxed()
                    .sorted(elevator.getDirection()==Direction.UP ? Comparator.naturalOrder() : Comparator.reverseOrder() )
                    .forEach(e->{

                        elevator.setUnderProgress(true);

                        try {
                            Thread.sleep((long)(speed/floorHeight*1000) );
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        elevator.setCurrentFloor(e);
                        System.out.println(elevator.toString()+""
                                +(elevator.getDirection()==Direction.UP?"▲":"▼")
                                +" Current floor is: "+e);

                    });

            operateDoorToOpen(elevator);

            if (!person.isInProgress()){
                person.setInProgress(true);
                operatePerson(elevator, person);
                return;
            }

            if (elevator.getQueue().size()>0)
                operateElevator(elevator);
            else
                elevator.setUnderProgress(false);

        };
        Thread t = new Thread(runnable);
        t.start();

    }

    public void operateDoorToClose(Elevator elevator){

        if (elevator.isDoorsOpened()){
            try {
                Thread.sleep(doorsDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            elevator.setDoorsOpened(false);
            System.out.println(elevator.toString()+ " Doors are closing");
        }

    }

    public void operateDoorToOpen(Elevator elevator){

        elevator.setDoorsOpened(true);
        try {
            Thread.sleep(doorsDelay);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(elevator.toString()+ " Doors open");
    }
}
