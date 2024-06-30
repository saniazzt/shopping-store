import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ShoppingPanel extends JFrame {

    public static final String USER_DATA_FILE = "users.dat";

    private JButton registerButton;
    private JButton loginButton;
    private JButton adminLoginButton;
    private ArrayList<User> registeredUsers = new ArrayList<>();


    public ShoppingPanel() {
        setTitle("فروشگاه آنلاین");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel storeNameLabel = new JLabel("نام فروشگاه");
        storeNameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        panel.add(storeNameLabel);

        registerButton = new JButton("ثبت نام");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame registerPage = new JFrame("صفحه ثبت نام");
                registerPage.setSize(400, 300);
                registerPage.setLocationRelativeTo(null);

                JPanel registerPanel = new JPanel();
                registerPanel.setLayout(new GridLayout(8, 2));

                JLabel nameLabel = new JLabel("نام:");
                JTextField nameTextField = new JTextField();
                registerPanel.add(nameLabel);
                registerPanel.add(nameTextField);

                JLabel lastNameLabel = new JLabel("نام خانوادگی:");
                JTextField lastNameTextField = new JTextField();
                registerPanel.add(lastNameLabel);
                registerPanel.add(lastNameTextField);

                JLabel phoneNumberLabel = new JLabel("شماره تلفن همراه:");
                JTextField phoneNumberTextField = new JTextField();
                registerPanel.add(phoneNumberLabel);
                registerPanel.add(phoneNumberTextField);

                JLabel usernameLabel = new JLabel("نام کاربری:");
                JTextField usernameTextField = new JTextField();
                registerPanel.add(usernameLabel);
                registerPanel.add(usernameTextField);

                JLabel passwordLabel = new JLabel("رمز عبور:");
                JPasswordField passwordField = new JPasswordField();
                registerPanel.add(passwordLabel);
                registerPanel.add(passwordField);

                JLabel confirmPasswordLabel = new JLabel("تکرار رمز عبور:");
                JPasswordField confirmPasswordField = new JPasswordField();
                registerPanel.add(confirmPasswordLabel);
                registerPanel.add(confirmPasswordField);

                JButton submitButton = new JButton("ثبت نام");
                JButton backButton = new JButton("بازگشت");

                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameTextField.getText();
                        String lastName = lastNameTextField.getText();
                        String phoneNumber = phoneNumberTextField.getText();
                        String username = usernameTextField.getText();
                        String password = new String(passwordField.getPassword());
                        String confirmPassword = new String(confirmPasswordField.getPassword());

                        User user = new User(name, lastName, phoneNumber, username, password);

                        boolean hasError = false;
                        StringBuilder errorMessage = new StringBuilder();

                        // اعتبارسنجی شماره تلفن همراه
                        if (phoneNumber.length() != 11) {
                            hasError = true;
                            errorMessage.append("شماره تلفن همراه باید 11 رقم باشد.\n");
                            phoneNumberTextField.setBackground(Color.RED);
                        } else {
                            phoneNumberTextField.setBackground(Color.WHITE);
                        }

                        // اعتبارسنجی رمز عبور و تکرار رمز عبور
                        if (!password.equals(confirmPassword)) {
                            hasError = true;
                            errorMessage.append("رمز عبور و تکرار رمز عبور باید یکسان باشند.\n");
                            passwordField.setBackground(Color.RED);
                            confirmPasswordField.setBackground(Color.RED);
                        } else {
                            passwordField.setBackground(Color.WHITE);
                            confirmPasswordField.setBackground(Color.WHITE);
                        }


                        if (hasError) {
                            JOptionPane.showMessageDialog(registerPage, errorMessage.toString(), "خطا", JOptionPane.ERROR_MESSAGE);
                        } else {
                            // ثبت نام موفقیت‌آمیز است
                            // اضافه کردن اطلاعات کاربر به پایگاه داده یا سایر عملیات مربوطه
                            // registeredUsers.add(user);
                            UserManager.saveUser(user);

                            // نمایش پیام موفقیت‌آمیز
                            JOptionPane.showMessageDialog(registerPage, "ثبت نام با موفقیت انجام شد.", "موفقیت", JOptionPane.INFORMATION_MESSAGE);

                            // پاک کردن فیلدها
                            nameTextField.setText("");
                            lastNameTextField.setText("");
                            phoneNumberTextField.setText("");
                            usernameTextField.setText("");
                            passwordField.setText("");
                            confirmPasswordField.setText("");
                        }
                    }
                });

                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        registerPage.dispose();
                    }
                });

                registerPanel.add(submitButton);
                registerPanel.add(backButton);

                registerPage.add(registerPanel);
                registerPage.setVisible(true);
            }
        });

        loginButton = new JButton("ورود");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame loginPage = new JFrame("صفحه ورود");
                loginPage.setSize(400, 200);
                loginPage.setLocationRelativeTo(null);

                JPanel loginPanel = new JPanel();
                loginPanel.setLayout(new GridLayout(3, 2));

                JLabel usernameLabel = new JLabel("نام کاربری:");
                JTextField usernameTextField = new JTextField();
                loginPanel.add(usernameLabel);
                loginPanel.add(usernameTextField);

                JLabel passwordLabel = new JLabel("رمز عبور:");
                JPasswordField passwordField = new JPasswordField();
                loginPanel.add(passwordLabel);
                loginPanel.add(passwordField);

                JButton submitButton = new JButton("ورود");
                JButton backButton = new JButton("بازگشت");

                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = usernameTextField.getText();
                        String password = new String(passwordField.getPassword());

                        // اعتبارسنجی نام کاربری و رمز عبور
                        if (username.isEmpty() || password.isEmpty()) {
                            JOptionPane.showMessageDialog(loginPage, "نام کاربری و رمز عبور را وارد کنید.", "خطا", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // اعتبارسنجی نام کاربری و رمز عبور با اطلاعات ثبت نام شده
                        // اگر اطلاعات صحیح نبودند، پیام خطا نمایش داده می‌شود
                        // اگر اطلاعات صحیح بودند، صفحه سفید می‌شود

                        if (isValidLogin(username, password)) {
                            JOptionPane.showMessageDialog(loginPage, "ورود موفقیت‌آمیز بود.", "موفقیت", JOptionPane.INFORMATION_MESSAGE);
                            loginPage.dispose();
                            User thisUser = new User(" ", " ", " ", "guest", " ");
                            for (User user : registeredUsers){
                                if (user.getUsername().equals(username)){      
                                    thisUser = user;
                                }
                            }
                            new UserPanel(thisUser);

                        } else {
                            JOptionPane.showMessageDialog(loginPage, "نام کاربری یا رمز عبور اشتباه است.", "خطا", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loginPage.dispose();
                    }
                });

                loginPanel.add(submitButton);
                loginPanel.add(backButton);

                loginPage.add(loginPanel);
                loginPage.setVisible(true);
            }
        });

        adminLoginButton = new JButton("ورود مدیر");
        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame adminLoginPage = new JFrame("ورود مدیر");
                adminLoginPage.setSize(400, 200);
                adminLoginPage.setLocationRelativeTo(null);

                JPanel adminLoginPanel = new JPanel();
                adminLoginPanel.setLayout(new GridLayout(3, 2));

                JLabel adminUsernameLabel = new JLabel("نام کاربری مدیر:");
                JTextField adminUsernameTextField = new JTextField();
                adminLoginPanel.add(adminUsernameLabel);
                adminLoginPanel.add(adminUsernameTextField);

                JLabel adminPasswordLabel = new JLabel("رمز عبور مدیر:");
                JPasswordField adminPasswordField = new JPasswordField();
                adminLoginPanel.add(adminPasswordLabel);
                adminLoginPanel.add(adminPasswordField);

                JButton adminSubmitButton = new JButton("ورود");
                JButton adminBackButton = new JButton("بازگشت");

                adminSubmitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String adminUsername = adminUsernameTextField.getText();
                        String adminPassword = new String(adminPasswordField.getPassword());

                        // اعتبارسنجی نام کاربری مدیر و رمز عبور مدیر
                        if (isValidAdminLogin(adminUsername, adminPassword)) {
                            JOptionPane.showMessageDialog(adminLoginPage, "ورود مدیر موفقیت‌آمیز بود.", "موفقیت", JOptionPane.INFORMATION_MESSAGE);
                            adminLoginPage.dispose();
                            new AdminPanel();

                        } else {
                            JOptionPane.showMessageDialog(adminLoginPage, "نام کاربری یا رمز عبور مدیر اشتباه است.", "خطا", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                adminBackButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        adminLoginPage.dispose();
                        // new UserPanel();
                    }
                });

                adminLoginPanel.add(adminSubmitButton);
                adminLoginPanel.add(adminBackButton);

                adminLoginPage.add(adminLoginPanel);
                adminLoginPage.setVisible(true);
            }
        });

        panel.add(adminLoginButton);

        panel.add(registerButton);
        panel.add(loginButton);
        panel.add(adminLoginButton);

        add(panel);
        setVisible(true);
    }

    // private boolean isValidLogin(String username, String password) {
    //     // اینجا می‌توانید عملیات اعتبارسنجی نام کاربری و رمز عبور را انجام دهید
    //     // مثال: اگر نام کاربری و رمز عبور غیر خالی بودند، ورود را تأیید کنید
    //     if (!username.isEmpty() && !password.isEmpty()) {
    //         for (User user : registeredUsers){

    //             if (user.getUsername().equals(username)){
    //                 if(user.getPassword().equals(password)){
    //                     return true;
    //                 }
    //             }
    //         }
        
    //     }
    //     return false;
    // }

    private static boolean isValidLogin(String username, String password) {
        ArrayList<User> users = UserManager.loadUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidAdminLogin(String adminUsername, String adminPassword) {
        return adminUsername.equals("admin") && adminPassword.equals("1234");
    }

    public static void main(String[] args) {
        new ShoppingPanel();
    }
}

class UserManager {
    public static void saveUser(User user) {
        ArrayList<User> users = loadUsers();
        users.add(user);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(ShoppingPanel.USER_DATA_FILE));
            outputStream.writeObject(users);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(ShoppingPanel.USER_DATA_FILE));
            users = (ArrayList<User>) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            // Ignore if the file doesn't exist or cannot be read
        }
        return users;
    }
}

