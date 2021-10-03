package swinglib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;

/*
 * Reusable utility code for swing Screen classes that extend JPanel and are for UIs.
 */
public abstract class AbstractScreen extends JPanel implements ActionListener {

  protected static class Bounds extends Rectangle {

    private static final long serialVersionUID = 1L;

    public Bounds(int x, int y, int width, int height) {
      super(x, y, width, height);
    }

    public Bounds(int y, int width, int height) {
      super(centeredX(width), y, width, height);
    }

  }

  private static class HashableButton extends JButton {

    private static final long serialVersionUID = 1L;

    private static int instances = 0;

    private final int id;

    public HashableButton(String text) {
      super(text);
      id = instances++;
    }

    @Override
    public boolean equals(Object obj) {
      return (obj.getClass() == getClass())
          && (((HashableButton) obj).id == id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }

  }

  private static final long serialVersionUID = 1L;

  public static final int WIDTH = 1300;
  public static final int HEIGHT = 700;

  protected static final int STD_TEXT_FIELD_WIDTH = WIDTH / 5;
  protected static final int STD_TEXT_FIELD_HEIGHT = HEIGHT / 13;

  protected static final int STD_BUTTON_WIDTH = WIDTH / 5;
  protected static final int STD_BUTTON_HEIGHT = HEIGHT / 7;

  protected static final Color BG_COLOR = new Color(240, 248, 255);
  protected static final Color BUTTON_COLOR = new Color(0xbaffc9);

  private final String title;

  private final HashMap<HashableButton, Runnable> onClicks;

  protected AbstractScreen(String title) {
    setLayout(null);

    this.title = title;
    onClicks = new HashMap<>();
  }

  public static final int fractionOfWidth(double fraction) {
    return (int) (WIDTH * fraction);
  }

  public static final int fractionOfHeight(double fraction) {
    return (int) (HEIGHT * fraction);
  }

  public static final int centeredX(int width) {
    return (WIDTH - width) / 2;
  }

  protected JButton newButton(String text, Font font, Bounds bounds,
      Runnable onClick) {
    final var jb = new HashableButton(text);
    jb.setBounds(bounds);
    jb.setFont(font);
    jb.setBackground(BUTTON_COLOR);

    add(jb);
    jb.addActionListener(this);

    onClicks.put(jb, onClick);

    return jb;
  }

  protected JButton newButton(String text, Bounds bounds, Runnable onClick) {
    return newButton(text, Fonts.MEDIUM, bounds, onClick);
  }

  protected JButton newButton(String text, int x, int y, Runnable onClick) {
    return newButton(text, Fonts.MEDIUM,
        new Bounds(x, y, STD_BUTTON_WIDTH, STD_BUTTON_HEIGHT), onClick);
  }

  protected LabeledComponent<JTextField> newTextField(String label, Font font,
      Bounds bounds) {
    final var jtf = new JTextField(WIDTH / 15);
    jtf.setBounds(bounds);
    jtf.setFont(Fonts.SMALL);
    return new LabeledComponent<JTextField>(jtf, label, font, this);
  }

  protected LabeledComponent<JTextField> newTextField(String label, Font font,
      int x, int y) {
    return newTextField(label, font,
        new Bounds(x, y, STD_TEXT_FIELD_WIDTH, STD_TEXT_FIELD_HEIGHT));
  }

  protected LabeledComponent<JTextField> newTextField(String label, Font font,
      int y) {
    return newTextField(label, font, centeredX(STD_TEXT_FIELD_WIDTH), y);
  }

  protected JTextArea newTextArea(Font font, Bounds bounds) {
    final var jta = new JTextArea();
    jta.setFont(font);
    jta.setEditable(false);
    jta.setBounds(bounds);
    add(jta);
    return jta;
  }

  protected Pair<JTextArea, JScrollPane> newScrollableTextArea(Font font,
      Bounds bounds) {
    final var jta = new JTextArea();
    jta.setFont(font);
    jta.setEditable(false);
    final var jsp = new JScrollPane(jta);
    jsp.setBounds(bounds);
    add(jsp);
    return new Pair<>(jta, jsp);
  }

  protected LabeledComponent<JSpinner> newSpinner(String label, Font font,
      Bounds bounds, SpinnerModel model) {
    final var js = new JSpinner(model);
    js.setBounds(bounds);
    js.setFont(font);
    return new LabeledComponent<>(js, label, font, this);
  }

  @Override
  public final Dimension getPreferredSize() {
    return new Dimension(WIDTH, HEIGHT);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(BG_COLOR);
    g.fillRect(0, 0, WIDTH, HEIGHT);

    g.setColor(Color.DARK_GRAY);
    g.setFont(Fonts.EXTRA_LARGE);
    drawStringCentered(g, title, HEIGHT / 7);
  }

  protected final void drawStringCentered(Graphics g, String s, int y) {
    final var metrics = g.getFontMetrics(g.getFont());
    final int x = (WIDTH - metrics.stringWidth(s)) / 2;
    g.drawString(s, x, y);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (onClicks.containsKey(e.getSource())) {
      onClicks.get(e.getSource()).run();
    }
  }

  public void run(String frameTitle) {
    final var frame = new JFrame(frameTitle);

    frame.add(this);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  public void run() {
    run(title);
  }

}
