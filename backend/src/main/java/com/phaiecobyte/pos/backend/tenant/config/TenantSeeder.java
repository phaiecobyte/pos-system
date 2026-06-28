package com.phaiecobyte.pos.backend.tenant.config;

import com.phaiecobyte.pos.backend.tenant.dto.TenantDto;
import com.phaiecobyte.pos.backend.tenant.mapper.TenantMapper;
import com.phaiecobyte.pos.backend.tenant.model.BusinessType;
import com.phaiecobyte.pos.backend.tenant.model.Tenant;
import com.phaiecobyte.pos.backend.tenant.repository.BusinessTypeRepository;
import com.phaiecobyte.pos.backend.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class TenantSeeder implements CommandLineRunner {
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final BusinessTypeRepository businessTypeRepository;


    @Override
    public void run(String... args) throws Exception {
        seedBusinessTyp();
        seedTenantRestaurant();
        seedTenantCosmetics();
    }

    public void seedTenantRestaurant(){
        UUID tenantId = UUID.fromString("bce46ea6-a62d-4a78-b208-9ed027f342ca");
        var type = businessTypeRepository.findById("RESTAURANT").get();
        Tenant tenant = new Tenant();
        tenant.setId(tenantId);
        tenant.setCode("SHOP-0001");
        tenant.setName("PHL Restaurant");
        tenant.setBusinessType(type);

        tenantRepository.save(tenant);

    }

    public void seedTenantCosmetics(){
        UUID tenantId = UUID.fromString("bce46ea6-a62d-4a78-b208-9ed027f342cb");
        var type = businessTypeRepository.findById("COSMETICS").get();
        Tenant tenant = new Tenant();
        tenant.setId(tenantId);
        tenant.setCode("SHOP-0002");
        tenant.setName("Cosmetics and Beauty Store");
        tenant.setBusinessType(type);

        tenantRepository.save(tenant);

    }

    public void seedTenantRetailer() {
        if (tenantRepository.count() > 0) {
            return;
        }

        for (int i=1;i<=2;i++){
            TenantDto.CreateReq dto = new TenantDto.CreateReq(
                    "SHOP-RETAIL"+i,
                    "RETAIL",
                    "Phai Store"+i,
                    "0965799628",
                    "phaiecobyte@gmail.com",
                    "Phnom Penh",
                    "A",
                    null
            );

            Tenant tenant = tenantMapper.toEntity(dto);
            tenantRepository.save(tenant);
            log.info("Default tenant seeded successfully");
        }

    }

    public void seedBusinessTyp(){
        if(businessTypeRepository.count() > 0 ){
            log.info("=============== No seed business type ==============");
            return;
        }
        log.info("===================== start seed business type ==================");
        List<BusinessType> types = new ArrayList<>();

        types.add(new BusinessType("COSMETICS","Cosmetics and Beauty Store",null));
        types.add(new BusinessType("RETAIL","Retail Store",null));
        types.add(new BusinessType("RESTAURANT","Restaurant",null));
        types.add(new BusinessType("GUESTHOUSE","Guest House",null));
        types.add(new BusinessType("PHARMACY","Pharmarcy",null));

        businessTypeRepository.saveAll(types);
    }


}
