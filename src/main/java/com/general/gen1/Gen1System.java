package com.general.gen1;

import com.general.AbstractSystem;

public class Gen1System extends AbstractSystem<Gen1Elevator, Gen1Event> {

    public Gen1System(Gen1Property gen1Property) {
        super();
        addElevator(new Gen1Elevator(getDispatcher(), gen1Property));
    }

    @Override
    public synchronized void onEvent(Gen1Event event) {
        getElevators().get(0)
                .getDispatcher()
                .emitEventAsync(event);
    }

    @Override
    public void onMessage(String message) {
        System.out.printf("%s %s %n", this.toString(), message);
    }
}
