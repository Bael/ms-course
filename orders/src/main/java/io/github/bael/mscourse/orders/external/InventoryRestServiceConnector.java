package io.github.bael.mscourse.orders.external;

import io.github.bael.mscourse.shopdto.v1.InventoryReserveRequest;
import io.github.bael.mscourse.shopdto.v1.InventoryReserveResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@Configuration
public class InventoryRestServiceConnector {

    private final RestTemplate restTemplate;
    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;

    public InventoryRestServiceConnector(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InventoryReserveResponse reserveProducts(InventoryReserveRequest request) {
        URI uri = URI.create(inventoryServiceUrl + "/inventory/reserve");
        HttpEntity<InventoryReserveRequest> orderRequestHttpEntity = new HttpEntity<>(request);
        ResponseEntity<InventoryReserveResponse> exchange = restTemplate.exchange(uri, HttpMethod.POST, orderRequestHttpEntity, InventoryReserveResponse.class);
        return exchange.getBody();
    }
}
