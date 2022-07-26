package com.dataconvertor.consumer.dao;

import com.dataconvertor.common.enums.Destination;
import com.dataconvertor.common.enums.Operation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "received_message_details")
@NoArgsConstructor
public class MessageDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private Operation operation;
    private int number1;
    private int number2;
    private String message_id;
    @Enumerated(EnumType.STRING)
    @Column(name="destination")
    private Destination destination;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date", nullable = false)
    private Date modifyDate;

    public MessageDao(Operation operation, int number1, int number2, String messageId, Destination destination) {
        this.operation = operation;
        this.number1 = number1;
        this.number2 = number2;
        this.destination = destination;
        this.message_id = messageId;
    }

    @PrePersist
    protected void onCreate() {
        createDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modifyDate = new Date();
    }
}
