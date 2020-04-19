package kss.springframework.msscbeerservice.web.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto implements Serializable {

    private UUID id;

    private Integer version;

    private String beerName;

    private BeerStyleEnum beerStyle;

    private String upc;


}

