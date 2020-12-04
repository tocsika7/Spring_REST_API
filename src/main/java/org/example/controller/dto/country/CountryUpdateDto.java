package org.example.controller.dto.country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryUpdateDto extends CountryDto {

    private String newCountry;
}
