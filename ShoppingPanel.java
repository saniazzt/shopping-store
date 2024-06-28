

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.JOptionPane;

public class ShoppingPanel extends JFrame {
    private JPanel panel;
    private JPanel registerPanel;

    public ShoppingPanel() {
        setTitle("Shopping Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridLayout(4, 2));
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        String[] items = {"کفش", "جوراب", "پیراهن", "شلوار"};

        for (String item : items) {
            JLabel label = new JLabel(item);
            JButton addButton = new JButton("افزودن به سبد خرید");
            addButton.setBackground(Color.LIGHT_GRAY);
            addButton.setBorder(border);

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "محصول " + item + " به سبد خرید اضافه شد.");
                }
            });
            

            panel.add(label);
            panel.add(addButton);
        }

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginButton = new JButton("ورود");
        JButton registerButton = new JButton("ثبت نام");

        topPanel.add(loginButton);
        topPanel.add(registerButton);

        add(topPanel, BorderLayout.NORTH);

        registerPanel = new JPanel(new GridLayout(8, 2));
        JLabel nameLabel = new JLabel("نام:");
        JTextField nameField = new JTextField();
        JLabel lastNameLabel = new JLabel("نام خانوادگی:");
        JTextField lastNameField = new JTextField();
        JLabel numberLabel = new JLabel("شماره:");
        JTextField numberField = new JTextField();
        JLabel usernameLabel = new JLabel("نام کاربری:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("رمز عبور:");
        JPasswordField passwordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("تکرار رمز عبور:");
        JPasswordField confirmPasswordField = new JPasswordField();
        JButton registerSubmitButton = new JButton("ثبت نام");

        registerSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isError = false;

                if (nameField.getText().isEmpty()) {
                    nameField.setBackground(Color.RED);
                    isError = true;
                } else {
                    nameField.setBackground(Color.WHITE);
                }

                if (lastNameField.getText().isEmpty()) {
                    lastNameField.setBackground(Color.RED);
                    isError = true;
                } else {
                    lastNameField.setBackground(Color.WHITE);
                }

                if (numberField.getText().isEmpty()) {
                    numberField.setBackground(Color.RED);
                    isError = true;
                } else {
                    numberField.setBackground(Color.WHITE);
                }

                if (usernameField.getText().isEmpty()) {
                    usernameField.setBackground(Color.RED);
                    isError = true;
                } else {
                    usernameField.setBackground(Color.WHITE);
                }

                if (passwordField.getPassword().length == 0) {
                    passwordField.setBackground(Color.RED);
                    isError = true;
                } else {
                    passwordField.setBackground(Color.WHITE);
                }

                if (confirmPasswordField.getPassword().length == 0) {
                    confirmPasswordField.setBackground(Color.RED);
                    isError = true;
                } else {
                    confirmPasswordField.setBackground(Color.WHITE);
                }

                if (isError) {
                    JOptionPane.showMessageDialog(null, "لطفاً تمام فیلدها را پر کنید.", "خطا", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "ثبت نام با موفقیت انجام شد.");
                }
            }
        });

        registerPanel.add(nameLabel);
        registerPanel.add(nameField);
        registerPanel.add(lastNameLabel);
        registerPanel.add(lastNameField);
        registerPanel.add(numberLabel);
        registerPanel.add(numberField);
        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(confirmPasswordLabel);
        registerPanel.add(confirmPasswordField);
        registerPanel.add(registerSubmitButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(panel);
                add(registerPanel);
                revalidate();
                repaint();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(panel);
                add(registerPanel);
                revalidate();
                repaint();
            }
        });
        // ... (previous code)

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField loginUsernameField = new JTextField(10);
                JPasswordField loginPasswordField = new JPasswordField(10);

                JPanel loginPanel = new JPanel();
                loginPanel.add(new JLabel("نام کاربری:"));
                loginPanel.add(loginUsernameField);
                loginPanel.add(new JLabel("رمز عبور:"));
                loginPanel.add(loginPasswordField);

                int result = JOptionPane.showConfirmDialog(null, loginPanel, "ورود", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String enteredUsername = loginUsernameField.getText();
                    String enteredPassword = new String(loginPasswordField.getPassword());

                    if (enteredUsername.equals(usernameField.getText()) && enteredPassword.equals(new String(passwordField.getPassword()))) {
                        JOptionPane.showMessageDialog(null, "ورود موفقیت آمیز بود.");
                        // Clear fields or take other action upon successful login
                    } else {
                        JOptionPane.showMessageDialog(null, "نام کاربری یا رمز عبور اشتباه است.");
                    }
                }
            }

        });
        JPanel shoppingCartPanel = new JPanel(new GridLayout(4, 2));
        items = new String[]{"پیراهن", "شلوار", "جوراب", "کفش"};

        for (String item : items) {
            JLabel itemLabel = new JLabel(item);
            JButton addToCartBtn = new JButton("افزودن به سبد خرید");
            JButton removeFromCartBtn = new JButton("حذف از سبد خرید");

            addToCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "محصول " + item + " به سبد خرید اضافه شد.");
                }
            });

            removeFromCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "محصول " + item + " از سبد خرید حذف شد.");
                }
            });

            shoppingCartPanel.add(itemLabel);
            shoppingCartPanel.add(addToCartBtn);
            shoppingCartPanel.add(removeFromCartBtn);
        }

        add(panel);
        setVisible(true);
    }
    public static void main(String[] args) {
        new ShoppingPanel();
    }
}

