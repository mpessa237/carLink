package com.herve.carLink.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "revoked_tokens")
public class RevokedToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer revokedTokenId;
    @Column(length = 2048)
    private String token;
    private Long expirationTime;
}
