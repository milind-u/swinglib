package swinglib;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Google-style logger to make debugging easier
 * @author milind
 */
public final class Log {

  private Log() {} // Don't let anyone instantiate

  /**
   * Severity levels for logging, used to filter out lower-level messages
   * once they are no longer needed
   * @author milind
   */
  public static enum Severity {
    /**
     * Severity for debug statements
     */
    DEBUG(System.out),
    /**
     * Severity for normal printing
     */
    INFO(System.out),
    /**
     * Severity for mild problems that can be accounted for
     */
    WARNING(System.out),
    /**
     * Severity for medium errors that are not fatal
     */
    ERROR(System.err),
    /**
     * Severity for errors that require killing the program
     */
    FATAL(System.err);

    private final PrintWriter out;

    private Severity(PrintStream out) {
      this.out = new PrintWriter(out, true);
    }
  }

  private static Severity min = Severity.INFO;
  private static final int FATAL_STATUS = 255;

  /**
   * Sets the minimum severity for logs that are printed.
   * Subsequent logs of lower severities will not be printed, 
   * but they can be kept in code.
   * @param severity Minimum severity
   */
  public static void setMinSeverity(Severity severity) {
    min = severity;
  }

  /**
   * Logs arguments separated by a space as debug severity
   * @param args Arguments to log
   */
  public static void debug(Object... args) {
    log(Severity.DEBUG, args);
  }

  /**
   * Logs arguments printf-style as debug severity
   * @param fmt String to format into
   * @param args Aruments to log
   */
  public static void debugf(String fmt, Object... args) {
    logf(Severity.DEBUG, fmt, args);
  }

  /**
   * Logs arguments separated by a space as info severity
   * @param args Arguments to log
   */
  public static void info(Object... args) {
    log(Severity.INFO, args);
  }

  /**
   * Logs arguments printf-style as info severity
   * @param fmt String to format into
   * @param args Aruments to log
   */
  public static void infof(String fmt, Object... args) {
    logf(Severity.INFO, fmt, args);
  }

  /**
   * Logs arguments separated by a space as warning severity
   * @param args Arguments to log
   */
  public static void warning(Object... args) {
    log(Severity.WARNING, args);
  }

  /**
   * Logs arguments printf-style as warning severity
   * @param fmt String to format into
   * @param args Aruments to log
   */
  public static void warningf(String fmt, Object... args) {
    logf(Severity.WARNING, fmt, args);
  }

  /**
   * Logs arguments separated by a space as error severity
   * @param args Arguments to log
   */
  public static void error(Object... args) {
    log(Severity.ERROR, args);
  }

  /**
   * Logs arguments printf-style as error severity
   * @param fmt String to format into
   * @param args Aruments to log
   */
  public static void errorf(String fmt, Object... args) {
    logf(Severity.ERROR, fmt, args);
  }

  /**
   * Logs arguments separated by a space as fatal severity, and kills the process
   * @param args Arguments to log
   */
  public static void fatal(Object... args) {
    log(Severity.FATAL, args);
    Thread.dumpStack();
    System.exit(FATAL_STATUS);
  }

  /**
   * Logs arguments printf-style as fatal severity, and kills the process
   * @param fmt String to format into
   * @param args Aruments to log
   */
  public static void fatalf(String fmt, Object... args) {
    logf(Severity.FATAL, fmt, args);
    Thread.dumpStack();
    System.exit(FATAL_STATUS);
  }

  private static String varargsToString(Object... args) {
    StringBuilder sb = new StringBuilder();
    for (var o : args) {
      sb.append(o);
      sb.append(" ");
    }
    if (sb.length() != 0) {
      sb.deleteCharAt(sb.length() - 1); // Remove last space
    }
    return sb.toString();
  }

  /**
   * Calls <code>fatal</code> with the given arguments if the given condition is false
   * @param cond Condition to check
   * @param args Arguments to log if condition fails
   */
  public static void check(boolean cond, Object... args) {
    if (!cond) {
      fatal("Check failed:", varargsToString(args));
    }
  }

  private static void checkOperator(boolean cond, String operator, Object a, Object b,
      Object... args) {
    if (!cond) {
      fatal("Check failed: expected", a, operator, b, '\n', varargsToString(args));
    }
  }

