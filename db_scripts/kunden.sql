-- Table: public.kunden

-- DROP TABLE public.kunden;

CREATE TABLE public.kunden
(
  id bigint NOT NULL DEFAULT nextval('kunden_id_seq'::regclass),
  nachname character varying(50) NOT NULL,
  vorname character varying(50),
  geburtsdatum date,
  strasse character varying(100),
  hausnr numeric(3,0),
  plz numeric(5,0),
  ort character varying(100),
  email character varying(100) DEFAULT NULL::character varying,
  CONSTRAINT kunden_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.kunden
  OWNER TO lukas;
