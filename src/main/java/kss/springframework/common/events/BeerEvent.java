package kss.springframework.common.events;

import kss.springframework.msscbeerservice.web.model.BeerDto;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 4002046345825406597L;

    private BeerDto beerDto;
//
//    public BeerEvent(BeerDto beerDto) {
//        this.beerDto = beerDto;
//    }
}
