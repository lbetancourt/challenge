package com.mercadolibre.challengue.ip.infrastructure.postgres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IPForbidden")
public class IPForbiddenPostgres {
    @Id
    private UUID id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "forbidden_date")
    private LocalDateTime forbiddenDate;
}