  /**
   * Calls <code>fatal</code> with the given arguments if <code>!a.equals(b)</code>
   * @param a First argument to operator
   * @param b Second argument to operator
   * @param args Arguments to log if condition fails
   */
  public static void checkEq(Object a, Object b, Object... args) {
    checkOperator(a.equals(b), "==", a, b, args);
  }

  /**
   * Calls <code>fatal</code> with the given arguments if <code>a.equals(b)</code>
   * @param a First argument to operator
   * @param b Second argument to operator
   * @param args Arguments to log if condition fails
   */
  public static void checkNe(Object a, Object b, Object... args) {
    checkOperator(!a.equals(b), "!=", a, b, args);
  }

  /**
   * Calls <code>fatal</code> with the given arguments if <code>a != b</code>
   * @param a First argument to operator
   * @param b Second argument to operator
   * @param args Arguments to log if condition fails
   */
  public static void checkEqRef(Object a, Object b, Object... args) {
    checkOperator(a == b, "== (ref)", a, b, args);
  }

  /**
   * Calls <code>fatal</code> with the given arguments if <code>a == b</code>
   * @param a First argument to operator
   * @param b Second argument to operator
   * @param args Arguments to log if condition fails
   */
  public static void checkNeRef(Object a, Object b, Object... args) {
    checkOperator(a != b, "!= (ref)", a, b, args);
  }

  /**
   * Calls <code>fatal</code> with the given arguments if <code>a == null</code>
   * @param a Object to check
   * @param args Arguments to log if condition fails
   */
  public static void checkNotNull(Object a, Object... args) {
    checkOperator(a != null, "!=", a, null, args);
  }

  /**
   * Calls <code>fatal</code> with the given arguments if <code> a &lt;= b</code>
   * @param <T> Class of the objects
   * @param a First argument to operator
   * @param b Second argument to operator
   * @param args Arguments to log if condition fails
   */
  public static <T extends Comparable<T>> void checkGt(T a, T b, Object... args) {
    checkOperator(a.compareTo(b) > 0, ">", a, b, args);
  }

  /**
   * Calls <code>fatal</code> with the given arguments if <code> a &gt;= b</code>
   * @param <T> Class of the objects
   * @param a First argument to operator
   * @param b Second argument to operator
   * @param args Arguments to log if condition fails
   */
  public static <T extends Comparable<T>> void checkLt(T a, T b, Object... args) {
    checkOperator(a.compareTo(b) < 0, "<", a, b, args);
  }

  /**
   * Calls <code>fatal</code> with the given arguments if <code> a &lt; b</code>
   * @param <T> Class of the objects
   * @param a First argument to operator
   * @param b Second argument to operator
   * @param args Arguments to log if condition fails
   */
  public static <T extends Comparable<T>> void checkGe(T a, T b, Object... args) {
    checkOperator(a.compareTo(b) >= 0, ">=", a, b, args);
  }

  /**
   * Calls <code>fatal</code> with the given arguments if <code> a &gt; b</code>
   * @param <T> Class of the objects
   * @param a First argument to operator
   * @param b Second argument to operator
   * @param args Arguments to log if condition fails
   */
  public static <T extends Comparable<T>> void checkLe(T a, T b, Object... args) {
    checkOperator(a.compareTo(b) <= 0, "<=", a, b, args);
  }


  private static void logHeader(Severity severity) {
    var caller = Thread.currentThread().getStackTrace()[4];
    severity.out.printf("[%s %s:%d] ", severity, caller.getFileName(), caller.getLineNumber());
  }

  private static void log(Severity severity, Object... args) {
    if (severity.ordinal() >= min.ordinal()) {
      logHeader(severity);
      for (var o : args) {
        severity.out.print(o);
        if ((o != null) && !o.equals('\n') && !o.equals("\n")) {
          severity.out.print(' ');
        }
      }
      severity.out.println();
    }
  }

  private static void logf(Severity severity, String fmt, Object... args) {
    if (severity.ordinal() >= min.ordinal()) {
      logHeader(severity);
      severity.out.printf(fmt, args);
      severity.out.println();
    }
  }

}
