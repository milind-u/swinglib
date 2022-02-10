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
  // Typical uses of swinglib have a class, Screen, that extends
  // AbstractScreen and creates and uses JComponents.
  private static class Screen extends AbstractScreen {
    
    /**
     * An enum for the different types of ages.
     */
    private static enum Age {
      CHILD, ADULT;
      
      @Override
      public String toString() {
        // Returns the lowercase name (child or adult)
        return super.toString().toLowerCase();
      }
    }
    
    // Declare components as instance variables
    private final JTextField nameField;
    private final JComboBox<Age> ageBox;
    private final DefaultTableModel table;

    public Screen() {
      // Display this as the title
      super("People Database");

      // Create a name text field to get the person's name.
      // Call .get() to get the JTextField from the LabeledComponent.
      // If you wanted to move/hide the field during the program, you would have to
      // keep a reference to the LabeledComponent and call those methods on it,
      // to move/hide both the label and the component.
      nameField = newTextField("Name", Fonts.MEDIUM, fractionOfHeight(0.25)).get();

      // Create a drop down box where they can select an option from the possible ages (child or adult).
      // Age.values() returns Age.CHILD and Age.ADULT as an array.
      // Again, call .get() to get the JComboBox from the LabeledComponent.
      ageBox = newComboBox("Age", Fonts.MEDIUM, 
          new Bounds(fractionOfHeight(0.35), STD_TEXT_FIELD_WIDTH, STD_TEXT_FIELD_HEIGHT),
          Age.values()).get();

      // Create a JButton that they can click to add a person.
      // This will call the add method when it is clicked, which will use the name text field
      // and age combo box to add a person to the table.
      // No need to keep a reference to the button here because it will call the add method,
      // but it you wanted to change things about it during the program you would have to.
      newButton("Add person", new Bounds(fractionOfHeight(0.45), STD_BUTTON_WIDTH, STD_BUTTON_HEIGHT), 
          this::add);

      // Create a scrollable table for displaying people.
      // It has two columns: name and age.
      // Get the table model (which is used to add rows) from the returned triplet of
      // JTable, DefaultTablemodel, and JScrollPane using .getSecond().
      table = newLabeledTable(Fonts.MEDIUM, Fonts.MEDIUM, "People", 
          new Bounds(fractionOfHeight(0.65), fractionOfWidth(0.7), fractionOfHeight(0.3)),
          "Name", "Age").getSecond();
    }
    
    private void add() {
      // Add the name (from the text field) and the age (from the combo box)
      // as a new row on the table
      table.addRow(new Object[] {nameField.getText(), ageBox.getSelectedItem()});
    }
    
  } 
  
  /**
   * Runs the UI which lets the user add people to a table
   * @param args Not using command line args
   */
  public static void main(String[] args) {
    // To run a screen, simply instantiate one and call .run().
    var s = new Screen();
    s.run();
  }
  
}