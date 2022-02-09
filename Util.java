package swinglib;

/**
 * Class with various utility functions
 * @author milind
 */
public class Util {

  private Util() {} // Don't let anyone instantiate

  /**
   * Wraps i around 0 or max if it is past either
   * @param i Integer to wrap
   * @param max Maximum value
   * @return Wrapped value of the given integer
   */
  public static int wrap(int i, int max) {
    Log.checkGt(max, 0, "Max must be positive");
    if (i < 0) {
      i = max - i;
    } else if (i >= max) {
      i -= max;
    }
    return i;
  }

  /**
   * Sets the given integer to either side 
   * of the given bounds if it is past one
   * @param i Integer to clamp
   * @param min Minumum value
   * @param max Maximum value
   * @return Clamped value of the given integer
   */
  public static int clamp(int i, int min, int max) {
    return Math.max(Math.min(i, max - 1), min);
  }

}
