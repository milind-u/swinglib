package swinglib;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class Stringer {

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
