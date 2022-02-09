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

/**
 * Reusable utility class for swing Screen classes that extend JPanel and are for UIs.
 * Has many defaults for UI look so that they don't have to be repeated in every project,
 * and the developer can focus on the actual code instead of remaking a UI each project. 
 * @author milind
 */
// TODO(milind): make new* methods have consistent params order
public abstract class AbstractScreen extends JPanel implements ActionListener, Runnable {

  /**
   * Bounds of a component on the screen.
   * @author milind
   */
  protected static class Bounds extends Rectangle {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a bounds rectangle with the given dimensions.
     * @param x Top left x
     * @param y Top left y
     * @param width Width of rectangle
     * @param height Height of rectangle
     */
    public Bounds(int x, int y, int width, int height) {
      super(x, y, width, height);
    }

    /**
     * Creates a horizontally-centered bounds rectangle.
     * @param y Top left y
     * @param width Width of rectangle
     * @param height Height of rectangle
     */
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

  /**
   * @author daichi
   */
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

  /**
   * Width of the screen window
   */
  public static final int WIDTH = 1300;
  /**
   * Height of the screen window
   */
  public static final int HEIGHT = 700;

  /**
   * Standard width of text fields
   */
  protected static final int STD_TEXT_FIELD_WIDTH = WIDTH / 5;
  /**
   * Standard height of text fields
   */
  protected static final int STD_TEXT_FIELD_HEIGHT = HEIGHT / 13;

  /**
   * Standard width of buttons
   */
  protected static final int STD_BUTTON_WIDTH = WIDTH / 5;
  /**
   * Standard height of buttons
   */
  protected static final int STD_BUTTON_HEIGHT = HEIGHT / 7;

  /**
   * Standard background color
   */
  protected static final Color BG_COLOR = new Color(240, 248, 255);
  /**
   * Standard button background color
   */
  protected static final Color BUTTON_COLOR = new Color(0xbaffc9);

  /**
   * Title of the screen
   */
  private final String title;
  /**
   * Map containing all buttons and functions to call on clicks
   */
  private final HashMap<HashableButton, Runnable> onClicks;

  /**
   * Creates a screen with the given title.
   * @param title Title of the screen that will be displayed.
   */
  protected AbstractScreen(String title) {
    setLayout(null);

    this.title = title;
    onClicks = new HashMap<>();
  }

  /**
   * Returns a fraction of the panel width
   * @param fraction Fraction of the width
   * @return The fraction of panel width
   */
  public static final int fractionOfWidth(double fraction) {
    return (int) (WIDTH * fraction);
  }

  /**
   * Returns a fraction of the panel height
   * @param fraction Fraction of the height
   * @return The fraction of panel height
   */
  public static final int fractionOfHeight(double fraction) {
    return (int) (HEIGHT * fraction);
  }

  /**
   * Computes the x for a rectangle with the given width to be horizontally-centered.
   * @param width Width of the rectangle
   * @return Centered x for the given width.
   */
  public static final int centeredX(int width) {
    return (WIDTH - width) / 2;
  }

  /**
   * Sets the contents of the given fields to <code>null</code>.
   * @param fields Text fields to clear
   */
  protected static void clearTextFields(JTextField... fields) {
    for (var jtf : fields) {
      jtf.setText(null);
    }
  }

  /**
   * Checks if one of the given text fields is blank.
   * @param fields Text fields to check
   * @return <code>true</code> if one of the fields is blank.
   */
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

  /**
   * Creates a button
   * @param text Button text
   * @param font Text font
   * @param bounds Bounding box of the button
   * @param onClick Function to be called on click
   * @return The created button
   */
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

  /**
   * Creates a button with <code>Fonts.MEDIUM</code>
   * @param text Button text
   * @param bounds Bounding box of the button
   * @param onClick Function to be called on click
   * @return The created button
   */
  protected JButton newButton(String text, Bounds bounds, Runnable onClick) {
    return newButton(text, Fonts.MEDIUM, bounds, onClick);
  }

  /**
   * Creates a button with <code>Fonts.MEDIUM</code> and standard button size.
   * @param text Button text
   * @param x The button's x
   * @param y The button's y
   * @param onClick Function to be called on click
   * @return The created button
   */
  protected JButton newButton(String text, int x, int y, Runnable onClick) {
    return newButton(text, Fonts.MEDIUM, new Bounds(x, y, STD_BUTTON_WIDTH, STD_BUTTON_HEIGHT),
        onClick);
  }
  
  /**
   * Creates a text field with a prompt inside the field
   * @author daichi
   * @param label Text field label
   * @param font Text field and label font
   * @param bounds Bounding box of the field
   * @return The created text field
   */
  protected JTextField newTextFieldWithPrompt(String label, Font font, Bounds bounds) {
    final var jtf = new TextFieldWithPrompt(WIDTH / 15, label);
    jtf.setBounds(bounds);
    jtf.setFont(font);
    add(jtf);
    return jtf;
  }

