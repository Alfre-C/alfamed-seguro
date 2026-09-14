create table coberturas(
	id serial primary key,
	cedula varchar(20) not null,
	servicio varchar(50) not null,
	cobertura int not null
);

insert into coberturas(cedula, servicio, cobertura) values 
('1234567','Consulta',50000),
('1235467','Hemograma',100000),
('1234567','Radiografia',150000);