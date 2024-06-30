import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

public class UserPanel extends JFrame {

    public JPanel shoppingCartPanel = new JPanel(new GridLayout(0, 5));
    private ArrayList<Product> items;
    private ArrayList<Product> shoppingCart = new ArrayList<>();
    private Wallet wallet;

    public UserPanel(User user) {

        setTitle("صفحه اصلی فروشگاه");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton profileButton = new JButton("پروفایل کاربر" + user.getUsername());
        profilePanel.add(profileButton);

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame ProfilePage = new JFrame("پروفایل کاربر");
                ProfilePage.setSize(300, 600);
                ProfilePage.setLocationRelativeTo(null);

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(0, 1));

                JLabel passwordLabel = new JLabel("رمز عبور قدیمی:");
                JPasswordField passwordField = new JPasswordField();


                JLabel confirmPasswordLabel = new JLabel("رمز عبور جدید: ");
                JPasswordField confirmPasswordField = new JPasswordField();
                panel.add(confirmPasswordLabel);
                panel.add(confirmPasswordField);

                JButton passChange = new JButton("تغییر رمز عبور");

                passChange.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String password = new String(passwordField.getPassword());
                        String newPassword = new String(confirmPasswordField.getPassword());

                        StringBuilder errorMessage = new StringBuilder();

