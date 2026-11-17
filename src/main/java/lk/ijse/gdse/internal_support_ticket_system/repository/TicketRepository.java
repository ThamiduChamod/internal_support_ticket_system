package lk.ijse.gdse.internal_support_ticket_system.repository;

import lk.ijse.gdse.internal_support_ticket_system.entity.Ticket;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.Priority;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByPriority(Priority priority);
    List<Ticket> findByStatusAndPriority(TicketStatus status, Priority priority);
}