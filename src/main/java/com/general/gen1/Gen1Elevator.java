package com.general.gen1;

import com.general.AbstractElevator;
import com.general.ElevatorStatus;
import com.general.eventSystem.EventDispatcher;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

public class Gen1Elevator extends AbstractElevator<Gen1Event, Gen1Property> {

    private static Comparator<Gen1Event> comparatorFifo =
        (p1, p2) -> (int) (
            (p1.getTimeStamp() - (p1.isInternal() ? 100000 * p1.getFloor() : 0))
                - (p2.getTimeStamp()) - (p2.isInternal() ? 100000 * p2.getFloor() : 0)
        );

    Gen1Elevator(EventDispatcher dispatcher, Gen1Property gen1Property) {
        super(dispatcher, new PriorityQueue<>(comparatorFifo), gen1Property);
        dispatcher.addEventListener(Gen1Event.class, this);
    }

    @Override
    public void process() {

        if (getCurrentStatus() == ElevatorStatus.OutOfOrder) {
            getEventQueue().clear();
            return;
        }
        else if (getCurrentStatus() != ElevatorStatus.Hold)
            return;

        Optional.ofNullable(getEventQueue().poll())
            .map(polledEvent -> {
                IntSummaryStatistics inStream = IntStream.of(polledEvent.getFloor(), getCurrentFloor()).summaryStatistics();
                setCurrentStatus(getCurrentFloor() == inStream.getMin() ? ElevatorStatus.MoveUp : ElevatorStatus.MoveDown);
                sleep(getProperty().getMillisecondsPerFloor());
                Runnable runnable = () -> {
                    IntStream.rangeClosed(inStream.getMin(), inStream.getMax())
                        .boxed()
                        .sorted(getCurrentStatus() == ElevatorStatus.MoveUp ? Comparator.naturalOrder() : Comparator.reverseOrder())
                        .forEach(e -> {
                            setCurrentFloor(e);
                            sleep(getProperty().getMillisecondsPerFloor());
                        });

                    setCurrentStatus(ElevatorStatus.DoorsOpening);
                    sleep(getProperty().getMillisecondsDoorsDelay());
                    setCurrentStatus(ElevatorStatus.DoorsClosing);
                    sleep(getProperty().getMillisecondsDoorsDelay());

                    if (getEventQueue().size() > 0) {
                        setCurrentStatus(ElevatorStatus.Hold);
                        process();
                    }
                    else
                        setCurrentStatus(ElevatorStatus.Hold);

                };
                return new Thread(runnable);
            })
            .orElse(new Thread())
            .start();

    }

}
