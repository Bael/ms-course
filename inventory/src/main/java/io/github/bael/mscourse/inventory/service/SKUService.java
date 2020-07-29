package io.github.bael.mscourse.inventory.service;

import io.github.bael.mscourse.inventory.api.SKUApi;
import io.github.bael.mscourse.inventory.data.SKURepository;
import io.github.bael.mscourse.inventory.data.SchedulePeriodRepository;
import io.github.bael.mscourse.inventory.domain.RentPeriod;
import io.github.bael.mscourse.inventory.entity.SKU;
import io.github.bael.mscourse.inventory.entity.SKUStatus;
import io.github.bael.mscourse.inventory.entity.SchedulePeriod;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SKUService implements SKUApi {

    private final SchedulePeriodRepository schedulePeriodRepository;
    private final SKURepository skuRepository;

    @Override
    public List<String> getFreeSKU(String productNumber, RentPeriod rentPeriod) {
        return skuRepository.getAllSkuListByProductNumber(productNumber)
                .stream()
                .filter(sku -> SKUStatus.AVAILABLE == sku.getStatus())
                .filter(sku -> isFree(sku, rentPeriod))
                .map(SKU::getCode)
                .collect(Collectors.toList());

    }

    /**
     * Не занят ли искомый артикул в указанном периоде?
     *
     * @param sku
     * @param rentPeriod
     * @return
     */
    private boolean isFree(SKU sku, RentPeriod rentPeriod) {
        return schedulePeriodRepository.findAllByItem(sku)
                .stream()
                .anyMatch(period -> rentPeriod.intersects(RentPeriod.of(period.getPeriodStart(), period.getPeriodFinish())));
    }

    @Override
    public boolean isAvailable(String sku, RentPeriod rentPeriod) {
        return isFree(getItemByCode(sku), rentPeriod);
    }

    private SKU getItemByCode(String code) {
        SKU item = skuRepository.findByCode(code).orElseThrow(() ->
                new ObjectNotFoundException("SKU not found:", code));
        return item;
    }


    @Override
    public void reserve(String skuCode, RentPeriod rentPeriod) {
        SKU item = getItemByCode(skuCode);
        if (item.getStatus() != SKUStatus.AVAILABLE) {
            throw new IllegalStateException("Wrong state of unit!" + item.getStatus());
        }

        if (!isFree(item, rentPeriod)) {
            throw new IllegalStateException("Unit is not free for period! Code:" + item.getCode() + " period: " + rentPeriod.getPeriod());
        }
        item.setStatus(SKUStatus.RESERVED);
        SchedulePeriod period = new SchedulePeriod();
        period.setItem(item);
        period.setPeriodStart(rentPeriod.getPeriod().lowerEndpoint());
        period.setPeriodFinish(rentPeriod.getPeriod().upperEndpoint());

        schedulePeriodRepository.save(period);
        skuRepository.save(item);

    }

    @Override
    public void ship(String skuCode) {
        SKU item = getItemByCode(skuCode);
        if (item.getStatus() != SKUStatus.RESERVED) {
            throw new IllegalStateException("Wrong state of unit!" + item.getStatus());
        }
        item.setStatus(SKUStatus.OUT);
        skuRepository.save(item);
    }

    @Override
    public void accept(String skuCode) {
        SKU item = getItemByCode(skuCode);
        if (item.getStatus() != SKUStatus.OUT) {
            throw new IllegalStateException("Wrong state of unit!" + item.getStatus());
        }
        item.setStatus(SKUStatus.AVAILABLE);
        skuRepository.save(item);
    }
}
