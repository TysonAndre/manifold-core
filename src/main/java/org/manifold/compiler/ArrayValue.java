package org.manifold.compiler;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class ArrayValue extends Value {

  private final TypeValue elementType;
  private List<Value> elements = null;

  public ArrayValue(ArrayTypeValue t, List<Value> elements)
      throws TypeMismatchException {
    super(t);
    this.elementType = t.getElementType();

    TypeValue etAlias = UserDefinedTypeValue.getUnaliasedType(elementType);
    // type-check contents -- every Value must have type 'elementType'
    for (Value element : elements){
      TypeValue vt = element.getType();
      TypeValue vtAlias = UserDefinedTypeValue.getUnaliasedType(vt);
      if (!vtAlias.equals(etAlias)) {
        throw new TypeMismatchException(elementType, vt);
      }
    }
    // now we can copy the new list into our object
    this.elements = ImmutableList.copyOf(elements);
  }

  public TypeValue getElementType(){
    return this.elementType;
  }

  public Value get(int i) {
    return elements.get(i);
  }

  public int length() {
    return elements.size();
  }

  @Override
  public void verify() throws Exception {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public boolean isElaborationtimeKnowable() {
    for (Value element : elements) {
      if (!element.isElaborationtimeKnowable()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isRuntimeKnowable() {
    for (Value element : elements) {
      if (!element.isRuntimeKnowable()) {
        return false;
      }
    }
    return true;
  }

  public void accept(SchematicValueVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public JsonElement toJson() {
    JsonArray arr = new JsonArray();
    for (Value element : elements) {
      arr.add(element.toJson());
    }
    return arr;
  }
}
