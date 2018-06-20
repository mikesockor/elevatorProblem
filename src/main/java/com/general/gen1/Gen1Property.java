package com.general.gen1;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Gen1Property {

    private @Getter int millisecondsPerFloor;
    private @Getter int millisecondsDoorsDelay;

}
