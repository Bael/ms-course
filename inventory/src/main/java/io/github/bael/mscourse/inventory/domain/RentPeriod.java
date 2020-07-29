package io.github.bael.mscourse.inventory.domain;

import com.google.common.collect.Range;
import io.github.bael.mscourse.inventory.entity.SchedulePeriod;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class RentPeriod {
    private Range<LocalDateTime> period;

    public static RentPeriod of(LocalDateTime start, LocalDateTime finish) {
        return RentPeriod.builder()
                .period(Range.closed(start, finish))
                .build();
    }

    public boolean intersects(RentPeriod period) {
        return period.getPeriod().isConnected(this.getPeriod());
    }
}
