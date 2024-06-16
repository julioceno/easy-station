CREATE TABLE courtyards (
    id TEXT NOT NULL,
    name TEXT NOT NULL,
    maxCars INT NOT NULL,

    company_id TEXT NOT NULL,
    created_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "courtyards_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "fk_company" FOREIGN KEY ("company_id") REFERENCES companies("id"),
    CONSTRAINT "unique_company_courtyard" UNIQUE (company_id)
)