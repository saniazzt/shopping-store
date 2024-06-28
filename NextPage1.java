package next1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NextPage1 extends JFrame {

    private ArrayList<String> items;

    public NextPage1() {
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
                    JOptionPane.showMessageDialog(null, "محصول " + item + " به سبد خرید اضافه شد.");
                }
            });

            removeFromCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    items.remove(item);
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

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newItem = newItemField.getText();
                if (!newItem.isEmpty()) {
                    items.add(newItem);

                    JLabel newItemLabel = new JLabel(newItem);
                    JButton newAddToCartBtn = new JButton("افزودن به سبد خرید");
                    JButton newRemoveFromCartBtn = new JButton("حذف از سبد خرید");

                    newAddToCartBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, "محصول " + newItem + " به سبد خرید اضافه شد.");
                        }
                    });

                    newRemoveFromCartBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            items.remove(newItem);
                            shoppingCartPanel.remove(newItemLabel);
                            shoppingCartPanel.remove(newAddToCartBtn);
                            shoppingCartPanel.remove(newRemoveFromCartBtn);
                            shoppingCartPanel.revalidate();
                            shoppingCartPanel.repaint();
                            JOptionPane.showMessageDialog(null, "محصول " + newItem + " از سبد خرید حذف شد.");
                        }
                    });

                    shoppingCartPanel.add(newItemLabel);
                    shoppingCartPanel.add(newAddToCartBtn);
                    shoppingCartPanel.add(newRemoveFromCartBtn);

                    shoppingCartPanel.revalidate();
                    shoppingCartPanel.repaint();

                    newItemField.setText("");
                }
            }
        });

        addNewItemPanel.add(newItemField);
        addNewItemPanel.add(addItemButton);

        panel.add(addNewItemPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new NextPage1();
    }
}
