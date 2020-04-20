package kss.springframework.msscbeerservice.web.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beer implements Serializable {

   private UUID id;
   private Integer version;
   private OffsetDateTime createdDate;
   private OffsetDateTime lastModifiedDate;
   private String beerName;
   private BeerNameEnum beerStyle;
   private Long upc;
   private BigDecimal price;
   private Integer quantityOnHand;

}

