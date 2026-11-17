package lk.ijse.gdse.internal_support_ticket_system.entity;

import jakarta.persistence.*;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.Priority;
import lk.ijse.gdse.internal_support_ticket_system.entity.enums.TicketStatus;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets", indexes = {
        @Index(name = "idx_ticket_status", columnList = "status"),
        @Index(name = "idx_ticket_priority", columnList = "priority")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TicketStatus status;

    @Column(name = "requester_name", nullable = false, length = 100)
    private String requesterName;

    @Column(name = "requester_email", nullable = false, length = 100)
    private String requesterEmail;

    @Column(name = "assignee_ref", length = 100)
    private String assigneeRef;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TicketStatusHistory> historyList = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = TicketStatus.OPEN;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}