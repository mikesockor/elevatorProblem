package com.general.gen1;

import com.general.eventSystem.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.Objects;

@ToString
public class Gen1Event implements Event {

    @Setter @Getter private int     floor;
    @Setter @Getter private boolean internal;
    @Getter private         long    timeStamp;

    public Gen1Event(int floor, boolean internal) {
        this.timeStamp = Instant.now().toEpochMilli();
        this.floor = floor;
        this.internal = internal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gen1Event gen1Event = (Gen1Event) o;
        return floor == gen1Event.floor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor);
    }
}
