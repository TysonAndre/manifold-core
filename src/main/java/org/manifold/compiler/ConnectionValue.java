package org.manifold.compiler;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

public class ConnectionValue extends Value {

  private final Attributes attributes;

  public Value getAttribute(String attrName) throws
      UndeclaredAttributeException {
    return attributes.get(attrName);
  }

  public Attributes getAttributes() {
    return attributes;
  }

  private PortValue portFrom = null, portTo = null;

  public PortValue getFrom() {
    return portFrom;
  }

  public PortValue getTo() {
    return portTo;
  }

  public ConnectionValue(PortValue from, PortValue to, Map<String, Value> attrs)
      throws TypeMismatchException {
    super(ConnectionTypeValue.getInstance());
    this.attributes = new Attributes(attrs);
    this.portFrom = checkNotNull(from);
    this.portTo = checkNotNull(to);

    if (from == to) {
      throw new UndefinedBehaviourError(
        "Cannot create connection from a port to itself"
      );
    }
    // type check: the signal type from the source is a subclass
    // of the signal type into the destination
    PortTypeValue portTypeFrom = (PortTypeValue) portFrom.getType();
    PortTypeValue portTypeTo = (PortTypeValue) portTo.getType();
    TypeValue signalTypeFrom = portTypeFrom.getSignalType();
    TypeValue signalTypeTo = portTypeTo.getSignalType();
    TypeValue signalTypeToUnaliased =
        UserDefinedTypeValue.getUnaliasedType(signalTypeTo);
    if (!(signalTypeFrom.isSubtypeOf(signalTypeToUnaliased))) {
      throw new TypeMismatchException(signalTypeFrom, signalTypeTo);
    }
  }

  @Override
  public boolean isElaborationtimeKnowable() {
    return false;
  }

  @Override
  public boolean isRuntimeKnowable() {
    return true;
  }

  @Override
  public void accept(SchematicValueVisitor visitor) {
    visitor.visit(this);
  }

}
