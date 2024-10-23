Terraform Module: Linode PostgresQL Database
--------------------------------------------

This Terraform module provisions a Linode PostgresQL Database cluster in a specified region and allows for various
configuration options.

### Resources Created

- linode_database_postgresql.postgresql-db: This resource creates a Linode PostgreSQL Database cluster with the
  specified configurations. It allows you to set up replication, SSL connection, and automated updates.

### Module Inputs

- label: A label to identify the PostgreSQL cluster. It will be prefixed with "pg-" and followed by the value of
  the `var.environment`.
- engine_id: The PostgreSQL engine ID. Specify the version of PostgreSQL you want to use.
- region: The Linode region where the PostgreSQL cluster will be created.
- type: The type of Linode instance to be used for the PostgreSQL cluster.

Optional Configuration Inputs:

- allow_list: A list of IPv4 addresses or CIDR blocks allowed to connect to the database.
- cluster_size: The size of the PostgreSQL cluster, e.g., "g6-standard-2".
- encrypted: A boolean value to enable encryption at rest for the database cluster.
- replication_type: The type of replication to be used for the database cluster.
- replication_commit_type: The commit type for replication.
- ssl_connection: A boolean value to enable SSL connection for the database.

### Automated Updates

The database cluster is set up with automated updates, which occur once a month on the second Saturday at 22:00.

### Experimental Blocks

The following blocks are intended for experimental purposes. They capture sensitive information from the created
PostgreSQL cluster and create a local JSON file containing this information.

- locals: A local block that captures essential information from the created PostgreSQL cluster, such as the public
  hostname, private hostname, database username, and database password.
- local_sensitive_file.db_info_file: A resource that creates a local JSON file named `db_info.json` containing the
  information captured in the `locals` block. This file should not be changed, as it is referenced in an
  external `install-grafana.yaml` file.

### Usage Example

hclCopy code

`module "postgresql_cluster" {
  source = "path/to/this/module"
  
  environment = "dev"
  engine_id = "postgres12"
  region = "us-central"
  type = "g6-standard-2"
  allow_list = ["192.0.2.0/24", "10.0.0.1"]
  cluster_size = "g6-standard-4"
  encrypted = true
  replication_type = "auto"
  replication_commit_type= "logical"
  ssl_connection = true
}`

In this example, a Linode PostgreSQL Database cluster will be created in the "us-central" region using a "g6-standard-2"
Linode instance type. The cluster will be labeled as "pg-dev-db" since the environment variable is set to "dev".
Replication and SSL connection will be enabled, and the database will be encrypted at rest. Automated updates will be
scheduled for the second Saturday of each month at 22:00.

The experimental blocks capture the PostgreSQL cluster's information and create a `db_info.json` file, which should not
be altered as it is used in an external `install-grafana.yaml` file.