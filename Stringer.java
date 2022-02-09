package swinglib;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author milind
 * Class that implements a <code>toString</code> method for objects automatically.
 * Other classes can inherit from this one to automatically have a <code>toString</code>,
 * or they can call the static <code>toString</code> method below.
 */
public abstract class Stringer {

  /**
   * Returns a string with all fields of the object, including inherited ones.
   * Format is the follwing: Class[field1=value1, field2=value2, ..., fieldn = valuen]
   * @param o Object to stringify
   * @return A string with the given object's data
   */
  public static String toString(Object o) {
    final var sb = new StringBuffer(o.getClass().getName());
    sb.append('[');

    var cls = o.getClass();
    while (cls != null && cls != Object.class) {
      toString(cls.getDeclaredFields(), o, sb);
      cls = cls.getSuperclass();
    }
    sb.delete(sb.length() - ", ".length(), sb.length()); // Remove last comma
    sb.append(']');
    return sb.toString();
  }

  private static void toString(Field[] fields, Object o, StringBuffer sb) {
    for (int i = 0; i < fields.length; i++) {
      if (!Modifier.isStatic(fields[i].getModifiers())) {
        fields[i].setAccessible(true);
        try {
          sb.append(String.format("%s=%s", fields[i].getName(), fields[i].get(o)));
        } catch (IllegalArgumentException | IllegalAccessException e) {
          e.printStackTrace();
        }
        sb.append(", ");
      }
    }
  }

  @Override
  public String toString() {
    return toString(this);
  }

}
