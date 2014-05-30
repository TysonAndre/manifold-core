package org.whdl.intermediate;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class NodeType extends Type {
 
  private Map<String, Type> attributes;
  private Map<String, PortType> ports;
  
  public NodeType(Map<String, Type> attributes, Map<String, PortType> ports){
    this.attributes = new HashMap<String, Type>(attributes);
    this.ports = new HashMap<String, PortType>(ports);
  }
  
}
