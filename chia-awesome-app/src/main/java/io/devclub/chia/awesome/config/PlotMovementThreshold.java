package io.devclub.chia.awesome.config;

import io.devclub.chia.awesome.api.rest.model.Disk;
import lombok.Data;

@Data
public class PlotMovementThreshold {
    private final ThresholdType type;
    private final Integer value;

    public PlotMovementThreshold(ThresholdType type,
                                 Integer value) {
        this.type = type;
        this.value = value;
    }

    public Boolean isPercentThreshold() {
        return ThresholdType.PERCENT.equals(getType());
    }

    public Boolean isGibSpaceThreshold() {
        return ThresholdType.GIB_SPACE.equals(getType());
    }

    public Boolean isDiskBelowGibSPace(Disk disk) {
        return disk.getAvailableSpace() / 1024 / 1024 / 1024 < getValue();
    }

    public Boolean isDiskBelowPercent(Disk disk) {
        return disk.getUsedSpace() / disk.getTotalSpace() * 100 < getValue();
    }
}
