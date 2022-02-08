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

import javax.swing.FocusManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

/*
 * Reusable utility code for swing Screen classes that extend JPanel and are for UIs.
 */
public abstract class AbstractScreen extends JPanel implements ActionListener, Runnable {

  protected static class Bounds extends Rectangle {

    private static final long serialVersionUID = 1L;

    public Bounds(int x, int y, int width, int height) {
      super(x, y, width, height);
    }

    public Bounds(int y, int width, int height) {
      super(centeredX(width), y, width, height);
    }

  }

  private static class TableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    public TableModel(Object... cols) {
      for (var o : cols) {
        addColumn(o);
      }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return false;
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
      return (obj.getClass() == getClass()) && (((HashableButton) obj).id == id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(id);
    }

  }

  private static class TextFieldWithPrompt extends JTextField {
    private String prompt = "";
    public TextFieldWithPrompt(int i) {
      super(i);
    }
    public TextFieldWithPrompt(int i, String prompt) {
      super(i);
      this.prompt = prompt;
    }
    public void setPrompt(String prompt) {
      this.prompt = prompt;
    }
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (getText().isEmpty() && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)) {
        Font font = getFont().deriveFont(Font.ITALIC);
        g.setFont(font);
        g.drawString(prompt, 5, font.getSize()); // figure out x, y from font's FontMetrics and size of component.
      }
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

  protected static void clearTextFields(JTextField... fields) {
    for (var jtf : fields) {
      jtf.setText(null);
    }
  }

  protected static boolean fieldBlank(JTextField... fields) {
    boolean blank = false;
    for (var jtf : fields) {
      if (jtf.getText().isBlank()) {
        blank = true;
        break;
      }
    }
    return blank;
  }

  protected JButton newButton(String text, Font font, Bounds bounds, Runnable onClick) {
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

  protected JButton newButton(String text, Font font, int x, int y, Runnable onClick) {
    int size = font.getSize();
    return newButton(text, font, new Bounds(x, y, size * text.length() + fractionOfWidth(.01), size + fractionOfHeight(.04)), onClick);
  }

  protected JButton newButton(String text, int x, int y, Runnable onClick) {
    return newButton(text, Fonts.MEDIUM, x, y, onClick);
  }
  
  protected JTextField newTextFieldWithPrompt(String label, Font font, Bounds bounds) {
    final var jtf = new TextFieldWithPrompt(WIDTH / 15, label);
    jtf.setBounds(bounds);
    jtf.setFont(font);
    add(jtf);
    return jtf;
  }

  protected JTextField newTextFieldWithPrompt(String label, Font font, int x, int y) {
    return newTextFieldWithPrompt(label, font, new Bounds(x, y, STD_TEXT_FIELD_WIDTH, STD_TEXT_FIELD_HEIGHT));
  }

  protected JTextField newTextFieldWithPrompt(String label, Font font, int y) {
    return newTextFieldWithPrompt(label, font, centeredX(STD_TEXT_FIELD_WIDTH), y);
  }

  protected LabeledComponent<JTextField> newTextField(String label, Font font, Bounds bounds) {
    final var jtf = new JTextField(WIDTH / 15);
    jtf.setBounds(bounds);
    jtf.setFont(font);
    return new LabeledComponent<>(jtf, label, this);
  }

  protected LabeledComponent<JTextField> newTextField(String label, Font font, int x, int y) {
    return newTextField(label, font, new Bounds(x, y, STD_TEXT_FIELD_WIDTH, STD_TEXT_FIELD_HEIGHT));
  }

  protected LabeledComponent<JTextField> newTextField(String label, Font font, int y) {
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

  protected Pair<JTextArea, JScrollPane> newScrollableTextArea(Font font, Bounds bounds) {
    final var jta = new JTextArea();
    jta.setFont(font);
    jta.setEditable(false);
    final var jsp = new JScrollPane(jta);
    jsp.setBounds(bounds);
    final var caret = (DefaultCaret) jta.getCaret();
    caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
    add(jsp);
    return new Pair<>(jta, jsp);
  }

  protected LabeledComponent<JSpinner> newSpinner(String label, Font font, Bounds bounds,
      SpinnerModel model) {
    final var js = new JSpinner(model);
    js.setBounds(bounds);
    js.setFont(font);
    return new LabeledComponent<>(js, label, this);
  }

  protected JLabel newLabel(String text, Font font, Bounds bounds) {
    final var jl = new JLabel(text);
    jl.setBounds(bounds);
    jl.setFont(font);
    add(jl);
    return jl;
  }

  protected Triplet<JTable, DefaultTableModel, JScrollPane> newTable(Font font, Bounds bounds,
      Object... cols) {
    final var table = initTable(font, bounds, cols);
    add(table.getThird());
    return table;
  }

  protected Triplet<JTable, DefaultTableModel, LabeledComponent<JScrollPane>> newLabeledTable(
      Font font, Font labelFont, String label, Bounds bounds, Object... cols) {
    final var table = initTable(font, bounds, cols);
    return new Triplet<>(table.getFirst(), table.getSecond(),
        new LabeledComponent<>(table.getThird(), label, labelFont, this,
            bounds.x - LabeledComponent.bufferedStringWidth(label, labelFont) / 2,
            bounds.y - LabeledComponent.bufferedStringHeight(labelFont)));
  }

  private Triplet<JTable, DefaultTableModel, JScrollPane> initTable(Font font, Bounds bounds,
      Object... cols) {
    final var tm = new TableModel(cols);

    final var jt = new JTable(tm);
    jt.setRowSelectionAllowed(false);
    jt.getTableHeader().setFont(font);
    jt.setFont(font);
    jt.setRowHeight((int) (font.getSize() * 1.75));

    final var jsp = new JScrollPane(jt);
    jsp.setBounds(bounds);

    return new Triplet<>(jt, tm, jsp);
  }

  @SafeVarargs
  public final <E> LabeledComponent<JComboBox<E>> newComboBox(String label, Font font,
      Bounds bounds, E... items) {
    final var jcb = new JComboBox<>(items);
    jcb.setFont(font);
    jcb.setBounds(bounds);
    jcb.setBackground(Color.WHITE);
    return new LabeledComponent<>(jcb, label, this);
  }

  @Override
  public Dimension getPreferredSize() {
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
    final var r = onClicks.get(e.getSource());
    // Make sure that the key was a button with an on click, and was not mapped to a null Runnable
    if (r != null) {
      r.run();
    }
  }

  private void initFrame(JFrame jf) {
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.pack();
    jf.setVisible(true);
  }

  public void run(String frameTitle) {
    final var jf = new JFrame(frameTitle);
    jf.add(this);
    initFrame(jf);
  }

  @Override
  public void run() {
    run(title);
  }

}
