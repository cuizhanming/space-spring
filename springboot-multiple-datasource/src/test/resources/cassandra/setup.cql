CREATE KEYSPACE IF NOT EXISTS mykeyspace WITH replication =
{'class':'SimpleStrategy','replication_factor':'1'};

CREATE TABLE IF NOT EXISTS mykeyspace.credential (id text PRIMARY KEY, accessKey text, secretKey text, certificate text, privateKey text, createdAt timestamp, updatedAt timestamp);
CREATE INDEX credentialindexonaccess ON credential (accessKey);
CREATE INDEX credentialindexonsecret ON credential (secretKey);