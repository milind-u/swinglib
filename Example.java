package swinglib;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Simple example program demonstrating swinglib's capabilities
 * @author milind
 */
public final class Example {

  /**
   * JPanel example that adds people to a table
   * @author milind
   */
  protected static class Screen extends AbstractScreen {
    
    private static enum Age {
      CHILD, ADULT;
      
      @Override
      public String toString() {
        return super.toString().toLowerCase();
      }
    }
    
    private final JTextField nameField;
    private final JComboBox<Age> ageBox;
    private final DefaultTableModel table;

    public Screen() {
      super("People Database");
      
      nameField = newTextField("Name", Fonts.MEDIUM, fractionOfHeight(0.25)).get();
      ageBox = newComboBox("Age", Fonts.MEDIUM, 
          new Bounds(fractionOfHeight(0.35), STD_TEXT_FIELD_WIDTH, STD_TEXT_FIELD_HEIGHT),
          Age.values()).get();
      newButton("Add person", new Bounds(fractionOfHeight(0.45), STD_BUTTON_WIDTH, STD_BUTTON_HEIGHT), 
          this::add);
      table = newLabeledTable(Fonts.MEDIUM, Fonts.MEDIUM, "People", 
          new Bounds(fractionOfHeight(0.65), fractionOfWidth(0.7), fractionOfHeight(0.3)),
          "Name", "Age").getSecond();
    }
    
    private void add() {
      table.addRow(new Object[] {nameField.getText(), ageBox.getSelectedItem()});
    }
    
  } 
  
  /**
   * Runs the UI which lets the user add people to a table
   * @param args Not using command line args
   */
  public static void main(String[] args) {
    var s = new Screen();
    s.run();
  }
  
}