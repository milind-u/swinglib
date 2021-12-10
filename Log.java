package swinglib;

import java.io.PrintStream;
import java.io.PrintWriter;

public final class Log {

  private Log() {} // Don't let anyone instantiate

  public static enum Severity {
    DEBUG(System.out), INFO(System.out), WARNING(System.out), ERROR(System.err), FATAL(System.err);

    private final PrintWriter out;

    private Severity(PrintStream out) {
      this.out = new PrintWriter(out, true);
    }
  }

  private static Severity min = Severity.INFO;
  private static final int FATAL_STATUS = 255;

  public static void setMinSeverity(Severity severity) {
    min = severity;
  }

  public static void debug(Object... args) {
    log(Severity.DEBUG, args);
  }

  public static void debugf(String fmt, Object... args) {
    logf(Severity.DEBUG, fmt, args);
  }

  public static void info(Object... args) {
    log(Severity.INFO, args);
  }

  public static void infof(String fmt, Object... args) {
    logf(Severity.INFO, fmt, args);
  }

  public static void warning(Object... args) {
    log(Severity.WARNING, args);
  }

  public static void warningf(String fmt, Object... args) {
    logf(Severity.WARNING, fmt, args);
  }

  public static void error(Object... args) {
    log(Severity.ERROR, args);
  }

  public static void errorf(String fmt, Object... args) {
    logf(Severity.ERROR, fmt, args);
  }

  public static void fatal(Object... args) {
    log(Severity.FATAL, args);
    Thread.dumpStack();
    System.exit(FATAL_STATUS);
  }

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
    sb.deleteCharAt(sb.length() - 1); // Remove last space
    return sb.toString();
  }

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

  public static void checkEq(Object a, Object b, Object... args) {
    checkOperator(a.equals(b), "==", a, b, args);
  }

  public static void checkNe(Object a, Object b, Object... args) {
    checkOperator(!a.equals(b), "!=", a, b, args);
  }

  public static void checkEqRef(Object a, Object b, Object... args) {
    checkOperator(a == b, "== (ref)", a, b, args);
  }

  public static void checkNeRef(Object a, Object b, Object... args) {
    checkOperator(a != b, "!= (ref)", a, b, args);
  }

  public static void checkNotNull(Object a, Object... args) {
    checkOperator(a != null, "!=", a, null, args);
  }

  public static <T extends Comparable<T>> void checkGt(T a, T b, Object... args) {
    checkOperator(a.compareTo(b) > 0, ">", a, b, args);
  }

  public static <T extends Comparable<T>> void checkLt(T a, T b, Object... args) {
    checkOperator(a.compareTo(b) < 0, "<", a, b, args);
  }

  public static <T extends Comparable<T>> void checkGe(T a, T b, Object... args) {
    checkOperator(a.compareTo(b) >= 0, ">=", a, b, args);
  }

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
