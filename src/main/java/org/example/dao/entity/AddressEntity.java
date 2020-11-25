package org.example.dao.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "address", schema = "sakila")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="address_id")
    private int id;

    @Column
    private String address;

    @Column
    private String address2;

    @Column
    private String district;

    @Column(name="city_id")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column
    private String phone;

    @Column(name="last_update")
    private Timestamp lastUpdate;
}
