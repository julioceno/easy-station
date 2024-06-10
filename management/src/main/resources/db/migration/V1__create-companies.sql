CREATE TABLE companies (
    id TEXT NOT NULL,
    name SERIAL NOT NULL,
    created_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP(3),

  CONSTRAINT "companies_pkey" PRIMARY KEY ("id")
)