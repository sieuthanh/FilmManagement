/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.view;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author CodeBlue
 */
public class Validator {

    public static boolean isEmpty(String text) {
        return text.trim().length() == 0;
    }

    // validate if text fields are empty
    public static boolean validateComponents(JTextComponent[] component) {
        if (component != null) {
            for (int i = 0; i < component.length; i++) {
                component[i].setBackground(Color.white);
                if (isEmpty(component[i].getText())) {
                    showMessage("Empty value is not allowed here");
                    component[i].setBackground(new Color(250, 255, 168));
                    component[i].requestFocus();
                    return false;
                }
            }
        }
        return true;
    }

    public static void showMessage(String message) {
        new CustomMessageDialog(null, true, "Validation Message", message, CustomMessageDialog.MESSAGE);
    }

    public static void setBlankFields(JTextComponent[] tcp) {
        for (int i = 0; i < tcp.length; i++) {
            tcp[i].setText("");
        }
    }

    // validate phone number
    public static boolean validatePhone(JTextField txtPhone) {
        if (!txtPhone.getText().matches("[\\d]{8,15}")) {
            showMessage("Phone is not valid");
            txtPhone.setBackground(new Color(250, 255, 168));
            txtPhone.requestFocus();
            return false;
        }
        return true;
    }

    // validate email
    public static boolean validateEmail(JTextField txtEmail) {
        if (!txtEmail.getText().matches("^(([\\-\\w]+)\\.?)+@(([\\-\\w]+)\\.?)+\\.[a-zA-Z]{2,4}$")) {
            showMessage("Email is not valid");
            txtEmail.setBackground(new Color(250, 255, 168));
            txtEmail.requestFocus();
            return false;
        }
        return true;
    }
    
    // validate email
    public static boolean validateYear(JTextField txtYear) {
        if (!txtYear.getText().matches("^([1][9]\\d\\d|20[0-1][0-2])$")) {
            showMessage("Year is not valid");
            txtYear.setBackground(new Color(250, 255, 168));
            txtYear.requestFocus();
            return false;
        }
        return true;
    }
    
    // validate float number
    public static boolean validateFloatField(JTextField txtNumber) {
        if (!txtNumber.getText().matches("[\\d]{1,3}(\\.[\\d]{1,5})?")) {
            showMessage("Input is not valid float number");
            txtNumber.setBackground(new Color(250, 255, 168));
            txtNumber.requestFocus();
            return false;
        }
        return true;
    }
    
    // validate int number
    public static boolean validateIntField(JTextField txtNumber) {
        if (!txtNumber.getText().matches("^[0-9]{1,10}$")) {
            showMessage("Input is not valid int number");
            txtNumber.setBackground(new Color(250, 255, 168));
            txtNumber.requestFocus();
            return false;
        }
        return true;
    }
}
