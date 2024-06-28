package shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NextPage extends JFrame {
    private List<String> shoppingCart = new ArrayList<>();

    public NextPage() {
        setTitle("صفحه خرید");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JButton shirtButton = new JButton("پیراهن");
        JButton pantsButton = new JButton("شلوار");
        JButton shoesButton = new JButton("کفش");
        JButton socksButton = new JButton("جوراب");

        JButton addToCartButton = new JButton("اضافه کردن به سبد خرید");
        JButton removeFromCartButton = new JButton("حذف از سبد خرید");

        JButton viewCartButton = new JButton("مشاهده لیست خرید");

        shirtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCart.add("پیراهن");
            }
        });

        pantsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCart.add("شلوار");
            }
        });

        shoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCart.add("کفش");
            }
        });

        socksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCart.add("جوراب");
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // اضافه کردن ایتم به سبد خرید
            }
        });

        removeFromCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // حذف ایتم از سبد خرید
            }
        });

        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // نمایش لیست خرید
                StringBuilder cartItems = new StringBuilder("لیست خرید:\n");
                for (String item : shoppingCart) {
                    cartItems.append(item).append("\n");
                }
                JOptionPane.showMessageDialog(panel, cartItems.toString(), "سبد خرید", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(shirtButton);
        panel.add(addToCartButton);

        panel.add(pantsButton);
        panel.add(removeFromCartButton);

        panel.add(shoesButton);
        panel.add(new JLabel()); // جای خالی برای فاصله

        panel.add(socksButton);
        panel.add(new JLabel()); // جای خالی برای فاصله

        panel.add(viewCartButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        NextPage shoppingCartPage = new NextPage();
        shoppingCartPage.setVisible(true);
    }
}