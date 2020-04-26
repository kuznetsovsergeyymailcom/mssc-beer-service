package kss.springframework.msscbeerservice.services.brewing;

import kss.springframework.msscbeerservice.config.JMSConfig;
import kss.springframework.msscbeerservice.domain.Beer;
import kss.springframework.msscbeerservice.repositories.BeerRepository;
import kss.springframework.common.events.NewInventoryEvent;
import kss.springframework.msscbeerservice.services.inventory.BeerInventoryService;
import kss.springframework.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();
        beers.forEach(beer -> {
            Integer onhandInventory = beerInventoryService.getOnhandInventory(beer.getId());

            log.debug("Min on hand" + beer.getMinOnHand());
            log.debug("Inventory is: " + onhandInventory);

            if (beer.getMinOnHand() >= onhandInventory) {
                jmsTemplate.convertAndSend(JMSConfig.INVENTORY_REQUEST_QUEUE,
                        new NewInventoryEvent(beerMapper.beerToBeerDto(beer)));
            }
        });

    }
}
