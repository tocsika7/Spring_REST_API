package org.example.model;

import lombok.*;

import java.sql.Timestamp;


@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Country {

    private int countyId;
    private String country;
    private Timestamp lastUpdate;
}
