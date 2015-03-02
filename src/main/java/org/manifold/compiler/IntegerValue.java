package org.manifold.compiler;

public class IntegerValue extends Value {

  private final int val;

  public IntegerValue(int val) {
    super(IntegerTypeValue.getInstance());
    this.val = val;
  }

  @Override
  public boolean isElaborationtimeKnowable() {
    return true;
  }

  @Override
  public boolean isRuntimeKnowable() {
    return false;
  }


  @Override
  public String toString() {
    return String.valueOf(val);
  }

  public void accept(SchematicValueVisitor visitor) {
    visitor.visit(this);
  }

}
