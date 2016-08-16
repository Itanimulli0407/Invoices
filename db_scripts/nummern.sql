-- Table: public.nummern

-- DROP TABLE public.nummern;

CREATE TABLE public.nummern
(
  nummer character varying(50) NOT NULL,
  art character varying(30),
  kunde bigint NOT NULL DEFAULT nextval('nummern_kunde_seq'::regclass),
  CONSTRAINT nummern_pkey PRIMARY KEY (nummer),
  CONSTRAINT nummer_kunde_fkey FOREIGN KEY (kunde)
      REFERENCES public.kunden (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.nummern
  OWNER TO lukas;
