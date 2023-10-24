# Terraform Module: Linode Domain and Domain Records

This Terraform module manages the creation of a Linode Domain along with its associated Domain Records. It allows you to
define and configure a Linode Domain, set the type, SOA email, tags, and specify Domain Records for the domain.

## Data Source

- `linode_domain.base_domain`: This data source retrieves information about an existing Linode Domain with the specified
  base domain name (`var.base_domain`). It is used to obtain the domain ID when creating Domain Records.

## Resources Created

- `linode_domain.domain`: This resource creates a new Linode Domain with the specified configurations, including the
  domain type (`var.domain_type`), domain name (`var.domain_name`), SOA email (`var.soa_email`), tags (`var.tags`), and,
  if the domain type is set as "slave", the master IPs (`var.domain_master_ips`).

- `linode_domain_record.domain_record`: This resource creates Domain Records based on the configurations specified in
  the `var.domain_records` list.

## Module Inputs

- `base_domain`: The base domain name used to obtain the existing Linode Domain ID using the data source.

- `domain_type`: The type of the domain ("master" or "slave").

- `domain_name`: The full domain name to be created.

- `soa_email`: The SOA email for the domain.

- `tags`: The tags to be applied to the domain.

- `domain_master_ips`: The master IPs to be used if the domain type is set to "slave". It should be provided as a list.

- `domain_records`: A list of domain records to be created. Each domain record is represented as a map with the
  following attributes:

- `name`: The name of the domain record.

- `record_type`: The type of the domain record (e.g., "A", "CNAME", "MX", etc.).

- `target`: The target value of the domain record.

- `ttl_sec`: The Time To Live (TTL) value for the domain record (optional).

- `priority`: The priority value for the domain record (optional).

- `protocol`: The protocol for SRV records (optional).

- `service`: The service for SRV records (optional).

- `tag`: The tag for the domain record (optional).

- `port`: The port value for SRV records (optional).

- `weight`: The weight value for SRV records (optional).

## Usage Example

```hcl

module "linode_domain_and_records" {

    source = "path/to/this/module"

    base_domain = "example.com"

    domain_type = "master"

    domain_name = "subdomain.example.com"

    soa_email        = "admin@example.com"

    tags                = ["dns", "production"]

    domain_records = [

        {

            name                = "@"

            record_type = "A"

            target            = "192.0.2.1"

            ttl_sec            = 3600

        },

        {

            name                = "www"

            record_type = "CNAME"

            target            = "subdomain.example.com"

            ttl_sec            = 3600

        },

        {

            name                = "_sip"

            record_type = "SRV"

            target            = "sip.example.com"

            port                = 5060

            weight            = 1

        },

    ]

}

```

- In this example, a Linode Domain with the name "subdomain.example.com" will be created as a master domain. The SOA
  email will be set to "admin@example.com", and it will be tagged with "dns" and "production". Three domain records will
  also be created for the domain: an "A" record for the root domain pointing to IP address "192.0.2.1", a "CNAME" record
  for "www" pointing to "subdomain.example.com", and an "SRV" record for "_sip" with target "sip.example.com", port
  5060, and a weight of 1.

- This module also cover case of sub-domain. For example, If base domain is mpi.akamai.pl and sub domain is
  dev.mpi.akamai.pl. This module create NS record in mpi.akamai.pl so it will route DNS request to dev.mpi.akamai.pl.
