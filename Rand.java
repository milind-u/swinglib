package swinglib;

public class Rand {

  /**
   * Returns a random int from 0 (inclusive) to max (exclusive)
   * 
   * @param max One greater than the maximum possible value of the int that will be returned.
   * @return A random int from 0 up to but not including max
   */
  public static int randInt(int max) {
    return (int) (Math.random() * max);
  }

  /**
   * Returns a random int from min (inclusive) to max (exclusive)
   * 
   * @param min The minimum possible value of the int that will be returned
   * @param max One greater than the maximum possible value of the int that will be returned.
   * @return A random int from min up to but not including max
   */
  public static int randInt(int min, int max) {
    return ((int) (Math.random() * (max - min)) + min);
  }

  /**
   * Returns a random elements of the given array
   * 
   * @param <T> The Class that the given array contains instances of
   * @param t The array from which a random element will be returned
   * @return A random element of Class <code>T</code> from the given array
   */
  public static <T> T randElem(T[] t) {
    return t[randInt(t.length)];
  }

}
