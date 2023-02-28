package br.dev.yann.rssreader.rss.rss.elements.channel.elements;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;


@XmlAccessorType(XmlAccessType.FIELD)
public class Cloud {

  @XmlAttribute
  private String domain;

  @XmlAttribute
  private String port;

  @XmlAttribute
  private String path;

  @XmlAttribute
  private String registerProcedure;

  @XmlAttribute
  private String protocol;



  public Cloud() {
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getRegisterProcedure() {
    return registerProcedure;
  }

  public void setRegisterProcedure(String registerProcedure) {
    this.registerProcedure = registerProcedure;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((domain == null) ? 0 : domain.hashCode());
    result = prime * result + ((port == null) ? 0 : port.hashCode());
    result = prime * result + ((path == null) ? 0 : path.hashCode());
    result = prime * result + ((registerProcedure == null) ? 0 : registerProcedure.hashCode());
    result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Cloud other = (Cloud) obj;
    if (domain == null) {
      if (other.domain != null)
        return false;
    } else if (!domain.equals(other.domain))
      return false;
    if (port == null) {
      if (other.port != null)
        return false;
    } else if (!port.equals(other.port))
      return false;
    if (path == null) {
      if (other.path != null)
        return false;
    } else if (!path.equals(other.path))
      return false;
    if (registerProcedure == null) {
      if (other.registerProcedure != null)
        return false;
    } else if (!registerProcedure.equals(other.registerProcedure))
      return false;
    if (protocol == null) {
      if (other.protocol != null)
        return false;
    } else if (!protocol.equals(other.protocol))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Cloud [domain=" + domain + ", port=" + port + ", path=" + path + ", registerProcedure=" + registerProcedure + ", protocol=" + protocol + "]";
  }


}
