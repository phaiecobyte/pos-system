package com.phaiecobyte.pos.backend.tenant.config;

import com.phaiecobyte.pos.backend.tenant.model.BusinessType;
import com.phaiecobyte.pos.backend.tenant.model.Tenant;
import com.phaiecobyte.pos.backend.tenant.repository.BusinessTypeRepository;
import com.phaiecobyte.pos.backend.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TenantSeeder implements CommandLineRunner {
    private final TenantRepository tenantRepository;
    private final BusinessTypeRepository businessTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        seedBusinessType();
        seedTenant();
    }

    public void seedBusinessType() {

        if (businessTypeRepository.count() > 0) {
            return;
        }

        List<BusinessType> businessTypes = List.of(
                new BusinessType("RETAIL", "Retail Store"),
                new BusinessType("RESTAURANT", "Restaurant"),
                new BusinessType("CAFE", "Cafe"),
                new BusinessType("PHARMACY", "Pharmacy"),
                new BusinessType("SALON", "Beauty Salon"),
                new BusinessType("GROCERY", "Grocery Store"),
                new BusinessType("SUPERMARKET", "Supermarket"),
                new BusinessType("MINIMART", "Mini Mart"),
                new BusinessType("CONVENIENCE", "Convenience Store"),
                new BusinessType("WHOLESALE", "Wholesale Business")
        );

        businessTypeRepository.saveAll(businessTypes);

        log.info("Business types seeded successfully");
    }

    public void seedTenant() {

        if (tenantRepository.count() > 0) {
            return;
        }

        for (int i=0;i<=100;i++){
            BusinessType retailType = businessTypeRepository
                    .findByCode("RETAIL")
                    .orElseThrow(() -> new RuntimeException("Business type RETAIL not found"));

            Tenant tenant = Tenant.builder()
                    .code("SHOP-"+i)
                    .name("Store "+i)
                    .businessType(retailType)
                    .phone("0965799628")
                    .email("phaiecobyte@gmail.com")
                    .address("Phnom Penh")
                    .status("A")
                    .createdAt(LocalDateTime.now())
                    .createdBy("SYS")
                    .build();

            tenantRepository.save(tenant);

            log.info("Default tenant seeded successfully");
        }

    }


}
