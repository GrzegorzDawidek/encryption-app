package com.company;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Menu extends JFrame implements ActionListener {

    JMenuBar menu;
    JButton zaloguj, wyjscie, stworz, pomoc;
    JLabel napisHasloLogowanie, napisNazwaLogowanie, napisHasloStworz, napisRejestracja, napisNazwaStworz, napisLogowanie, autor;
    JTextField poleNazwaLogowanie, poleNazwaStworz;
    JPasswordField poleHasloLogowanie, poleHasloStworz;
    private BazaDanychUzytkownika bazaDanychUzytkownika;
    private Haszowanie haszowanie;
    String key = "Java@123##";

    public Menu() {
        menu = new JMenuBar();
        setJMenuBar(menu);
        setTitle("Aplikacja przechowująca dane logowania");
        setSize(410, 300);
        setLayout(null);
        this.getContentPane().setBackground(Color.LIGHT_GRAY.darker());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        autor = new JLabel("Autor: Grzegorz Dawidek ");
        autor.setBounds(20, 240, 160, 20);
        add(autor);

        napisLogowanie = new JLabel("Logowanie: ");
        napisLogowanie.setBounds(60, 10, 100, 20);
        add(napisLogowanie);

        napisNazwaLogowanie = new JLabel("Nazwa: ");
        napisNazwaLogowanie.setBounds(60, 40, 100, 20);
        add(napisNazwaLogowanie);

        poleNazwaLogowanie = new JTextField();
        poleNazwaLogowanie.setBounds(110, 40, 80, 20);
        poleNazwaLogowanie.setBackground(Color.GRAY.darker());
        poleNazwaLogowanie.setBorder(BorderFactory.createLineBorder(Color.black));
        add(poleNazwaLogowanie);

        napisHasloLogowanie = new JLabel("Haslo: ");
        napisHasloLogowanie.setBounds(60, 65, 80, 20);
        add(napisHasloLogowanie);

        poleHasloLogowanie = new JPasswordField();
        poleHasloLogowanie.setBounds(110, 65, 80, 20);
        poleHasloLogowanie.setBackground(Color.GRAY.darker());
        poleHasloLogowanie.setBorder(BorderFactory.createLineBorder(Color.black));
        add(poleHasloLogowanie);

        zaloguj = new JButton("Zaloguj");
        zaloguj.setBounds(240, 65, 90, 20);
        zaloguj.setBorder(BorderFactory.createLineBorder(Color.black));
        add(zaloguj);
        zaloguj.addActionListener(this);

        napisRejestracja = new JLabel("Rejestracja:");
        napisRejestracja.setBounds(60, 110, 100, 20);
        add(napisRejestracja);

        napisNazwaStworz = new JLabel("Nazwa: ");
        napisNazwaStworz.setBounds(60, 140, 100, 20);
        add(napisNazwaStworz);

        poleNazwaStworz = new JTextField();
        poleNazwaStworz.setBounds(110, 140, 80, 20);
        poleNazwaStworz.setBackground(Color.GRAY.darker());
        poleNazwaStworz.setBorder(BorderFactory.createLineBorder(Color.black));
        add(poleNazwaStworz);

        napisHasloStworz = new JLabel("Hasło: ");
        napisHasloStworz.setBounds(60, 165, 80, 20);
        add(napisHasloStworz);

        poleHasloStworz = new JPasswordField();
        poleHasloStworz.setBounds(110, 165, 80, 20);
        poleHasloStworz.setBackground(Color.GRAY.darker());
        poleHasloStworz.setBorder(BorderFactory.createLineBorder(Color.black));
        add(poleHasloStworz);

        stworz = new JButton("Stwórz");
        stworz.setBounds(240, 165, 90, 20);
        stworz.setBorder(BorderFactory.createLineBorder(Color.black));
        add(stworz);
        stworz.addActionListener(this);

        wyjscie = new JButton("Wyjscie");
        wyjscie.setBounds(240, 220, 90, 20);
        wyjscie.setBorder(BorderFactory.createLineBorder(Color.black));
        add(wyjscie);
        wyjscie.addActionListener(this);

        pomoc = new JButton("Pomoc");
        pomoc.setBounds(240, 190, 90, 20);
        pomoc.setBorder(BorderFactory.createLineBorder(Color.black));
        add(pomoc);
        pomoc.addActionListener(this);
    }

    public boolean stworzPlik(String fileName) throws IOException {
        String abc = "";
        String abc1 = "";
        abc = haszowanie.haszowanieNazwy(fileName) + "1.txt";
        File file = new File(abc);
        abc1 = haszowanie.haszowanieNazwy(fileName) + ".txt";
        File file1 = new File(abc1);
        String temp1 = poleHasloStworz.getText();
        String temp2 = poleNazwaStworz.getText();
        if (temp1.length() > 20 || temp1.length() < 1 || temp2.length() < 1 || temp2.length() > 20) {
            JOptionPane.showMessageDialog(this, " Hasło musi zawierać od 1 do 20 znaków! \n Nazwa musi zawierać od 1 do 20 znaków!");
            return false;
        } else if (temp1.equals(temp2)) {
            JOptionPane.showMessageDialog(this, " Nazwa taka sama jak hasło!! Niebezpieczeństwo!!");
            return false;
        } else {
            if (!file1.exists()) {
                file.createNewFile();
                JOptionPane.showMessageDialog(this, "Użytkownik dodany!");
                FileWriter fileWriter = new FileWriter(file, true);
                fileWriter.write(temp1 + "\r\n");
                fileWriter.close();
                dispose();
                Menu odswiezanieMenu = new Menu();
                odswiezanieMenu.setVisible(true);
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Użytkownik istnieje!");
                return false;
            }
        }
    }

    public void usunPlik(String nazwaPlikuUsun) {
        File file = new File(nazwaPlikuUsun);
        file.delete();
    }

    public void logowanie(String fileName) {
        String plikTymczasowy = "temp.txt";
        File file = new File(fileName);
        String temp3 = poleHasloLogowanie.getText();
        String temp4 = poleNazwaLogowanie.getText();
        temp4 = haszowanie.haszowanieNazwy(temp4);

        if (!file.exists()) {
            try {
                temp4 += ".txt";
                FileInputStream fis = new FileInputStream(temp4);
                FileOutputStream fos = new FileOutputStream(plikTymczasowy);
                decrypt(key, fis, fos);
                FileReader fileReader = new FileReader(plikTymczasowy);
                BufferedReader bufferReader = new BufferedReader(fileReader);
                for (int i = 0; i < 1; i++) {
                    String linia;
                    linia = bufferReader.readLine();
                    if (temp3.equals(linia)) {
                        JOptionPane.showMessageDialog(this, "Nastąpiło poprawne otwarcie pliku");
                        dispose();
                        bazaDanychUzytkownika = new BazaDanychUzytkownika();
                        bazaDanychUzytkownika.setVisible(true);
                        bazaDanychUzytkownika.setHasloPliku(poleHasloLogowanie.getText());
                        bazaDanychUzytkownika.setNazwaPliku(haszowanie.haszowanieNazwy(poleNazwaLogowanie.getText()) + ".txt");
                        usunPlik(haszowanie.haszowanieNazwy(poleNazwaLogowanie.getText()) + ".txt");

                    } else {
                        fileReader.close();
                        usunPlik(plikTymczasowy);
                        dispose();
                        Menu menu1 = new Menu();
                        menu1.setVisible(true);
                        JOptionPane.showMessageDialog(this, "Błędne haslo");
                    }
                }
                fileReader.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "plik nie istnieje");
            }
        }
    }


    public static void encrypt(String key, InputStream is, OutputStream os) throws Exception {
        encryptOrDecrypt(key, Cipher.ENCRYPT_MODE, is, os);
    }


    public static void decrypt(String key, InputStream is, OutputStream os) throws Exception {
        encryptOrDecrypt(key, Cipher.DECRYPT_MODE, is, os);
    }


    public static void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) throws Exception {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");

        if (mode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            makeFile(cis, os);
        } else if (mode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            CipherOutputStream cos = new CipherOutputStream(os, cipher);
            makeFile(is, cos);
        }
    }

    public static void makeFile(InputStream is, OutputStream os) throws IOException {

        byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            os.write(bytes, 0, numBytes);
        }
        os.flush();
        os.close();
        is.close();
    }

    public void actionPerformed(ActionEvent e) {

        Object zrodlo = e.getSource();


        if (zrodlo == stworz) {
            String nazwa = "";
            String hasz = haszowanie.haszowanieNazwy(poleNazwaStworz.getText());
            String pole = poleNazwaStworz.getText();
            String temp = "";
            try {
                if (stworzPlik(pole)) {
                    temp += haszowanie.haszowanieNazwy(pole) + "1.txt";
                    hasz += ".txt";
                    FileInputStream fis = new FileInputStream(temp);
                    FileOutputStream fos = new FileOutputStream(hasz);
                    encrypt(key, fis, fos);
                    nazwa = temp;
                    File file = new File(nazwa);
                    file.delete();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if (zrodlo == wyjscie) {
            dispose();
        }
        if (zrodlo == zaloguj) {
            logowanie(poleNazwaLogowanie.getText());
        }
        if (zrodlo == pomoc) {
            JOptionPane.showMessageDialog(this, " Pierwsze dwa pola od góry i przycisk zaloguj są odpowiedzialne za logowanie." +
                    "\n Dwa kolejne pola i przycisk stwórz odpowiadają za tworzenie naszego zaszyfrowanego pliku z danymi." +
                    "\n Podcza rejestracji nazwa i hasło powinny zawierać od 1 do 20 znaków " +
                    "\n przy czym hasło powinno być silne i trudne do odgadnięcia! " +
                    "\n Pamiętaj o zasadach bezpiecznego hasła!");
        }
    }

}