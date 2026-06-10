
CREATE INDEX idx_products_category_stock
    ON product(category_id, stock_qty);

CREATE INDEX idx_products_supplier
    ON product(supplier_id);