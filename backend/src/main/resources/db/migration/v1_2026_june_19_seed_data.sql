INSERT INTO t_identity_permission (id, name, description)
VALUES
    (gen_random_uuid(), 'USER_VIEW', 'View users'),
    (gen_random_uuid(), 'USER_CREATE', 'Create users'),
    (gen_random_uuid(), 'USER_UPDATE', 'Update users'),
    (gen_random_uuid(), 'USER_DELETE', 'Delete users'),

    (gen_random_uuid(), 'ROLE_VIEW', 'View roles'),
    (gen_random_uuid(), 'ROLE_CREATE', 'Create roles'),
    (gen_random_uuid(), 'ROLE_UPDATE', 'Update roles'),
    (gen_random_uuid(), 'ROLE_DELETE', 'Delete roles'),

    (gen_random_uuid(), 'TENANT_VIEW', 'View tenants'),
    (gen_random_uuid(), 'TENANT_CREATE', 'Create tenants'),
    (gen_random_uuid(), 'TENANT_UPDATE', 'Update tenants'),
    (gen_random_uuid(), 'TENANT_DELETE', 'Delete tenants');