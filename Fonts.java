package swinglib;

import java.awt.Font;

public final class Fonts {

  public static final Font MEDIUM =
      new Font("Times New Roman", Font.BOLD, AbstractScreen.HEIGHT / 20);
  public static final Font SMALL = makeFont(0.85);
  public static final Font EXTRA_SMALL = makeFont(0.7);
  public static final Font LARGE = makeFont(1.5);
  public static final Font EXTRA_LARGE = makeFont(2);

  private static Font makeFont(double scale) {
    return new Font(MEDIUM.getFamily(), MEDIUM.getStyle(), (int) (MEDIUM.getSize() * scale));
  }

  private Fonts() {}

}
