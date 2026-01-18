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
    scheduled_tasks,
    weeks,
    typologies
CASCADE
;

-- =====================================================
-- Table: users // to add : created_at, last_connection
-- =====================================================
CREATE TABLE users (
    user_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    is_admin BOOLEAN NOT NULL DEFAULT false
);

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
    UNIQUE (user_id, name)
);
CREATE INDEX IF NOT EXISTS idx_clients_user_id ON clients (user_id);

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
    UNIQUE (user_id, name),

    CONSTRAINT fk_chantier_client
        FOREIGN KEY (client_id)
            REFERENCES clients (client_id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_chantiers_client_id ON chantiers (client_id);
CREATE INDEX IF NOT EXISTS idx_chantiers_user_id ON chantiers (user_id);

-- =====================================================
-- Table: interventions // add created_at and last_updated
-- =====================================================

CREATE TABLE interventions (
    intervention_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    chantier_id BIGINT NOT NULL,
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

CREATE INDEX IF NOT EXISTS idx_interventions_chantier_id ON interventions (chantier_id);
CREATE INDEX IF NOT EXISTS idx_interventions_user_id ON interventions (user_id);

-- =====================================================
-- Table: weeks // maybe change PK ALWAYS to BY DEFAULT
--    // maybe year field too
-- =====================================================
CREATE TABLE weeks (
    week_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    week_number INT NOT NULL,
    is_holiday BOOLEAN NOT NULL DEFAULT false,
    year_number INT NOT NULL,
    -- BELONGS TO USER
    CONSTRAINT fk_week_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    UNIQUE (user_id, week_number, year_number)
);
CREATE INDEX IF NOT EXISTS idx_weeks_user_id ON weeks (user_id);
CREATE INDEX IF NOT EXISTS idx_weeks_week_number ON weeks (week_number);
CREATE INDEX IF NOT EXISTS idx_weeks_year_number ON weeks (year_number);

-- =====================================================
-- Table: scheduled_tasks // no unique constraint
-- =====================================================
CREATE TABLE scheduled_tasks (
    scheduled_task_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    intervention_id BIGINT NOT NULL,
    week_id INT NOT NULL,
    typology_id BIGINT NOT NULL,
    description VARCHAR(255),
    -- BELONGS TO USER
    CONSTRAINT fk_scheduled_tasks_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    CONSTRAINT fk_intervention_weeks_intervention
        FOREIGN KEY (intervention_id)
            REFERENCES interventions (intervention_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_intervention_weeks_week
        FOREIGN KEY (week_id)
            REFERENCES weeks (week_id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_scheduled_tasks_user_id ON scheduled_tasks (user_id);
CREATE INDEX IF NOT EXISTS idx_scheduled_tasks_intervention_id ON scheduled_tasks (intervention_id);
CREATE INDEX IF NOT EXISTS idx_scheduled_tasks_week_id ON scheduled_tasks (week_id);

-- =====================================================
-- Table: typologies
-- =====================================================
CREATE TABLE typologies (
    typology_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(20) NOT NULL,
    -- BELONGS TO USER
    CONSTRAINT fk_typologies_user
        FOREIGN KEY (user_id)
            REFERENCES users (user_id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    UNIQUE (user_id, name)
);