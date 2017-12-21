package com.chatfuel;

import com.chatfuel.domain.Direction;
import com.chatfuel.domain.Elevator;
import com.chatfuel.domain.Person;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class ElevatorService {

    @Setter @Getter private int floorTotal;
    @Setter @Getter private double floorHeight;
    @Setter @Getter private int speed;
    @Setter @Getter private int doorsDelay;
    @Setter @Getter private int elevatorNumber;

    private List<Elevator> elevators;

    public void elevatorInitiate(){
        elevators = Collections.nCopies(elevatorNumber, new Elevator(floorTotal));
        elevators.forEach(e->System.out.println(e.toString()+" Current floor is: "+e.getCurrentFloor()));
    }

    /**
     * Implement here advanced choosing elevator in case of multi
     *
     */

    public Elevator getApplicableElevator(){
        //TODO avoid get by index
        return elevators.get(0);
    }

    public void operateElevator(Elevator elevator) {

        Person nextPerson;
        IntSummaryStatistics inStream;

        if (elevator.isUnderOperate()){
            nextPerson = elevator.getInside().poll();
            inStream = IntStream
                    .of(nextPerson.getDesireFloor(), elevator.getCurrentFloor())
                    .summaryStatistics();
        }
        else {
            elevator.setUnderOperate(true);
            nextPerson = elevator.getOutside().poll();
            inStream = IntStream
                    .of(nextPerson.getCurrentFloor(), elevator.getCurrentFloor())
                    .summaryStatistics();
        }

        elevator.setDirection(elevator.getCurrentFloor()==inStream.getMin() ? Direction.UP : Direction.DOWN);

        Runnable runnable = () -> {

            operateDoorToClose(elevator);

            IntStream.rangeClosed(inStream.getMin(), inStream.getMax())
                    .boxed()
                    .sorted(elevator.getDirection()==Direction.UP ? Comparator.naturalOrder() : Comparator.reverseOrder() )
                    .forEach(e->{

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

//            elevator.getInside().add(nextPerson);

            if (elevator.getInside().size()>0 || elevator.getOutside().size()>0)
                operateElevator(elevator);

            elevator.setUnderOperate(false);

        };
        Thread t = new Thread(runnable);
        t.start();

    }

    private void operateDoorToClose(Elevator elevator){

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

    private void operateDoorToOpen(Elevator elevator){

        elevator.setDoorsOpened(true);
        try {
            Thread.sleep(doorsDelay);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(elevator.toString()+ " Doors open");
    }
}
