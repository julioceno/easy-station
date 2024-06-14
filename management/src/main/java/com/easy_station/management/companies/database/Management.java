package com.easy_station.management.companies.database;

import com.easy_station.management.users.database.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Table(name = "managements")
@Entity(name = "managements")
@EntityListeners(AuditingEntityListener.class)
public class Management {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "price_hour", nullable = false)
    private Double priceHour;

    @Column(name = "company_id", unique = true)
    private String companyId;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public Management(String companyId, Double priceHour) {
        this.companyId = companyId;
        this.priceHour = priceHour;
    }
}
