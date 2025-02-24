DO
$create_user_gastronomie$
BEGIN
        IF EXISTS (
            SELECT FROM pg_catalog.pg_roles WHERE  username = 'gastronomie') THEN
            RAISE NOTICE 'User"gastronomie" already exists. Skipping.';
ELSE
CREATE USER gastronomie LOGIN PASSWORD '123456';
END IF;
END
$create_user_gastronomie$;

GRANT ALL PRIVILEGES ON DATABASE restaurant_management TO gastronomie;
ALTER DEFAULT PRIVILEGES FOR USER gastronomie
    GRANT ALL PRIVILEGES ON TABLES TO gastronomie;
ALTER DEFAULT PRIVILEGES FOR USER gastronomie
    GRANT ALL PRIVILEGES ON SEQUENCES TO gastronomie;
GRANT ALL PRIVILEGES ON SCHEMA public TO gastronomie;
\c restaurant_management gastronomie

