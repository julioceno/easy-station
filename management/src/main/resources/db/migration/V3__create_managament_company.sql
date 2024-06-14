CREATE TABLE managements (
    id TEXT NOT NULL,
    price_hour DOUBLE NOT NULL,

    company_id TEXT NOT NULL,
    created_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "managements_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "fk_company" FOREIGN KEY ("company_id") REFERENCES companies("id")
    CONSTRAINT "unique_company" UNIQUE (company_id)
)