package com.phaiecobyte.pos.backend.identity.config;

import com.phaiecobyte.pos.backend.identity.dto.CreateUserReq;
import com.phaiecobyte.pos.backend.identity.mapper.UserMapper;
import com.phaiecobyte.pos.backend.identity.model.Permission;
import com.phaiecobyte.pos.backend.identity.model.Role;
import com.phaiecobyte.pos.backend.identity.model.User;
import com.phaiecobyte.pos.backend.identity.repository.PermissionRepository;
import com.phaiecobyte.pos.backend.identity.repository.RoleRepository;
import com.phaiecobyte.pos.backend.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.phaiecobyte.pos.backend.core.common.base.Constant.tenantCosmetics;
import static com.phaiecobyte.pos.backend.core.common.base.Constant.tenantRestaurant;

@Component
@RequiredArgsConstructor
@Slf4j
public class IdentitySeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionRepository permissionRepository;
    private final UserMapper userMapper;


    @Override
    public void run(String... args) throws Exception {
        seedPermission();
        seedRole();

        seedSuperAdminUser();

        seedTenantCosmeticsAdmin();
        seedTenantCosmeticsCashierUser();

        seedTenantRestaurantAdmin();
        seedTenantRestaurantCashierUser();
    }

    public void seedPermission(){
        List<Permission> perms = new ArrayList<>();

        perms.add(new Permission("USER_VIEW","View users", LocalDateTime.now()));
        perms.add(new Permission("USER_CREATE","Create users",LocalDateTime.now()));
        perms.add(new Permission("USER_UPDATE","Update users",LocalDateTime.now()));
        perms.add(new Permission("USER_DELETE","Delete users", LocalDateTime.now()));

        perms.add(new Permission("ROLE_VIEW","View users", LocalDateTime.now()));
        perms.add(new Permission("ROLE_CREATE","Create users",LocalDateTime.now()));
        perms.add(new Permission("ROLE_UPDATE","Update users",LocalDateTime.now()));
        perms.add(new Permission("ROLE_DELETE","Delete users", LocalDateTime.now()));

        perms.add(new Permission("TENANT_VIEW","View users", LocalDateTime.now()));
        perms.add(new Permission("TENANT_CREATE","Create users",LocalDateTime.now()));
        perms.add(new Permission("TENANT_UPDATE","Update users",LocalDateTime.now()));
        perms.add(new Permission("TENANT_DELETE","Delete users", LocalDateTime.now()));

        log.info("======================== successfully seed default permissions ====================");
        permissionRepository.saveAll(perms);

    }

    public void seedRole(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(null,"SUPER_ADMIN","","Full access to the entire platform",true,null));
        roles.add(new Role(null,"TENANT_ADMIN","","Manage tenant configuration, users, and roles",true,null));
        roles.add(new Role(null,"CASHIER","","Create sales transactions and process payments",true,null));

        log.info("======================== successfully seed default roles =========================");
        roleRepository.saveAll(roles);
    }

    public void seedSuperAdminUser() {

        Role cashierRole = roleRepository.findByCode("SUPER_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role SUPER_ADMIN not found"));

        List<User> users = new ArrayList<>();


            CreateUserReq req = new CreateUserReq();
            req.setUsername("SUPER_ADMIN");
            req.setPassword("123456");
            req.setFirstName("FirstName");
            req.setLastName("LastName");
            req.setEmail("super_admin@email.com");
            req.setPhone("02344455");
            req.setRoleId(Collections.singleton(cashierRole.getId()));

            User user = userMapper.toEntity(req);
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setActive(true);

            users.add(user);

        userRepository.saveAll(users);

        log.info("======================= Successfully seeded {} users ========================", users.size());
    }

    /*
        -------------------------------------------------------
                                SHOP A
        -------------------------------------------------------
     */
    public void seedTenantCosmeticsAdmin() {
        Role role = roleRepository.findByCode("TENANT_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role TENANT_ADMIN not found"));

        List<User> users = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {

            CreateUserReq req = new CreateUserReq();
            req.setTenantId(tenantCosmetics);
            req.setUsername("TENANT_ADMIN" + i);
            req.setPassword("123456");
            req.setFirstName("FirstName" + i);
            req.setLastName("LastName" + i);
            req.setEmail("tenant_admin" + i + "@email.com");
            req.setPhone("03344455" + i);
            req.setRoleId(Collections.singleton(role.getId()));

            User user = userMapper.toEntity(req);

            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setActive(true);

            users.add(user);
        }

        userRepository.saveAll(users);

        log.info("========================== Successfully seeded {} TENANT_ADMIN users ========================", users.size());
    }
    public void seedTenantCosmeticsCashierUser() {

        Role cashierRole = roleRepository.findByCode("CASHIER")
                .orElseThrow(() -> new RuntimeException("Role CASHIER not found"));

        List<User> users = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {

            CreateUserReq req = new CreateUserReq();
            req.setTenantId(tenantCosmetics);
            req.setUsername("CASHIER" + i);
            req.setPassword("123456");
            req.setFirstName("FirstName" + i);
            req.setLastName("LastName" + i);
            req.setEmail("cashier" + i + "@email.com");
            req.setPhone("04344455" + i);
            req.setRoleId(Collections.singleton(cashierRole.getId()));

            User user = userMapper.toEntity(req);

            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setActive(true);

            users.add(user);
        }

        userRepository.saveAll(users);

        log.info("Successfully seeded {} CASHIER users", users.size());
    }

    /*
        --------------------------------------------------
                        SHOP B
        --------------------------------------------------
     */
    public void seedTenantRestaurantAdmin() {
        Role cashierRole = roleRepository.findByCode("TENANT_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role TENANT_ADMIN not found"));

        List<User> users = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {

            CreateUserReq req = new CreateUserReq();
            req.setTenantId(tenantRestaurant);
            req.setUsername("TENANT_ADMIN" + i);
            req.setPassword("123456");
            req.setFirstName("FirstName" + i);
            req.setLastName("LastName" + i);
            req.setEmail("tenant_admin" + i + "@email.com");
            req.setPhone("03344455" + i);
            req.setRoleId(Collections.singleton(cashierRole.getId()));

            User user = userMapper.toEntity(req);


            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setActive(true);

            users.add(user);
        }

        userRepository.saveAll(users);

        log.info("========================== Successfully seeded {} TENANT_ADMIN users ========================", users.size());
    }
    public void seedTenantRestaurantCashierUser() {

        Role cashierRole = roleRepository.findByCode("CASHIER")
                .orElseThrow(() -> new RuntimeException("Role CASHIER not found"));

        List<User> users = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {

            CreateUserReq req = new CreateUserReq();
            req.setTenantId(tenantRestaurant);
            req.setUsername("CASHIER" + i);
            req.setPassword("123456");
            req.setFirstName("FirstName" + i);
            req.setLastName("LastName" + i);
            req.setEmail("cashier" + i + "@email.com");
            req.setPhone("04344455" + i);
            req.setRoleId(Collections.singleton(cashierRole.getId()));

            User user = userMapper.toEntity(req);


            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setActive(true);

            users.add(user);
        }

        userRepository.saveAll(users);

        log.info("Successfully seeded {} CASHIER users", users.size());
    }
}