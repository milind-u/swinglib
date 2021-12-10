package swinglib;

public class Util {

  private Util() {} // Don't let anyone instantiate

  public static int wrap(int i, int max) {
    Log.checkGt(max, 0, "Max must be positive");
    if (i < 0) {
      i = max - i;
    } else if (i >= max) {
      i -= max;
    }
    return i;
  }

  public static int clamp(int i, int min, int max) {
    return Math.max(Math.min(i, max - 1), min);
  }

}
