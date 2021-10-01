package swinglib;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LabeledComponent<C extends Component> extends JComponent {

  private static final long serialVersionUID = 9218446090131412904L;

  private final C component;
  private final JLabel label;

  public LabeledComponent(C component, String label, Font labelFont,
      AbstractScreen s) {
    this.component = component;
    this.label = new JLabel(label + ':', SwingConstants.RIGHT);
    final int labelWidth = (labelFont.getSize() * label.length());
    this.label.setBounds(component.getX() - labelWidth - labelFont.getSize(),
        component.getY(), labelWidth, labelFont.getSize());
    this.label.setFont(labelFont);
    this.label.setVisible(component.isVisible());
    s.add(this.label);
    s.add(component);
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
