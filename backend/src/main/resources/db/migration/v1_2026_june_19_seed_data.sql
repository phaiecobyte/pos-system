------------------------------------------------------------
--            1. SEED BUSINESS TYPE
------------------------------------------------------------

INSERT INTO t_core_business_type (id, description)
VALUES
('RETAIL', 'Retail Store'),
('WHOLESALE', 'Wholesale Business'),
('RESTAURANT', 'Restaurant'),
('CAFE', 'Cafe'),
('BAKERY', 'Bakery'),
('PHARMACY', 'Pharmacy'),
('SUPERMARKET', 'Supermarket'),
('MINIMART', 'Minimart'),
('GROCERY', 'Grocery Store'),
('CONVENIENCE_STORE', 'Convenience Store'),
('ELECTRONICS', 'Electronics Store'),
('FASHION', 'Fashion and Clothing Store'),
('COSMETICS', 'Cosmetics and Beauty Store'),
('BOOKSTORE', 'Bookstore'),
('HARDWARE', 'Hardware Store'),
('FURNITURE', 'Furniture Store'),
('JEWELRY', 'Jewelry Store'),
('SPORTS', 'Sports Equipment Store'),
('PET_SHOP', 'Pet Shop'),
('AUTO_PARTS', 'Auto Parts Store'),
('SERVICE', 'Service Business'),
('SALON', 'Beauty Salon'),
('BARBERSHOP', 'Barbershop'),
('SPA', 'Spa and Wellness Center'),
('CLINIC', 'Clinic'),
('WORKSHOP', 'Repair Workshop'),
('HOTEL', 'Hotel'),
('GUESTHOUSE', 'Guesthouse'),
('AGRICULTURE', 'Agriculture Business'),
('MANUFACTURING', 'Manufacturing Business');

------------------------------------------------------------
--           2. SEED PERMISSION
------------------------------------------------------------
INSERT INTO t_identity_permission (id, description, created_at)
VALUES
    ('USER_VIEW', 'View users',CURRENT_TIMESTAMP),
    ('USER_CREATE', 'Create users',CURRENT_TIMESTAMP),
    ('USER_UPDATE', 'Update users',CURRENT_TIMESTAMP),
    ('USER_DELETE', 'Delete users',CURRENT_TIMESTAMP),
    ('ROLE_VIEW', 'View roles',CURRENT_TIMESTAMP),
    ('ROLE_CREATE', 'Create roles',CURRENT_TIMESTAMP),
    ('ROLE_UPDATE', 'Update roles',CURRENT_TIMESTAMP),
    ('ROLE_DELETE', 'Delete roles',CURRENT_TIMESTAMP),
    ('TENANT_VIEW', 'View tenants',CURRENT_TIMESTAMP),
    ('TENANT_CREATE', 'Create tenants',CURRENT_TIMESTAMP),
    ('TENANT_UPDATE', 'Update tenants',CURRENT_TIMESTAMP),
    ('TENANT_DELETE', 'Delete tenants',CURRENT_TIMESTAMP);

------------------------------------------------------------
--           3. SEED ROLE
------------------------------------------------------------
INSERT INTO t_identity_role (id,tenant_id,code,name,description,is_system)
VALUES
    (gen_random_uuid(),NULL,'SUPER_ADMIN','Super Administrator','Full access to the entire platform',TRUE),
    (gen_random_uuid(),NULL,'TENANT_ADMIN','Tenant Administrator','Manage tenant configuration, users, and roles',TRUE),
    (gen_random_uuid(),NULL,'MANAGER','Manager','Manage daily business operations',TRUE),
    (gen_random_uuid(),NULL,'CASHIER','Cashier','Create sales transactions and process payments',TRUE),
    (gen_random_uuid(),NULL,'INVENTORY_CLERK','Inventory Clerk','Manage products, stock movements, and inventory',TRUE),
    (gen_random_uuid(),NULL,'ACCOUNTANT','Accountant','Access financial reports and accounting functions',TRUE);