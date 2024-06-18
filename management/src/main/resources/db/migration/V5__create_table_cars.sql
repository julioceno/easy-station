CREATE TABLE cars (
    id TEXT NOT NULL,
    owner_name TEXT NOT NULL,
    plate TEXT NOT NULL,
    hour_price FLOAT NOT NULL,

    courtyard_id TEXT NOT NULL,
    created_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(3) DEFAULT NULL,

    CONSTRAINT "cars_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "fk_company" FOREIGN KEY ("courtyard_id") REFERENCES courtyards("id")
)