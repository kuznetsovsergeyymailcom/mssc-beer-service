package kss.springframework.msscbeerservice.services.brewing;

import kss.springframework.msscbeerservice.config.JMSConfig;
import kss.springframework.msscbeerservice.domain.Beer;
import kss.brewery.model.events.BrewBeerEvent;
import kss.brewery.model.events.NewInventoryEvent;
import kss.springframework.msscbeerservice.repositories.BeerRepository;
import kss.brewery.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;
    @Transactional
    @JmsListener(destination = JMSConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event){
        BeerDto beerDto = event.getBeerDto();
        Beer beer = beerRepository.getOne(beerDto.getId());
        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        log.debug("Brewed beer " + beer.getMinOnHand() + " QOH " + beerDto.getQuantityOnHand());

        NewInventoryEvent inventoryEvent = new NewInventoryEvent(beerDto);
        jmsTemplate.convertAndSend(JMSConfig.INVENTORY_REQUEST_QUEUE, inventoryEvent);

    }
}
