package swinglib;

import java.awt.Font;

public final class Fonts {

  public static final Font MEDIUM =
      new Font("Times New Roman", Font.BOLD, AbstractScreen.HEIGHT / 20);
  public static final Font SMALL = newFont(0.85);
  public static final Font EXTRA_SMALL = newFont(0.7);
  public static final Font LARGE = newFont(1.5);
  public static final Font EXTRA_LARGE = newFont(2);

  public static Font newFont(double scale) {
    return new Font(MEDIUM.getFamily(), MEDIUM.getStyle(), (int) (MEDIUM.getSize() * scale));
  }

  private Fonts() {}

}
