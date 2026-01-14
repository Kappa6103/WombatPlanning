-- =====================================================
-- Schema
-- =====================================================

CREATE SCHEMA IF NOT EXISTS wombat_planning;
SET search_path TO wombat_planning;

-- Drop table for dev and testing
DROP TABLE IF EXISTS
    users,
    clients,
    chantiers,
    interventions,
    weeks,
    intervention_weeks
;

-- =====================================================
-- Table: users // to add : created_at, last_connection
-- =====================================================
CREATE TABLE users (
    user_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);
CREATE INDEX IF NOT EXISTS ON users (email);

-- =====================================================
-- Table: clients // to add : created_at, last_update
-- =====================================================
CREATE TABLE clients (
    client_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,

    -- BELONGS TO USER
    CONSTRAINT fk_client_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    -- UNIQUENESS CONSTRAINT
    CONSTRAINT UNIQUE (user_id, name)
);
CREATE INDEX IF NOT EXISTS
    ON clients (user_id);

-- =====================================================
-- Table: chantiers // to add : created_at, last_update
-- =====================================================

CREATE TABLE chantiers (
    chantier_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    client_id BIGINT NOT NULL,
    name VARCHAR(20) NOT NULL,

    -- BELONGS TO USER
    CONSTRAINT fk_chantier_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    -- UNIQUENESS CONSTRAINT
    CONSTRAINT UNIQUE (user_id, name),

    CONSTRAINT fk_chantier_client
        FOREIGN KEY (client_id)
            REFERENCES clients (client_id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);

CREATE INDEX IF NOT EXISTS ON chantiers (client_id);
CREATE INDEX IF NOT EXISTS ON chantiers (user_id);

-- =====================================================
-- Table: interventions // add created_at and last_updated
-- =====================================================

CREATE TABLE interventions (
    intervention_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    chantier_id BIGINT NOT NULL,
    description VARCHAR(255),
    -- BELONGS TO USER
    CONSTRAINT fk_intervention_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,

    CONSTRAINT fk_intervention_chantier
        FOREIGN KEY (chantier_id)
            REFERENCES chantiers (chantier_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE INDEX IF NOT EXISTS ON interventions (chantier_id);
CREATE INDEX IF NOT EXISTS ON interventions (user_id);

-- =====================================================
-- Table: weeks
-- =====================================================
CREATE TABLE weeks (
    week_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    working_days INT NOT NULL,
    -- BELONGS TO USER
    CONSTRAINT fk_week_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);
CREATE INDEX IF NOT EXISTS ON weeks (user_id)

-- =====================================================
-- Table: intervention_weeks LINK TABLE
-- =====================================================
CREATE TABLE intervention_weeks (
    intervention_id BIGINT NOT NULL,
    week_id BIGINT NOT NULL,

    PRIMARY KEY (intervention_id, week_id),

    CONSTRAINT fk_intervention_weeks_intervention_id
        FOREIGN KEY (intervention_id)
            REFERENCES interventions (intervention_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

    CONSTRAINT fk_intervention_weeks_week_id
        FOREIGN KEY (week_id)
            REFERENCES weeks (week_id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_int_weeks_week_id ON intervention_weeks (week_id);

