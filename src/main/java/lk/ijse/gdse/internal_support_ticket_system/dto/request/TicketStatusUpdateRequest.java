package lk.ijse.gdse.internal_support_ticket_system.dto.request;

import jakarta.validation.constraints.NotNull;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.TicketStatus;
import lombok.Data;

@Data
public class TicketStatusUpdateRequest {
    @NotNull(message = "New status is required")
    private TicketStatus status;

    private String actorRef; //
}
