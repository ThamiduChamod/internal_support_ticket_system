package lk.ijse.gdse.internal_support_ticket_system.dto.response;

import lk.ijse.gdse.internal_support_ticket_system.entity.enums.Priority;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private TicketStatus status;
    private String requesterName;
    private String requesterEmail;
    private String assigneeRef;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;
}