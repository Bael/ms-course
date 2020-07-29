package io.github.bael.mscourse.orders.external;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface InventoryConnector {
    boolean isSKUAvailable(String SKU, LocalDateTime periodStart, LocalDateTime periodFinish);
}
