package com.easy_station.sso.auth.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RefreshToken {

    @Id
    private String id;
    private Instant expiresIn;
    private String userId;

    @CreatedDate
    private Instant createdAt;

    public RefreshToken( Instant expiresIn, String userId) {
        this.expiresIn = expiresIn;
        this.userId = userId;
        this.createdAt = Instant.now();
    }
}
