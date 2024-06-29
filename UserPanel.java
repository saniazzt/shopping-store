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
    private ArrayList<Product> shoppingCart;
    private Wallet wallet;

    public UserPanel() {
        setTitle("صفحه اصلی فروشگاه");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel shoppingCartPanel = new JPanel(new GridLayout(0, 3));
        items = loadItemsFromFile();

        wallet = new Wallet(600);

        for (Product item : items) {
            JLabel itemLabel = new JLabel(item.getName());
            JButton addToCartBtn = new JButton("افزودن به سبد خرید");
            JButton removeFromCartBtn = new JButton("حذف از سبد خرید");

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

                        if (wallet.canAfford(overalPrice)){

                            wallet.withdraw(overalPrice);

                            JOptionPane.showMessageDialog(null, "خرید با موفقیت ثبت شد." +
                                                                 "\n موجودی کیف پول: " + wallet.getBalance());
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
                            if (wallet.canAfford(item.getPrice())) {
                                shoppingCart.add(item);
                                wallet.withdraw(item.getPrice());
                                JOptionPane.showMessageDialog(null, "محصول " + item.getName() + " به سبد خرید اضافه شد.");
                            } else {
                                JOptionPane.showMessageDialog(null, "موجودی کافی نیست. لطفا مبلغ بیشتری به کیف پول اضافه کنید.");
                            }
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
