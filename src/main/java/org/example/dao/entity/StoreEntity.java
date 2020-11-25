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
@Table(name = "store", schema = "sakila")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private int storeId;

    @Column(name = "manager_staff_id")
    private int managerStaffId;

    @Column(name = "address_id")
    private int addressId;

    @Column(name = "last_update")
    private Timestamp lastUpdate;
}
