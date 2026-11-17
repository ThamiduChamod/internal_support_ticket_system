package lk.ijse.gdse.internal_support_ticket_system.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse.internal_support_ticket_system.dto.request.TicketCreateRequest;
import lk.ijse.gdse.internal_support_ticket_system.dto.request.TicketStatusUpdateRequest;
import lk.ijse.gdse.internal_support_ticket_system.dto.response.TicketResponse;
import lk.ijse.gdse.internal_support_ticket_system.entity.TicketStatusHistory;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.Priority;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.TicketStatus;
import lk.ijse.gdse.internal_support_ticket_system.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    // POST /api/tickets - Create a ticket
    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody TicketCreateRequest request) {
        TicketResponse created = ticketService.createTicket(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET /api/tickets - List and filter tickets (status & priority)
    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) Priority priority) {
        List<TicketResponse> tickets = ticketService.getAllTickets(status, priority);
        return ResponseEntity.ok(tickets);
    }

    // GET /api/tickets/{id} - Get one ticket
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        TicketResponse ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    // PATCH /api/tickets/{id}/status - Change status with workflow validation
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody TicketStatusUpdateRequest request) {
        try {
            TicketResponse updated = ticketService.updateStatus(id, request.getStatus(), request.getActorRef());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // PATCH /api/tickets/{id}/assignee - Assign or reassign a ticket
    @PatchMapping("/{id}/assignee")
    public ResponseEntity<TicketResponse> assignTicket(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {
        String assigneeRef = requestBody.get("assigneeRef");
        TicketResponse updated = ticketService.assignTicket(id, assigneeRef);
        return ResponseEntity.ok(updated);
    }

    // GET /api/tickets/{id}/history - View status history
    @GetMapping("/{id}/history")
    public ResponseEntity<List<TicketStatusHistory>> getTicketHistory(@PathVariable Long id) {
        List<TicketStatusHistory> history = ticketService.getTicketHistory(id);
        return ResponseEntity.ok(history);
    }
}