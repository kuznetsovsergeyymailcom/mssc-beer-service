package kss.springframework.msscbeerservice.loader;

import kss.springframework.msscbeerservice.domain.Beer;
import kss.springframework.msscbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {
    private static final String BEER_UPC_1 = "678234589234";
    private static final String BEER_UPC_2 = "66782349134";
    private static final String BEER_UPC_3 = "087892346725";

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
       loadBeer();
    }

    private void loadBeer() {
        if(beerRepository.count() == 0){
            beerRepository.save(Beer.builder()
                    .beerName("New beer 1")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(BEER_UPC_1)
                    .price(new BigDecimal("12.45"))
                    .build()
            );

            beerRepository.save(Beer.builder()
                    .beerName("New beer 2")
                    .beerStyle("ALE")
                    .quantityToBrew(130)
                    .minOnHand(5)
                    .upc(BEER_UPC_2)
                    .price(new BigDecimal("23.80"))
                    .build()
            );
            beerRepository.save(Beer.builder()
                    .beerName("New beer 3")
                    .beerStyle("WHEAT")
                    .quantityToBrew(90)
                    .minOnHand(29)
                    .upc(BEER_UPC_3)
                    .price(new BigDecimal("28.17"))
                    .build()
            );
        }

        System.out.println("Count of loaded beer: " + beerRepository.count());
        beerRepository.findAll().forEach(beer -> System.out.println(beer.getId()));
    }
}
