package shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NextPage extends JFrame {

    private ArrayList<String> items;
    private ArrayList<String> shoppingCart;

    public NextPage() {
        setTitle("صفحه فروشگاه");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel shoppingCartPanel = new JPanel(new GridLayout(0, 3));
        items = new ArrayList<>();
        items.add("پیراهن");
        items.add("شلوار");
        items.add("جوراب");
        items.add("کفش");

        for (String item : items) {
            JLabel itemLabel = new JLabel(item);
            JButton addToCartBtn = new JButton("افزودن به سبد خرید");
            JButton removeFromCartBtn = new JButton("حذف از سبد خرید");

            addToCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shoppingCart.add(item);
                    JOptionPane.showMessageDialog(null, "محصول " + item + " به سبد خرید اضافه شد.");
                }
            });

            removeFromCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    shoppingCart.remove(item);
                    shoppingCartPanel.remove(itemLabel);
                    shoppingCartPanel.remove(addToCartBtn);
                    shoppingCartPanel.remove(removeFromCartBtn);
                    shoppingCartPanel.revalidate();
                    shoppingCartPanel.repaint();
                    JOptionPane.showMessageDialog(null, "محصول " + item + " از سبد خرید حذف شد.");
                }
            });

            shoppingCartPanel.add(itemLabel);
            shoppingCartPanel.add(addToCartBtn);
            shoppingCartPanel.add(removeFromCartBtn);
        }

        panel.add(shoppingCartPanel, BorderLayout.CENTER);

        JPanel addNewItemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField newItemField = new JTextField(10);
        JButton addItemButton = new JButton("افزودن کالا");
        JButton shoppingCartButton = new JButton("سبد خرید");
        JButton searchButton = new JButton("جستجو");





        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cartItems = "";
                for (String item : shoppingCart) {
                    cartItems += item + "\\n";
                }
                JOptionPane.showMessageDialog(null, "کالاهای موجود در سبد خرید:\\n" + cartItems);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchItem = JOptionPane.showInputDialog("لطفا نام محصول را وارد کنید:");
                boolean found = false;
                for (String item : items) {
                    if (item.equals(searchItem)) {
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

        addNewItemPanel.add(shoppingCartButton);
        addNewItemPanel.add(searchButton);

        panel.add(addNewItemPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        shoppingCart = new ArrayList<>();
    }

    public static void main(String[] args) {
        new NextPage();
    }
}
