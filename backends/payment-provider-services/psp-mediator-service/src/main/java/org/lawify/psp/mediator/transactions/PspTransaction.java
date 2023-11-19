package org.lawify.psp.mediator.transactions;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "psp_transaction")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PspTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private UUID paymentId;
    @Column
    private Date timeStamp;
    @Column
    private UUID merchantId;
    @Column
    private Date createdAt;
}
