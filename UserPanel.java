import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserPanel extends JFrame {

    private ArrayList<Product> items;
    private ArrayList<String> shoppingCart;

    public UserPanel() {
        setTitle("صفحه اصلی فروشگاه");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel shoppingCartPanel = new JPanel(new GridLayout(0, 3));
        items = loadItemsFromFile();


        for (Product item : items) {
            JLabel itemLabel = new JLabel(item.getName());
            JButton addToCartBtn = new JButton("افزودن به سبد خرید");
            JButton removeFromCartBtn = new JButton("حذف از سبد خرید");

            addToCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shoppingCart.add(item.getName());
                    JOptionPane.showMessageDialog(null, "محصول " + item.getName() + " به سبد خرید اضافه شد.");
                }
            });

            removeFromCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shoppingCart.remove(item.getName());
                    shoppingCartPanel.remove(itemLabel);
                    shoppingCartPanel.remove(addToCartBtn);
                    shoppingCartPanel.remove(removeFromCartBtn);
                    shoppingCartPanel.revalidate();
                    shoppingCartPanel.repaint();
                    JOptionPane.showMessageDialog(null, "محصول " + item.getName() + " از سبد خرید حذف شد.");
                }
            });

            shoppingCartPanel.add(itemLabel);
            shoppingCartPanel.add(addToCartBtn);
            shoppingCartPanel.add(removeFromCartBtn);
        }

        panel.add(shoppingCartPanel, BorderLayout.CENTER);

        JPanel addNewItemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton shoppingCartButton = new JButton("سبد خرید");
        JButton searchButton = new JButton("جستجو");
        JButton sortPriceButton = new JButton("مرتب سازی بر اساس قیمت");
        addNewItemPanel.add(sortPriceButton);

        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cartItems = "";
                for (String item : shoppingCart) {
                    cartItems += item + "\n";
                }
                JOptionPane.showMessageDialog(null, "کالاهای موجود در سبد خرید:\n" + cartItems);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchItem = JOptionPane.showInputDialog("لطفا نام محصول را وارد کنید:");
                boolean found = false;
                for (Product item : items) {
                    if (item.getName().equals(searchItem)) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    JOptionPane.showMessageDialog(null, "محصول " + searchItem + " در فروشگاه موجود است.");
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
                displayItems();
            }

            private void displayItems() {
                shoppingCartPanel.removeAll();
                for (Product item : items) {
                    JLabel itemLabel = new JLabel(item.getName());
                    JButton addToCartBtn = new JButton("افزودن به سبد خرید");
                    JButton removeFromCartBtn = new JButton("حذف از سبد خرید");


                    addToCartBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            shoppingCart.add(item.getName());
                            JOptionPane.showMessageDialog(null, "محصول " + item.getName() + " به سبد خرید اضافه شد.");
                        }
                    });

                    removeFromCartBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            shoppingCart.remove(item.getName());
                            shoppingCartPanel.remove(itemLabel);
                            shoppingCartPanel.remove(addToCartBtn);
                            shoppingCartPanel.remove(removeFromCartBtn);
                            shoppingCartPanel.revalidate();
                            shoppingCartPanel.repaint();
                            JOptionPane.showMessageDialog(null, "محصول " + item.getName() + " از سبد خرید حذف شد.");
                        }
                    });

                    shoppingCartPanel.add(itemLabel);
                    shoppingCartPanel.add(addToCartBtn);
                    shoppingCartPanel.add(removeFromCartBtn);
                }
                shoppingCartPanel.revalidate();
                shoppingCartPanel.repaint();
            }
        });

        addNewItemPanel.add(shoppingCartButton);
        addNewItemPanel.add(searchButton);

        panel.add(addNewItemPanel, BorderLayout.SOUTH);

        shoppingCart = new ArrayList<>();

        add(panel);
        setVisible(true);
    }

    public ArrayList<Product> loadItemsFromFile() {
        ArrayList<Product> items = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("items.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(";");
                Product p = new Product(info[0],Integer.valueOf(info[1]));
                items.add(p);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void main(String[] args) {
        new UserPanel();
    }
}

