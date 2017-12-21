package com.chatfuel;

import com.chatfuel.domain.Elevator;
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
    }

    /**
     * Implement here advanced choosing elevator in case of multi
     *
     */

    public Elevator getApplicableElevator(){
        //TODO avoid get by index
        return elevators.get(0);
    }

    public void operateElevator(Elevator elevator){

        elevator.setUnderOperate(true);
        IntSummaryStatistics inStream = IntStream.of(getElevatorPriorFloor(elevator), elevator.getCurrentFloor()).summaryStatistics();

        if (elevator.isDoorsOpened()){
            try {
                Thread.sleep(doorsDelay);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            elevator.setDoorsOpened(false);
            System.out.println("Doors are closing");
        }

        Thread thread = new Thread(() -> {

            IntStream.rangeClosed(inStream.getMin(), inStream.getMax())
                    .boxed()
                    .sorted(elevator.getCurrentFloor()==inStream.getMin() ? Comparator.naturalOrder() : Comparator.reverseOrder() )
                    .forEach(e->{

                        try {
                            Thread.sleep(2000 );
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        elevator.setCurrentFloor(e);
                        System.out.println("Current floor is: "+e);

                    });

            elevator.setDoorsOpened(true);
            try {
                Thread.sleep(doorsDelay);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("Doors open");

            elevator.setUnderOperate(false);

            if (elevator.getOutside().size()>0)
                operateElevator(elevator);

        });
        thread.start();

    }

    private int getElevatorPriorFloor(Elevator elevator){

        return elevator.getOutside().poll().getCurrentFloor();
    }
}
