package lk.ijse.gdse.internal_support_ticket_system.service;

import lk.ijse.gdse.internal_support_ticket_system.dto.request.TicketCreateRequest;
import lk.ijse.gdse.internal_support_ticket_system.dto.response.TicketResponse;
import lk.ijse.gdse.internal_support_ticket_system.entity.Ticket;
import lk.ijse.gdse.internal_support_ticket_system.entity.TicketStatusHistory;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.Priority;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.TicketStatus;

import java.util.List;

public interface TicketService {
    public TicketResponse createTicket(TicketCreateRequest ticket);
    public List<TicketResponse> getAllTickets(TicketStatus status, Priority priority);
    public TicketResponse getTicketById(Long id);
    public TicketResponse updateStatus(Long id, TicketStatus newStatus, String actorRef);
    public TicketResponse assignTicket(Long id, String assigneeRef);
    public List<TicketStatusHistory> getTicketHistory(Long id);
}
