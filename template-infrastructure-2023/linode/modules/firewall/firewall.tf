resource "linode_firewall" "firewall_rules" {

  label         = "firewall-${var.label}"
  tags          = var.tags
  linodes       = var.linode_ids
  nodebalancers = var.nodebalancer_ids

  inbound_policy  = "DROP"
  outbound_policy = "ACCEPT"

  dynamic "inbound" {
    for_each = var.inbound_rules
    content {
      action   = inbound.value.action
      label    = inbound.value.label
      protocol = inbound.value.protocol
      ports    = inbound.value.ports
      ipv4     = inbound.value.ipv4
      ipv6     = inbound.value.ipv6
    }
  }

  dynamic "outbound" {
    for_each = var.outbound_rules
    content {
      action   = outbound.value.action
      label    = outbound.value.label
      protocol = outbound.value.protocol
      ports    = outbound.value.ports
      ipv4     = outbound.value.ipv4
      ipv6     = outbound.value.ipv6
    }
  }

}