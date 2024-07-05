package hava1;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class HavayoluRezervasyonSistemiGUI extends JFrame {
    private final RezervasyonVeritabani veritabani;
    private JTextArea goruntuAlani;
    private JComboBox<String> koltukComboBox;
    private JButton rezerveButonu;
    private JButton iptalButonu;
    private JButton sorgulaButonu;

    public HavayoluRezervasyonSistemiGUI() {
        veritabani = new RezervasyonVeritabani();
        arayuzuOlustur();
    }

    private void arayuzuOlustur() {
        setTitle("Havayolu Rezervasyon Sistemi");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        goruntuAlani = new JTextArea();
        goruntuAlani.setEditable(false);
        goruntuAlani.setBackground(Color.BLACK);
        goruntuAlani.setForeground(Color.WHITE); // Metin rengi beyaz
        JScrollPane scrollPane = new JScrollPane(goruntuAlani);

        koltukComboBox = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            koltukComboBox.addItem("Koltuk" + i);
        }

        rezerveButonu = new JButton("Rezerve Et");
        rezerveButonu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenKoltuk = (String) koltukComboBox.getSelectedItem();
                new WriterThread(veritabani, secilenKoltuk,true).start();
                guncelleGoruntu();
            }
        });

        iptalButonu = new JButton("İptal Et");
        iptalButonu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenKoltuk = (String) koltukComboBox.getSelectedItem();
                new WriterThread(veritabani, secilenKoltuk,false).start();
                guncelleGoruntu();
            }
        });

        sorgulaButonu = new JButton("Sorgula");
        sorgulaButonu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenKoltuk = (String) koltukComboBox.getSelectedItem();
                new ReaderThread(veritabani, secilenKoltuk).start();
                guncelleGoruntu();
            }
        });

        JPanel kontrolPaneli = new JPanel();
        kontrolPaneli.setLayout(new GridLayout(4, 1, 10, 10));
        kontrolPaneli.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        kontrolPaneli.add(koltukComboBox);
        kontrolPaneli.add(rezerveButonu);
        kontrolPaneli.add(iptalButonu);
        kontrolPaneli.add(sorgulaButonu);

        add(scrollPane, BorderLayout.CENTER);
        add(kontrolPaneli, BorderLayout.EAST);
    }

    private void guncelleGoruntu() {
        Map<String, Boolean> koltuklar = veritabani.getKoltukDurumlari();
        goruntuAlani.setText("Koltuk Durumları:\n");
        goruntuAlani.append("=================\n");
        for (Map.Entry<String, Boolean> entry : koltuklar.entrySet()) {
            goruntuAlani.append(entry.getKey() + ": " + (entry.getValue() ? "Rezerve" : "Boş") + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HavayoluRezervasyonSistemiGUI().setVisible(true);
            }
        });
    }
}
