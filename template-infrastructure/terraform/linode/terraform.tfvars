resources_label = "cui-guru"
resources_tags = ["dev"]

k8s_version = "1.31"
k8s_region = "gb-lon"
k8s_ha = false
k8s_pools = [
  {
    # https://api.linode.com/v4/linode/types
    type : "g6-standard-2"
    count : 3
    min : 3
    max : 6
  }
]

firewall_rules_outbound = []
firewall_rules_inbound = [
  {
    action    = "ACCEPT"
    label     = "TCP_NodePorts_for_workloads"
    protocol  = "TCP"
    ports     = "30000-32767"
    ipv4      = [ "0.0.0.0/0" ]
  },
  {
    action    = "ACCEPT"
    label     = "UDP_NodePorts_for_workloads"
    protocol  = "UDP"
    ports     = "30000-32767"
    ipv4      = [ "0.0.0.0/0" ]
  },
#  {
#    action    = "ACCEPT"
#    label     = "Enable_IPENCAP"
#    protocol  = "IPENCAP"
#    ipv4      = [ "192.168.128.0/17" ]
#  },
#  {
#    action    = "ACCEPT"
#    label     = "Kubelet_health_checks"
#    protocol  = "TCP"
#    ports     = "10250"
#    ipv4      = [ "192.168.128.0/17" ]
#  },
#  {
#    action    = "ACCEPT"
#    label     = "Calico_BGP_traffic"
#    protocol  = "TCP"
#    ports     = "179"
#    ipv4      = [ "192.168.128.0/17" ]
#  },
#  {
#    # https://www.linode.com/community/questions/6908/what-is-the-nodebalancer-private-ip-range
#    action    = "ACCEPT"
#    label     = "Node_Balancers"
#    protocol  = "TCP"
#    ports     = "80,443"
#    ipv4      = [ "192.168.255.0/24" ]
#  },
#  {
#    action    = "ACCEPT"
#    label     = "Istio_Ingress"
#    protocol  = "TCP"
#    ports     = "443"
#    ipv4      = [ "0.0.0.0/0" ]
#  },
#  {
#    # https://istio.io/latest/docs/ops/deployment/requirements/
#    action    = "ACCEPT"
#    label     = "Envoy_Proxy_Default_Ports"
#    protocol  = "TCP"
#    ports     = "15000,15001,15004,15006,15008,15009,15020,15021,15053,15090,10250"
#    ipv4      = [ "10.128.0.0/16", "10.2.0.0/16" ]
#  },
#  {
#    action    = "ACCEPT"
#    label     = "Istio_Control_Default_Ports"
#    protocol  = "TCP"
#    ports     = "15010,15012,15014,15017"
#    ipv4      = [ "10.128.0.0/16", "10.2.0.0/16" ]
#  },
#  {
#    action    = "ACCEPT"
#    label     = "inbound-UDP-DNS"
#    protocol  = "UDP"
#    ports     = "53"
#    ipv4      = [ "192.168.128.0/17", "10.128.0.0/16" ]
#  },
#  {
#    action    = "ACCEPT"
#    label     = "inbound-TCP-DNS"
#    protocol  = "TCP"
#    ports     = "53"
#    ipv4      = [ "192.168.128.0/17", "10.128.0.0/16" ]
#  }
]


dns_soa_email = "i@cuizhanming.com"
dns_base_domain="api.cuizhanming.com"
dns_domain_name = "dev.api.cuizhanming.com"
dns_domain_records = [
  {
    name = "dev.api.cuizhanming.com"
    record_type = "NS"
    target = "ns1.linode.com"
  }
]