DO
$create_role_gastronomie$
BEGIN
        IF EXISTS (
            SELECT FROM pg_catalog.pg_roles WHERE  rolname = 'gastronomie') THEN
            RAISE NOTICE 'Role "gastronomie" already exists. Skipping.';
ELSE
CREATE ROLE gastronomie LOGIN PASSWORD '123456';
END IF;
END
$create_role_gastronomie$;

GRANT ALL PRIVILEGES ON DATABASE restaurant_management TO gastronomie;
ALTER DEFAULT PRIVILEGES FOR ROLE gastronomie
    GRANT ALL PRIVILEGES ON TABLES TO gastronomie;
ALTER DEFAULT PRIVILEGES FOR ROLE gastronomie
    GRANT ALL PRIVILEGES ON SEQUENCES TO gastronomie;
GRANT ALL PRIVILEGES ON SCHEMA public TO gastronomie;
\c restaurant_management gastronomie

