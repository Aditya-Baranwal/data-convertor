package com.dataconvertor.consumer.dao;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.dataconvertor.common.enums.Operation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "operation_detail")
@NoArgsConstructor
// maps pojo for json converter.
@JsonPropertyOrder({"number1", "number2", "operation", "result"})
public class OperationDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer number1;
    Integer number2;
    @Enumerated(EnumType.STRING)
    @Column(name="operation")
    private Operation operation;
    Integer result;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date", nullable = false)
    private Date modifyDate;

    public OperationDao(Integer number1, Integer number2, Operation operation, Integer result) {
        this.number1 = number1;
        this.number2 = number2;
        this.operation = operation;
        this.result = result;
    }

    @PrePersist
    protected void onCreate() {
        createDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modifyDate = new Date();
    }

    public Object[] toArray() {
        return new Object[]{this.getNumber1(), this.getNumber2(), this.getOperation().toString(), this.getResult()};
    }

}
