package swinglib;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LabeledComponent<C extends Component> extends JComponent {

  private static final long serialVersionUID = 1L;

  private final C component;
  private final JLabel label;

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

  public LabeledComponent(C component, String label, Font labelFont, AbstractScreen s) {
    this(component, label, labelFont, s,
        component.getX() - bufferedStringWidth(label, labelFont) - labelFont.getSize(),
        component.getY());
  }

  public LabeledComponent(C component, String label, AbstractScreen s) {
    this(component, label, component.getFont(), s);
  }

  public static int bufferedStringWidth(String s, Font font) {
    return font.getSize() * s.length();
  }

  public static int bufferedStringHeight(Font font) {
    return (int) (font.getSize() * 1.2);
  }

  @Override
  public void setVisible(boolean aFlag) {
    component.setVisible(aFlag);
    label.setVisible(aFlag);
  }

  public C get() {
    return component;
  }

  public JLabel getLabel() {
    return label;
  }

}
