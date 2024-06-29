import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NextPage extends JFrame {

    private ArrayList<Product> items;
    private ArrayList<String> shoppingCart;

    public NextPage() {
        setTitle("صفحه فروشگاه");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel shoppingCartPanel = new JPanel(new GridLayout(0, 3));
        items = new ArrayList<>();
        items.add(new Product("پیراهن   50000تومن", 50));
        items.add(new Product("شلوار   70000تومن", 70));
        items.add(new Product(" جوراب   20000تومن", 20));
        items.add(new Product("  کفش     100000 تومن", 100));

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
        JTextField newItemField = new JTextField(10);
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

        addNewItemPanel.add(newItemField);
        addNewItemPanel.add(shoppingCartButton);
        addNewItemPanel.add(searchButton);

        panel.add(addNewItemPanel, BorderLayout.SOUTH);

        shoppingCart = new ArrayList<>();

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new NextPage();
    }
}

class Product {
    private String name;
    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
