package lk.ijse.gdse.internal_support_ticket_system.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.gdse.internal_support_ticket_system.dto.request.TicketCreateRequest;
import lk.ijse.gdse.internal_support_ticket_system.dto.response.TicketResponse;
import lk.ijse.gdse.internal_support_ticket_system.entity.Ticket;
import lk.ijse.gdse.internal_support_ticket_system.entity.TicketStatusHistory;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.Priority;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.TicketStatus;
import lk.ijse.gdse.internal_support_ticket_system.repository.TicketRepository;
import lk.ijse.gdse.internal_support_ticket_system.repository.TicketStatusHistoryRepository;
import lk.ijse.gdse.internal_support_ticket_system.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketStatusHistoryRepository historyRepository;

    @Transactional
    public TicketResponse createTicket(TicketCreateRequest request) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setRequesterName(request.getRequesterName());
        ticket.setRequesterEmail(request.getRequesterEmail());

        Ticket savedTicket = ticketRepository.save(ticket);

        TicketStatusHistory history = TicketStatusHistory.builder()
                .ticket(savedTicket)
                .oldStatus(TicketStatus.OPEN)
                .newStatus(TicketStatus.OPEN)
                .actorRef(ticket.getRequesterEmail())
                .build();
        historyRepository.save(history);

        return mapToResponse(savedTicket);
    }

    public List<TicketResponse> getAllTickets(TicketStatus status, Priority priority) {
        List<Ticket> tickets;

        if (status != null && priority != null) {
            tickets = ticketRepository.findByStatusAndPriority(status, priority);
        } else if (status != null) {
            tickets = ticketRepository.findByStatus(status);
        } else if (priority != null) {
            tickets = ticketRepository.findByPriority(priority);
        } else {
            tickets = ticketRepository.findAll();
        }

        return tickets.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));
        return mapToResponse(ticket);
    }

    @Transactional
    public TicketResponse updateStatus(Long id, TicketStatus newStatus, String actorRef) {
        // Internal usage සඳහා Entity එක ලබා ගැනීම
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));

        TicketStatus currentStatus = ticket.getStatus();

        if (!isValidTransition(currentStatus, newStatus)) {
            throw new IllegalArgumentException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        if (newStatus == TicketStatus.CLOSED && currentStatus != TicketStatus.RESOLVED) {
            throw new IllegalArgumentException("Ticket can only be closed when it is in RESOLVED state.");
        }

        ticket.setStatus(newStatus);
        if (newStatus == TicketStatus.RESOLVED) {
            ticket.setResolvedAt(LocalDateTime.now());
        }
        Ticket updatedTicket = ticketRepository.save(ticket);

        TicketStatusHistory history = TicketStatusHistory.builder()
                .ticket(updatedTicket)
                .oldStatus(currentStatus)
                .newStatus(newStatus)
                .actorRef(actorRef != null ? actorRef : "System")
                .build();
        historyRepository.save(history);

        return mapToResponse(updatedTicket);
    }

    @Transactional
    public TicketResponse assignTicket(Long id, String assigneeRef) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));

        ticket.setAssigneeRef(assigneeRef);
        Ticket updatedTicket = ticketRepository.save(ticket);

        return mapToResponse(updatedTicket);
    }

    public List<TicketStatusHistory> getTicketHistory(Long id) {
        // ටිකට් එක තිබේදැයි පරීක්ෂා කිරීම
        boolean exists = ticketRepository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException("Ticket not found with id: " + id);
        }
        return historyRepository.findByTicketIdOrderByChangedAtDesc(id);
    }

    private boolean isValidTransition(TicketStatus current, TicketStatus next) {
        if (current == TicketStatus.OPEN && next == TicketStatus.IN_PROGRESS) return true;
        if (current == TicketStatus.IN_PROGRESS && next == TicketStatus.RESOLVED) return true;
        if (current == TicketStatus.RESOLVED && next == TicketStatus.CLOSED) return true;
        return false;
    }

    private TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .priority(ticket.getPriority())
                .status(ticket.getStatus())
                .requesterName(ticket.getRequesterName())
                .requesterEmail(ticket.getRequesterEmail())
                .assigneeRef(ticket.getAssigneeRef())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .resolvedAt(ticket.getResolvedAt())
                .build();
    }
}