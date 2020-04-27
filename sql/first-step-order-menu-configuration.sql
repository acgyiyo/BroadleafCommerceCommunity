select * from BLC_ADMIN_SECTION;
select * from blc_admin_module;

-- create new module for admin
insert into blc_admin_module (ADMIN_MODULE_ID, NAME, MODULE_KEY, ICON, DISPLAY_ORDER)
values (80, "Order Module", "Ordes", "blc-icon-globe", 800);

-- Create Admin permissions and entity permissions for your new entity
insert into blc_admin_permission (ADMIN_PERMISSION_ID, DESCRIPTION, NAME, PERMISSION_TYPE, IS_FRIENDLY)
values (80, "PERMISSION_ALL_ORDER_CLASS", "PERMISSION_ALL_ORDER_CLASS", "ALL", false);

insert into blc_admin_permission_entity (ADMIN_PERMISSION_ENTITY_ID, CEILING_ENTITY, ADMIN_PERMISSION_ID)
values (80, "org.broadleafcommerce.core.order.domain.Order", 80);

-- Add Role Permission XREF to the Permission
INSERT INTO BLC_ADMIN_PERMISSION_XREF (ADMIN_PERMISSION_ID, CHILD_PERMISSION_ID) 
VALUES (80, 80);

INSERT INTO BLC_ADMIN_ROLE_PERMISSION_XREF (ADMIN_ROLE_ID, ADMIN_PERMISSION_ID) 
VALUES (-1, 80);

-- Mapping sections and permissions
INSERT INTO BLC_ADMIN_SECTION (ADMIN_SECTION_ID, DISPLAY_ORDER, ADMIN_MODULE_ID, NAME, SECTION_KEY, URL, CEILING_ENTITY) 
VALUES (80, 1, 80, "Orders","Orders", "/order", "org.broadleafcommerce.core.order.domain.Order");

INSERT INTO BLC_ADMIN_SEC_PERM_XREF (ADMIN_SECTION_ID, ADMIN_PERMISSION_ID) 
VALUES (80, 80);