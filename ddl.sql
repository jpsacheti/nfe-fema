CREATE TABLE notafiscal
(
  codigo          SERIAL NOT NULL
    CONSTRAINT notafiscal_pkey
    PRIMARY KEY,
  xmlnota         XML,
  chavenfe        VARCHAR(46),
  datahoraemissao TIMESTAMP,
  xmlautorizacao  XML
);

