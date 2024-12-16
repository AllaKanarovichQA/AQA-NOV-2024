package org.prog.selenium.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonIgnoreProperties(ignoreUnknown = true)


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private String city;


}