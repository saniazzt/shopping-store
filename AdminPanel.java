import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class AdminPanel extends JFrame {
    private ArrayList<Product> items;
    private JPanel shoppingCartPanel;
    private JTextField newItemNameField,newItemPriceField;

    public AdminPanel() {
        setTitle("پنل مدیریت");
        setSize(800, 400);
        setLayout(new GridLayout(5,1));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        items = loadItemsFromFile();
        shoppingCartPanel = new JPanel();
        add(shoppingCartPanel, BorderLayout.CENTER);

        newItemNameField = new JTextField(20);
        newItemPriceField =  new JTextField(20);

        JButton addItemButton = new JButton("افزودن محصول");
        JPanel addNewItemPanel = new JPanel();

        for (Product item : items) {
            JLabel itemLabel = new JLabel(item.getName());
            JButton removeFromCartBtn = new JButton("حذف از فروشگاه");

            removeFromCartBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    items.remove(item);
                    shoppingCartPanel.remove(itemLabel);
                    shoppingCartPanel.remove(removeFromCartBtn);
                    shoppingCartPanel.revalidate();
                    shoppingCartPanel.repaint();
                    JOptionPane.showMessageDialog(null, "محصول " + item + " از سبد خرید حذف شد.");
                }
            });

            shoppingCartPanel.add(itemLabel);
            shoppingCartPanel.add(removeFromCartBtn);
        }

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product newProduct;
                newProduct = new Product(newItemNameField.getText()+"  "+newItemPriceField.getText()+"t",
                                                Integer.valueOf(newItemPriceField.getText()));
                if (newProduct!=null) {

                    items.add(newProduct);

                    JLabel newItemLabel = new JLabel(newProduct.getName());
                    JButton newRemoveFromCartBtn = new JButton("حذف از فروشگاه");

                    newRemoveFromCartBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            items.remove(newProduct);
                            shoppingCartPanel.remove(newItemLabel);
                            shoppingCartPanel.remove(newRemoveFromCartBtn);
                            shoppingCartPanel.revalidate();
                            shoppingCartPanel.repaint();
                            JOptionPane.showMessageDialog(null, "محصول " + newProduct + " از فروشگاه حذف شد.");
                        }
                    });

                    shoppingCartPanel.add(newItemLabel);
                    shoppingCartPanel.add(newRemoveFromCartBtn);

                    shoppingCartPanel.revalidate();
                    shoppingCartPanel.repaint();

                    newItemNameField.setText("");
                    newItemPriceField.setText("");
                }
            }
        });

        addNewItemPanel.add(new JLabel("نام محصول:"));
        addNewItemPanel.add(newItemNameField);
        addNewItemPanel.add(new JLabel("قیمت محصول:"));
        addNewItemPanel.add(newItemPriceField);
        addNewItemPanel.add(addItemButton);

        add(addNewItemPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveItemsToFile(items);
            }
        });

        setVisible(true);
    }

    public void saveItemsToFile(ArrayList<Product> items) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("items.txt"));
            for (Product item : items) {
                writer.write(item.getName()+";"+item.getPrice()+"\n");
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
        new AdminPanel();
    }
}
