package lk.ijse.gdse.internal_support_ticket_system.repository;

import lk.ijse.gdse.internal_support_ticket_system.entity.TicketStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketStatusHistoryRepository  extends JpaRepository<TicketStatusHistory,Long> {
    List<TicketStatusHistory> findByTicketIdOrderByChangedAtDesc(Long ticketId);
}
