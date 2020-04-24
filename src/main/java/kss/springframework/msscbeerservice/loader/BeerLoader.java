package kss.springframework.msscbeerservice.loader;

import kss.springframework.msscbeerservice.domain.Beer;
import kss.springframework.msscbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BeerLoader implements CommandLineRunner {
    public static final String BEER_UPC_1 = "678234589234";
    public static final String BEER_UPC_2 = "66782349134";
    public static final String BEER_UPC_3 = "087892346725";
    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(beerRepository.count() == 0){
            loadBeer();
        }
    }

    private void loadBeer() {

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


        System.out.println("Count of loaded beer: " + beerRepository.count());
        beerRepository.findAll().forEach(beer -> System.out.println(beer.getUpc()));
    }
}
