insert into ciudad values (1, "Armenia");
insert into ciudad values (2, "Pereira");
insert into ciudad values (3, "Medellin");

insert into administrador values ("111", "admin@gmail.com", "Administrador", "123", "Admin");

insert into usuario values ("123", "mario@gmail.com", "Mario", "12345","michelleA", 1);
insert into usuario values ("126", "carlos@gmail.com","Carlos", "12348","carlosI", 2);
insert into usuario values ("125", "sebas@gmail.com", "Sebastian", "12347","sebastiaD", 3);
insert into usuario values ("111", "admin@gmai.com", "Administrador", "123","Admin", 1);

insert into usuario_telefonos values ("123", "3023891890");
insert into usuario_telefonos values ("126", "3020202900");
insert into usuario_telefonos values ("125", "3023586691");


insert into producto values (5, "Telefono Iphone 7 como nuevo", 0.0, "2021/10/31", "Iphone 7", 800000.0, 0, 1, "123");
insert into producto values (6, "Licuadora Imusa nuevo", 0.1, "2021/11/25", "Licuadora", 150000.0, 10, 2, "125");
insert into producto values (3, "Computador accer nuevo", 0.0, "2021/10/19", "Computador accer", 1800000.0, 5, 3, "126");
insert into producto values (4, "Celular en estado 10/10", 0.06, "2021/10/19", "IPhone 5", 1000000.0, 5, 3, "123");

insert into categoria values (0, "Ropa");
insert into categoria values (1, "Souvenir");
insert into categoria values (2, "Artesanias");

insert into categoria_lista_productos values (0, 1);
insert into categoria_lista_productos values (1, 1);
insert into categoria_lista_productos values (1, 2);
insert into categoria_lista_productos values (2, 3);
insert into categoria_lista_productos values (2, 4);

insert into administrador values ("132", "gloria@gmail.com", "Gloria", "12354", "Glo");
insert into administrador values ("134", "camilo@gmail.com", "Camilo", "12356", "Cam");
insert into administrador values ("135", "cristian@gmail.com", "Cristian", "12357", "Cris");

insert into chat values (11, 1, "123");
insert into chat values (22, 2, "123");
insert into chat values (33, 3, "123");

insert into mensaje values (313, "Mario", "2021/11/10", "Que lindo", 11);
insert into mensaje values (314, "Tatiana", "2021/11/13", "Muy práctico", 11);
insert into mensaje values (315, "Sebastian", "2021/10/10", "Excelente", 33);

insert into comentario values (13, 4.5, "2021/11/10", "Que lindo", "Gracias", 1, "124");
insert into comentario values (14, 4.0, "2021/11/13", "Muy práctico", "Así es", 2, "126");
insert into comentario values (15, 5.0, "2021/10/10", "Excelente", "Gracias", 3, "126" );
insert into comentario values (16, 3.0, "2021/11/13", "Casi no me gustó", "Así es", 4, "124");

insert into compra values (3020, "2021/11/10", "Efectivo", "123");
insert into compra values (3040, "2021/10/14", "Tarjeta crédito", "123");
insert into compra values (3050, "2021/11/23", "Tarjeta crédito", "126");

insert into detalle_compra values (3122, 800000.0, 1, 3020, 1);
insert into detalle_compra values (3123, 800000.0, 2, 3020, 1);
insert into detalle_compra values (3124, 150000.0, 3, 3040, 2);
insert into detalle_compra values (3125, 1800000.0, 1, 3050, 3);

insert into producto_imagenes values (1, "ruta1/img1/.jpg" , "21");
insert into producto_imagenes values (1, "ruta2/img2/.jpg" , "22");
insert into producto_imagenes values (2, "ruta3/img3/.jpg" , "23");
insert into producto_imagenes values (3, "ruta4/img4/.jpg" , "24");

insert into usuario_productos_favoritos values ("123", 1);
insert into usuario_productos_favoritos values ("123", 2);
insert into usuario_productos_favoritos values ("124", 1);

insert into subasta values (11, "2021/10/29", 1);
insert into subasta values (12, "2021/10/28", 1);
insert into subasta values (13, "2021/12/08", 2);
insert into subasta values (14, "2021/11/12", 2);
insert into subasta values (15, "2021/11/12", 3);

insert into detalle_subasta values (342, "2021/11/07", 60000.0, 12, "123");
insert into detalle_subasta values (343, "2021/11/09", 500000.0, 12, "126");
insert into detalle_subasta values (344, "2021/11/09", 40000.0, 12, "125");
insert into detalle_subasta values (346, "2021/11/07", 100000.0, 13, "124");
insert into detalle_subasta values (348, "2021/11/11", 900000.0, 14, "124");