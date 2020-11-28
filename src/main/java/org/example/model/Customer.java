package org.example.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Customer {

    private int  storeId;
    private String firstName;
    private String lastName;
    private String email;
    private int addressId;
    private int active;
}
