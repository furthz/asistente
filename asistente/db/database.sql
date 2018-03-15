CREATE SEQUENCE book_seq;

DROP TABLE public.empresas;

CREATE TABLE public.empresas
(
  id integer NOT NULL,
  nombre character(300),
  CONSTRAINT empresas_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE file_seq;

CREATE TABLE public.files_upload (
  'id' int(11) NOT NULL AUTO_INCREMENT,
  'file_name' varchar(128) DEFAULT NULL,
  'file_data' longblob,
  PRIMARY KEY ('upload_id')
)

