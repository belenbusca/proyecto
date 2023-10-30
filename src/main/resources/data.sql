INSERT INTO CLIENT (client_id, client_name, client_lastname, client_docnumber) values
(1, 'Juan'   , 'Perez' , 12345678),
(2, 'Azucena', 'Garcia', 23456789),
(3, 'Serafin', 'Lopez' , 34567890);

INSERT INTO PRODUCT (prod_id, prod_description, prod_code, prod_stock, prod_price) values
(1, 'Coca Cola' , '000', 200, 472.00),
(2, 'Chocolinas', '001', 320, 418.41),
(3, 'Leche'     , '002', 600, 506.05);

INSERT INTO INVOICE (inv_id, inv_client_id, inv_created_at, inv_total) values
(1, 3, '2023-09-19 14:30:01', 1834.41),
(2, 1, '2023-09-19 14:32:36', 3036.30);

INSERT INTO INVOICE_DETAIL (inv_detail_id, inv_detail_inv_id, inv_detail_amount, inv_detail_prod_id, inv_detail_price) values
(1, 1, 3, 1, 1416.00), --1416 -- factrua 1, compra 3 cocas
(2, 1, 1, 2, 418.41), -- factura 1, compra 1 chocolina
(3, 2, 6, 3, 3036.30); -- factura 2, compra 6 leches