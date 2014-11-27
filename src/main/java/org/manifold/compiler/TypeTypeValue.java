package org.manifold.compiler;

import com.google.common.collect.ImmutableMap;

public class TypeTypeValue extends TypeValue {

  private static TypeTypeValue instance = new TypeTypeValue();

  public static TypeTypeValue getInstance() {
    return instance;
  }

  private TypeTypeValue() {
    super(null, ImmutableMap.of());
  }

  // We override the isSubtypeOf method to prevent recursive loops.
  @Override
  public boolean isSubtypeOf(TypeValue type) {
    return this == type;
  }

  @Override
  public void accept(SchematicValueVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return "Type";
  }
}
