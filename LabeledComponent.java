package swinglib;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @author milind
 * Class that wraps a component and adds a label to it
 * @param <C> Class of the component
 */
public class LabeledComponent<C extends Component> extends JComponent {

  private static final long serialVersionUID = 1L;

  /**
   * Wrapped component
   */
  private final C component;
  /**
   * Label for component
   */
  private final JLabel label;

  /**
   * Creates a labeled component with the given values
   * @param component The component to label
   * @param label Label text
   * @param labelFont Label font
   * @param s Screen to add to
   * @param x Top left x of label
   * @param y Top left y of label
   */
  public LabeledComponent(C component, String label, Font labelFont, AbstractScreen s, int x,
      int y) {
    this.component = component;
    this.label = new JLabel(label + ':', SwingConstants.RIGHT);
    this.label.setBounds(x, y, bufferedStringWidth(label, labelFont),
        bufferedStringHeight(labelFont));
    this.label.setFont(labelFont);
    this.label.setVisible(component.isVisible());

    s.add(this.label);
    s.add(component);
  }

  /**
   * Creates a labeled component with the default coordinates to fit the component
   * @param component The component to label
   * @param label Label text
   * @param labelFont Label font
   * @param s Screen to add to
   */
  public LabeledComponent(C component, String label, Font labelFont, AbstractScreen s) {
    this(component, label, labelFont, s,
        component.getX() - bufferedStringWidth(label, labelFont) - labelFont.getSize(),
        component.getY());
  }

  /**
   * Creates a labeled component with the default coordinates to fit the component and 
   * <code>Fonts.MEDIUM</code>.
   * @param component The component to label
   * @param label Label text
   * @param s Screen to add to
   */
  public LabeledComponent(C component, String label, AbstractScreen s) {
    this(component, label, component.getFont(), s);
  }

  /**
   * Returns the screen width of a string with some buffer with a given font
   * @param s String to return the width of
   * @param font Font of the string
   * @return An upper bound for the width of the given string with the given font
   */
  public static int bufferedStringWidth(String s, Font font) {
    return font.getSize() * s.length();
  }

  /**
   * Returns the screen height of any string with a given font with some buffer 
   * @param font Font of the string
   * @return An upper bound for the height of a string with the given font
   */
  public static int bufferedStringHeight(Font font) {
    return (int) (font.getSize() * 1.2);
  }

  @Override
  public void setVisible(boolean aFlag) {
    component.setVisible(aFlag);
    label.setVisible(aFlag);
  }

  /**
   * Returns the wrapped component
   * @return The wrapped component
   */
  public C get() {
    return component;
  }

  /**
   * Returns the label of the component
   * @return The label
   */
  public JLabel getLabel() {
    return label;
  }

}
