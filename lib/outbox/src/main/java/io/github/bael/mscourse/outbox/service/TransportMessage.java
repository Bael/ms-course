package io.github.bael.mscourse.outbox.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportMessage {
    private String messageId;
    private String type;
    private String payload;

}
