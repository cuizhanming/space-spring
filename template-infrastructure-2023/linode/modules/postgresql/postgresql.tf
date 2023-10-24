resource "linode_database_postgresql" "postgresql-db" {
  label     = "pg-${var.label}-db"
  engine_id = var.engine_id
  region    = var.region
  type      = var.type

  allow_list              = var.allow_list
  cluster_size            = var.cluster_size
  encrypted               = var.encrypted
  replication_type        = var.replication_type
  replication_commit_type = var.replication_commit_type
  ssl_connection          = var.ssl_connection

  updates {
    day_of_week   = "saturday"
    duration      = 1
    frequency     = "monthly"
    hour_of_day   = 22
    week_of_month = 2
  }
}

#Following blocks use for experimental purpose.
resource "local_sensitive_file" "db_info_file" {
  filename = "db_info.json"  #Don't change this file name. it reference in install-grafana.yaml
  content  = jsonencode(local.db_info)
}

locals {
  db_info = {
    public_hostname  = linode_database_postgresql.postgresql-db.host_primary
    private_hostname = linode_database_postgresql.postgresql-db.host_secondary
    db_username      = linode_database_postgresql.postgresql-db.root_username
    db_password      = linode_database_postgresql.postgresql-db.root_password
  }
}