  /**
   * Creates a text field with a prompt inside the field and standard dimensions
   * @author daichi
   * @param label Text field label
   * @param font Text field and label font
   * @param x Text field x
   * @param y Text field y
   * @return The created text field
   */
  protected JTextField newTextFieldWithPrompt(String label, Font font, int x, int y) {
    return newTextFieldWithPrompt(label, font, new Bounds(x, y, STD_TEXT_FIELD_WIDTH, STD_TEXT_FIELD_HEIGHT));
  }

  /**
   * Creates a text field with a prompt inside the field and standard dimensions, 
   * centered horizontally.
   * @author daichi
   * @param label Text field label
   * @param font Text field and label font
   * @param y Text field y
   * @return The created text field
   */
  protected JTextField newTextFieldWithPrompt(String label, Font font, int y) {
    return newTextFieldWithPrompt(label, font, centeredX(STD_TEXT_FIELD_WIDTH), y);
  }

  /**
   * Creates a text field with an external label
   * @param label Text field label
   * @param font Text field and label font
   * @param bounds Bounding box of the field
   * @return The created text field
   */
  protected LabeledComponent<JTextField> newTextField(String label, Font font, Bounds bounds) {
    final var jtf = new JTextField(WIDTH / 15);
    jtf.setBounds(bounds);
    jtf.setFont(font);
    return new LabeledComponent<>(jtf, label, this);
  }

  /**
   * Creates a text field with an external label and standard dimensions.
   * @param label Text field label
   * @param font Text field and label font
   * @param x Text field x
   * @param y Text field y
   * @return The created text field
   */
  protected LabeledComponent<JTextField> newTextField(String label, Font font, int x, int y) {
    return newTextField(label, font, new Bounds(x, y, STD_TEXT_FIELD_WIDTH, STD_TEXT_FIELD_HEIGHT));
  }

  /**
   * Creates a text field with an external label and standard dimensions,
   * centered horizontally.
   * @param label Text field label
   * @param font Text field and label font
   * @param y Text field y
   * @return The created text field
   */
  protected LabeledComponent<JTextField> newTextField(String label, Font font, int y) {
    return newTextField(label, font, centeredX(STD_TEXT_FIELD_WIDTH), y);
  }

  /**
   * Creates a text area
   * @param font Text area font
   * @param bounds Bounding box of the area
   * @return The created text area
   */
  protected JTextArea newTextArea(Font font, Bounds bounds) {
    final var jta = new JTextArea();
    jta.setFont(font);
    jta.setEditable(false);
    jta.setBounds(bounds);
    add(jta);
    return jta;
  }

  /**
   * Creates a text area with a scrollbar
   * @param font Text area font
   * @param bounds Bounding box of the area
   * @return The created text area and scroll pane
   */
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

  /**
   * Creates a spinner with a label
   * @param label Spinner label
   * @param font Spinner font
   * @param bounds Bounding box of the spinner
   * @param model Spinner model
   * @return The created spinner with a label
   */
  protected LabeledComponent<JSpinner> newSpinner(String label, Font font, Bounds bounds,
      SpinnerModel model) {
    final var js = new JSpinner(model);
    js.setBounds(bounds);
    js.setFont(font);
    return new LabeledComponent<>(js, label, this);
  }

  /**
   * Creates a label
   * @param text Label text
   * @param font Label font
   * @param bounds Bounding box of the label
   * @return The created label
   */
  protected JLabel newLabel(String text, Font font, Bounds bounds) {
    final var jl = new JLabel(text);
    jl.setBounds(bounds);
    jl.setFont(font);
    add(jl);
    return jl;
  }

  /**
   * Creates a scrollable table
   * @param font Table font
   * @param bounds Bounding box of the table
   * @param cols Column labels of the table
   * @return The created table, along with it's model and scroll pane
   */
  protected Triplet<JTable, DefaultTableModel, JScrollPane> newTable(Font font, Bounds bounds,
      Object... cols) {
    final var table = initTable(font, bounds, cols);
    add(table.getThird());
    return table;
  }

  /**
   * Creates a scrollable table with a label
   * @param font Table font
   * @param labelFont Label font
   * @param label Table label
   * @param bounds Bounding box of the table
   * @param cols Column labels of the table
   * @return The created table, along with it's model and labeled scroll pane
   */
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

  /**
   * Creates a combo box with a label
   * @param <E> Class of the combo box items
   * @param label Box label
   * @param font Box font
   * @param bounds Bounding box of the combo box
   * @param items Items in the combo box dropown
   * @return The created labeled combo box
   */
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

  /**
   * Draws a string horizontally centered
   * @param g Graphics to draw with
   * @param s String to draw
   * @param y The y at the bottom of the drawn string
   */
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

  /**
   * Starts up the screen with the given window title.
   * @param frameTitle Window title
   */
  public void run(String frameTitle) {
    final var jf = new JFrame(frameTitle);
    jf.add(this);
    initFrame(jf);
  }

  /**
   * Starts up the screen with <code>title</code> as the window title.
   */
  @Override
  public void run() {
    run(title);
  }

}
