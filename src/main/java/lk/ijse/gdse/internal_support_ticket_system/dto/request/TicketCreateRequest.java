package lk.ijse.gdse.internal_support_ticket_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.Priority;
import lombok.Data;

@Data
public class TicketCreateRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotBlank(message = "Requester name is required")
    private String requesterName;

    @NotBlank(message = "Requester email is required")
    @Email(message = "Invalid email format")
    private String requesterEmail;
}
