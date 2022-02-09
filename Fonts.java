package swinglib;

import java.awt.Font;

/**
 * @author milind
 * Class with default fonts to use.
 */
public final class Fonts {

  /**
   * Medium sized font
   */
  public static final Font MEDIUM =
      new Font("Times New Roman", Font.BOLD, AbstractScreen.HEIGHT / 20);
  /**
   * Small sized font
   */
  public static final Font SMALL = newFont(0.85);
  /**
   * Very small sized font
   */
  public static final Font EXTRA_SMALL = newFont(0.7);
  /**
   * Large sized font
   */
  public static final Font LARGE = newFont(1.5);
  /**
   * Very large sized font
   */
  public static final Font EXTRA_LARGE = newFont(2);

  /**
   * Creates a font with the given scale relative to <code>MEDIUM</code>
   * @param scale Factor to multiply <code>MEDIUM</code> by
   * @return The created font
   */
  public static Font newFont(double scale) {
    return new Font(MEDIUM.getFamily(), MEDIUM.getStyle(), (int) (MEDIUM.getSize() * scale));
  }

  private Fonts() {}

}
