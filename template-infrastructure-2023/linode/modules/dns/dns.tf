resource "linode_domain" "base_domain" {
  type       = "master"
  domain     = var.base_domain
  soa_email  = var.soa_email
}

resource "linode_domain" "domain" {
  type       = var.domain_type
  domain     = var.domain_name
  soa_email  = var.soa_email
  tags       = var.tags
  master_ips = var.domain_type == "slave" ? var.domain_master_ips : null
}

resource "linode_domain_record" "domain_record" {
  for_each = local.domain_records_map

  domain_id   = each.value.record_type == "NS" ? linode_domain.base_domain.id : linode_domain.domain.id
  name        = each.value.name
  record_type = each.value.record_type
  target      = each.value.target
  ttl_sec     = each.value.ttl_sec
  priority    = each.value.priority
  protocol    = each.value.protocol
  service     = each.value.service
  tag         = each.value.tag
  port        = each.value.port
  weight      = each.value.weight
}

locals {
  domain_records_map = {
    for rec in var.domain_records : rec.name => rec
  }
}
