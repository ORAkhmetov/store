CREATE TABLE IF NOT EXISTS cart (
    id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    product_id bigint REFERENCES product(id) ON DELETE CASCADE,
    quantity int NOT NULL CHECK (quantity > 0)
)