                        if (!password.equals(user.getPassword())) {
                            errorMessage.append("رمز عبور اشتباه است.\n");
                            passwordField.setBackground(Color.RED);
                            confirmPasswordField.setBackground(Color.RED);
                            JOptionPane.showMessageDialog(null, errorMessage.toString(), "خطا", JOptionPane.ERROR_MESSAGE);
                            passwordField.setText("");
                            confirmPasswordField.setText("");
                        } else {
                            user.setPassword(newPassword);
                            passwordField.setBackground(Color.WHITE);
                            confirmPasswordField.setBackground(Color.WHITE);
                            JOptionPane.showMessageDialog(null, "رمز عبور با موفقیت تغییر کرد.");
                        }
                    }
                });

                panel.add(new JLabel("نام: "+ user.getName()));
                panel.add(new JLabel("نام خانوادگی: " + user.getLastName()));
                panel.add(new JLabel("شماره تلفن: " + user.getPhoneNumber()));
                panel.add(new JLabel("نام کاربری: " + user.getUsername()));
                panel.add(new JLabel("موجودی کیف پول: " + user.getWallet().getBalance() + " تومان"));

                panel.add(passwordLabel);
                panel.add(passwordField);
                panel.add(confirmPasswordLabel);
                panel.add(confirmPasswordField);

                panel.add(passChange);

                ProfilePage.add(panel);
                ProfilePage.setVisible(true);

            }
        });

        panel.add(profilePanel, BorderLayout.NORTH);
        
        //منوی محصولات 

        items = loadItemsFromFile();
        displayItems();
        panel.add(shoppingCartPanel, BorderLayout.CENTER);

        //منوی جستجو و سبد خرید و مرتب سازی محصولات 

        JPanel addNewItemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton shoppingCartButton = new JButton("سبد خرید");
        JButton searchButton = new JButton("جستجو");
        JButton sortPriceButton = new JButton("مرتب سازی بر اساس قیمت");
        JButton sortScoreButton = new JButton("مرتب سازی بر اساس امتیاز");


        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(user.getUsername());

                String cartItems = "";
                int total = 0;
                for (Product item : shoppingCart) {
                    cartItems += item.getName() + "\n";
                    total += item.getPrice();
                }
                final int overalPrice = total;

                JFrame CheckoutPage = new JFrame("سبد خرید");
                CheckoutPage.setSize(300, 500);
                CheckoutPage.setLocationRelativeTo(null);

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(5, 1));

                panel.add(new JLabel("کالاهای موجود در سبد خرید:\n"));
                panel.add(new JLabel(cartItems));
                panel.add(new JLabel("مجموع قیمت: " + overalPrice + " تومان"));
                panel.add(new JLabel("موجودی کیف پول: " + wallet.getBalance() + " تومان"));

                JButton checkout = new JButton("ثبت نهایی");

                checkout.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (overalPrice == 0){
                            JOptionPane.showMessageDialog(null, "محصولی انتخاب نشده است.");
                            return;
                        }

                        if (user.getWallet().canAfford(overalPrice)){

                            user.getWallet().withdraw(overalPrice);

                            JOptionPane.showMessageDialog(null, "خرید با موفقیت ثبت شد." +
                                                                 "\n موجودی کیف پول: " + user.getWallet().getBalance());
                            shoppingCart.clear();                                 
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "موجودی کافی نیست. لطفا مبلغ بیشتری به حساب اضافه کنید.");
                        }
                    }
                });

                panel.add(checkout);
                
                CheckoutPage.add(panel);
                CheckoutPage.setVisible(true);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchItem = JOptionPane.showInputDialog("لطفا نام محصول را وارد کنید:");
                boolean found = false;
                String foundedList = "";
                for (Product item : items) {
                    if (item.getName().contains(searchItem)) {
                        found = true;
                        foundedList += item.getName() + "\n";
                    }
                }
                if (found) {
                    JOptionPane.showMessageDialog(null, "محصول " + searchItem + " در فروشگاه موجود است." + "\nنتایچ جستجو: " + foundedList);
                } else {
                    JOptionPane.showMessageDialog(null, "محصول " + searchItem + " در فروشگاه موجود نیست.");
                }
            }
        });

        
        sortPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(items, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return Integer.compare(p1.getPrice(), p2.getPrice());
                    }
                });
                
                // بازسازی نمایش کالاها بر روی صفحه
                shoppingCartPanel.removeAll();

                displayItems();
                
                shoppingCartPanel.revalidate();
                shoppingCartPanel.repaint();
            }
        });

        sortScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(items, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return Integer.compare((int)p1.getScore(), (int)p2.getScore());
                    }
                });
                
                // بازسازی نمایش کالاها بر روی صفحه
                shoppingCartPanel.removeAll();

                displayItems();
                
                shoppingCartPanel.revalidate();
                shoppingCartPanel.repaint();
            }
        });

        addNewItemPanel.add(sortPriceButton);
        addNewItemPanel.add(sortScoreButton);
        addNewItemPanel.add(shoppingCartButton);
        addNewItemPanel.add(searchButton);

        panel.add(addNewItemPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveItemsToFile(items);
            }
        });
    }

    //نمایش منوی محصولات

    public void displayItems() {

        for (Product item : items) {

            JLabel itemLabel = new JLabel(item.getName());
            JLabel itemScore = new JLabel();
            JButton scoreBtn = new JButton("امتیازدهی");
            JButton addToCartBtn = new JButton("افزودن به سبد خرید");
            JButton removeFromCartBtn = new JButton("حذف از سبد خرید");

            if (item.getScore() != 0.0)
                itemScore.setText(String.valueOf(item.getScore()));
            else
                itemScore.setText("بدون امتیاز");


            scoreBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String userScore = JOptionPane.showInputDialog("لطفا امتیازی که به محصول می‌دهید را وارد کنید: (5-0)");

                    if (Float.valueOf(userScore)<=5 && Float.valueOf(userScore)>=0){

                    float newScore = (Float.valueOf(userScore) + item.getScoredNum()*item.getScore())/(item.getScoredNum()+1);
                    item.setScore(newScore);
                    item.setScoredNum(item.getScoredNum()+1);

                    if (item.getScore() != 0.0)
                        itemScore.setText(String.valueOf(item.getScore()));
                    else
                        itemScore.setText("بدون امتیاز");

                    scoreBtn.setEnabled(false);
                    shoppingCartPanel.revalidate();
                    shoppingCartPanel.repaint();

                    } else {
                        JOptionPane.showMessageDialog(null, "لطفا امتیازی بین ۰ تا ۵ وارد کنید.");
                    }

                }
            });

            addToCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    shoppingCart.add(item);
                    JOptionPane.showMessageDialog(null, "محصول " + item.getName() + " به سبد خرید اضافه شد.");
                    
                }
            });

            removeFromCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (shoppingCart.contains(item)){
                        shoppingCart.remove(item);
                        JOptionPane.showMessageDialog(null, "محصول " + item.getName() + " از سبد خرید حذف شد.");
                    }
                }
            });

            shoppingCartPanel.add(itemLabel);
            shoppingCartPanel.add(itemScore);
            shoppingCartPanel.add(scoreBtn);
            shoppingCartPanel.add(addToCartBtn);
            shoppingCartPanel.add(removeFromCartBtn);
        }

    }


    public void saveItemsToFile(ArrayList<Product> items) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("items.txt"));
            for (Product item : items) {
                writer.write( item.getName() +";"+ item.getPrice() +";"+ item.getScore() +";"+ item.getScoredNum() +"\n");
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Product> loadItemsFromFile() {
        ArrayList<Product> items = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("items.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(";");
                Product p = new Product(info[0],Integer.valueOf(info[1]),Float.valueOf(info[2]),Integer.valueOf(info[3]));
                items.add(p);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void main(String[] args) {
        new UserPanel(new User("g", "u", "0", "guest", "123"));
    }
}
