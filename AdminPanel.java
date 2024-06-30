import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AdminPanel extends JFrame {
    private ArrayList<Product> items;
    private JPanel shoppingCartPanel;
    private JTextField newItemNameField,newItemPriceField;
    private ArrayList<User> registeredUsers;

    public AdminPanel() {
        registeredUsers = loadUsers();

        setTitle("پنل مدیریت");
        setSize(700, 400);
        setLayout(new GridLayout(5,3));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        items = loadItemsFromFile();
        shoppingCartPanel = new JPanel(new GridLayout(0, 3));
        displayItems();
        add(shoppingCartPanel, BorderLayout.CENTER);

        newItemNameField = new JTextField(20);
        newItemPriceField =  new JTextField(20);

        JButton addItemButton = new JButton("افزودن محصول");
        JButton searchButton = new JButton("جستجو");
        JButton sortPriceButton = new JButton("مرتب سازی بر اساس قیمت");
        JButton sortScoreButton = new JButton("مرتب سازی بر اساس امتیاز");
        JButton showUsersButton = new JButton("نمایش کاربران");

        JPanel addNewItemPanel = new JPanel();

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product newProduct;
                newProduct = new Product(newItemNameField.getText()+"  "+newItemPriceField.getText()+"t",
                                                Integer.valueOf(newItemPriceField.getText()),0,0);
                if (newProduct!=null) {

                    shoppingCartPanel.removeAll();
                    items.add(newProduct);

                    displayItems();

                    shoppingCartPanel.revalidate();
                    shoppingCartPanel.repaint();

                    newItemNameField.setText("");
                    newItemPriceField.setText("");
                }
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

        showUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String users = "";
                int t = 1;
                for (User user : registeredUsers) {
                    users += t++ +"-"+ user.getUsername() +"\n";
                }
                JOptionPane.showMessageDialog(null, "کاربران:\n " + users);
            }
        });

        addNewItemPanel.add(new JLabel("نام محصول:"));
        addNewItemPanel.add(newItemNameField);
        addNewItemPanel.add(new JLabel("قیمت محصول:"));
        addNewItemPanel.add(newItemPriceField);
        addNewItemPanel.add(addItemButton);
        addNewItemPanel.add(searchButton);
        addNewItemPanel.add(sortPriceButton);
        addNewItemPanel.add(sortScoreButton);
        addNewItemPanel.add(showUsersButton);

        add(addNewItemPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveItemsToFile(items);
            }
        });

        setVisible(true);
    }

    public void displayItems(){

        for (Product item : items) {
            JLabel itemLabel = new JLabel(item.getName());
            JLabel itemScore = new JLabel();
            JButton removeFromCartBtn = new JButton("حذف از فروشگاه");

            if (item.getScore() != 0.0)
                itemScore.setText(String.valueOf(item.getScore()));
            else
                itemScore.setText("بدون امتیاز");

            removeFromCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    items.remove(item);
                    shoppingCartPanel.remove(itemLabel);
                    shoppingCartPanel.remove(removeFromCartBtn);
                    shoppingCartPanel.remove(itemScore);
                    shoppingCartPanel.revalidate();
                    shoppingCartPanel.repaint();
                    JOptionPane.showMessageDialog(null, "محصول " + item.getName() + " از فروشگاه حذف شد.");
                }
            });

            shoppingCartPanel.add(itemLabel);
            shoppingCartPanel.add(itemScore);
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
        new AdminPanel();
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
