-- =====================================================
-- Schema
-- =====================================================

CREATE SCHEMA IF NOT EXISTS mydb;
SET search_path TO mydb;

-- =====================================================
-- Table: client
-- =====================================================

CREATE TABLE client (
                        id   BIGSERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
);

-- =====================================================
-- Table: chantier
-- =====================================================

CREATE TABLE chantier (
                          id        BIGSERIAL PRIMARY KEY,
                          name      VARCHAR(100) NOT NULL,
                          client_id BIGINT,

                          CONSTRAINT uq_chantier_client_name
                              UNIQUE (client_id, name),

                          CONSTRAINT fk_chantier_client
                              FOREIGN KEY (client_id)
                                  REFERENCES client (id)
                                  ON DELETE SET NULL
                                  ON UPDATE CASCADE
);

CREATE INDEX idx_chantier_client_id
    ON chantier (client_id);

-- =====================================================
-- Table: intervention
-- =====================================================

CREATE TABLE intervention (
                              id          BIGSERIAL PRIMARY KEY,
                              chantier_id BIGINT NOT NULL,

                              CONSTRAINT fk_intervention_chantier
                                  FOREIGN KEY (chantier_id)
                                      REFERENCES chantier (id)
                                      ON DELETE CASCADE
                                      ON UPDATE CASCADE
);

CREATE INDEX idx_intervention_chantier_id
    ON intervention (chantier_id);
