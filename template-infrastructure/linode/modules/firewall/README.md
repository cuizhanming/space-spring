# Terraform Module: Linode Firewall

This Terraform module creates a Linode Firewall with customizable inbound and outbound rules, allowing you to control
traffic to and from specified Linodes.

## Resources Created

- `linode_firewall.firewall_rules`: This resource creates a Linode Firewall with the specified configurations.

## Module Inputs

- `label`: A label to identify the Firewall. The label will be prefixed with "firewall-" and followed by the value of
  the `var.label`.

- `inbound_rules`: A list of inbound firewall rules with the following attributes.

- `outbound_rules`: A list of outbound firewall rules with similar attributes as the inbound rules.

- `action`: The action to take for the inbound rule (e.g., "DROP" or "ACCEPT").

- `label`: A label to identify the rule.

- `protocol`: The protocol for the rule (e.g., "tcp", "udp", or "icmp").

- `ports`: The port(s) to allow or deny traffic (e.g., "80", "443", or "1024-2048").

- `ipv4`: A list of IPv4 addresses or CIDR blocks to apply the rule to.

- `ipv6`: A list of IPv6 addresses or CIDR blocks to apply the rule to.

- `linode_ids`: The Linode ID(s) to apply the Firewall to. This can be a single ID or a list of IDs.

- `nodebalancer_ids`: The NodeBalancer ID(s) to apply the Firewall to. This can be a single ID or a list of IDs.

- `tags`: The tags to be applied to the Firewall.

## Default Policies

- `inbound_policy`: The default inbound policy set to "DROP", meaning that any incoming traffic that does not match the
  defined inbound rules will be dropped.

- `outbound_policy`: The default outbound policy set to "ACCEPT", meaning that any outgoing traffic that does not match
  the defined outbound rules will be allowed.

## Usage Example

```hcl
module "linode_firewall" {
  source        = "path/to/this/module"
  label         = "web-server-firewall"
  inbound_rules = [
    {
      action   = "ACCEPT"
      label    = "HTTP-Inbound"
      protocol = "tcp"
      ports    = "80"
      ipv4     = ["0.0.0.0/0"]
      ipv6     = []
    },
    {
      action   = "ACCEPT"
      label    = "SSH-Inbound"
      protocol = "tcp"
      ports    = "22"
      ipv4     = ["10.0.0.0/24", "192.168.1.0/24"]
      ipv6     = []
    },
  ]

  outbound_rules = [
    {
      action   = "ACCEPT"
      label    = "Outbound"
      protocol = "tcp"
      ports    = "0-65535"
      ipv4     = []
      ipv6     = []
    },
  ]
  linode_id = "1234567"
  tags      = ["web", "security"]
}
```

In this example, a Linode Firewall will be created with the label "web-server-firewall".
It allows incoming HTTP traffic on port 80 from any IPv4 address and SSH traffic on port 22 from the specified IPv4
subnets.
Outgoing traffic to any destination on any port will be allowed. The Firewall will be applied to the Linode with ID "
1234567",
and it will be tagged with "web" and "security".