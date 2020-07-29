package io.github.bael.mscourse.inventory.rest;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class SKURestController {

    @PutMapping("/sku/available")
    public List<SKUShortInfoDTO> requestAvailability(@RequestBody RequestAvailabilityDTO requestAvailabilityDTO) {
        return Collections.emptyList();
    }

}
