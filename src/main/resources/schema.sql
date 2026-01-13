-- =====================================================
-- Schema
-- =====================================================

CREATE SCHEMA IF NOT EXISTS wombat_planning;
SET search_path TO wombat_planning;

-- Drop table for dev and testing
DROP TABLE IF EXISTS
    clients,
    chantiers,
    interventions,
    weeks,
    intervention_weeks;

-- =====================================================
-- Table: clients
-- =====================================================
CREATE TABLE clients (
    client_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- =====================================================
-- Table: chantiers
-- =====================================================

CREATE TABLE chantiers (
    chantier_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL,
    client_id INT,

    -- =====================================================
    -- This is a composite unique constraint. It means that for a specific client,
    -- you cannot have two projects (chantiers) with the same name.
    -- However, Client A and Client B can both have a project called "Renovation".
    -- =====================================================
    -- CONSTRAINT uq_chantier_client_name
    -- UNIQUE (client_id, name),

    CONSTRAINT fk_chantier_client
        FOREIGN KEY (client_id)
            REFERENCES clients (client_id)
            ON DELETE SET NULL
            ON UPDATE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_chantier_client_id
    ON chantiers (client_id);

-- =====================================================
-- Table: interventions
-- =====================================================

CREATE TABLE interventions (
    intervention_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    chantier_id INT NOT NULL,
    description VARCHAR(255),

    CONSTRAINT fk_intervention_chantier
        FOREIGN KEY (chantier_id)
            REFERENCES chantiers (chantier_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_intervention_chantier_id
    ON interventions (chantier_id);

-- =====================================================
-- Table: weeks
-- // maybe i need to set the id manually
-- =====================================================
CREATE TABLE weeks (
    week_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    working_days INT NOT NULL
);

-- =====================================================
-- Table: intervention_weeks LINK TABLE
-- =====================================================
CREATE TABLE intervention_weeks (
    intervention_id INT NOT NULL,
    week_id INT NOT NULL,

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

CREATE INDEX IF NOT EXISTS idx_intervention_weeks_week_id
    ON intervention_weeks (week_id);
