package com.company;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;

public class BazaDanychUzytkownika extends JFrame implements ActionListener {

    JMenuBar menu;
    JButton wyloguj, dodaj, wczytaj, usun, pomoc;
    JLabel napisLogin, napisAdres, napisWczytaneDane, napisHaslo, napisUsunDane, autor;
    JPasswordField poleHaslo;
    JTextArea poleLogin, poleSprawdzDane, poleUsunDane;
    JTextField poleAdres;
    private Menu menuMenu;
    public String hasloPliku;
    public String nazwaPliku;
    private String nazwaPlikuRoboczego = "temp.txt";
    String key = "Java@123##";

    public void setHasloPliku(String hasloPliku) {
        this.hasloPliku = hasloPliku;
    }

    public void setNazwaPliku(String nazwaPliku) {
        this.nazwaPliku = nazwaPliku;
    }

    public BazaDanychUzytkownika() {

        menu = new JMenuBar();
        setJMenuBar(menu);
        setTitle("Baza danych");
        setSize(560, 420);
        setLayout(null);
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        autor = new JLabel("Autor: Grzegorz Dawidek ");
        autor.setBounds(20, 360, 160, 20);
        add(autor);

        napisHaslo = new JLabel("Haslo: ");
        napisHaslo.setBounds(20, 260, 120, 20);
        add(napisHaslo);

        poleHaslo = new JPasswordField();
        poleHaslo.setBounds(20, 280, 150, 20);
        poleHaslo.setBorder(new LineBorder(Color.BLACK));
        poleHaslo.setBackground(Color.GRAY);
        poleHaslo.setBorder(BorderFactory.createLineBorder(Color.black));
        add(poleHaslo);

        napisLogin = new JLabel("Login: ");
        napisLogin.setBounds(20, 220, 120, 20);
        add(napisLogin);

        poleLogin = new JTextArea();
        poleLogin.setBounds(20, 240, 150, 20);
        poleLogin.setBorder(new LineBorder(Color.BLACK));
        poleLogin.setBackground(Color.GRAY);
        poleLogin.setBorder(BorderFactory.createLineBorder(Color.black));
        add(poleLogin);

        napisAdres = new JLabel("Adres strony internetowej: ");
        napisAdres.setBounds(20, 180, 200, 20);
        add(napisAdres);

        poleAdres = new JTextField();
        poleAdres.setBounds(20, 200, 150, 20);
        poleAdres.setBorder(new LineBorder(Color.BLACK));
        poleAdres.setBackground(Color.GRAY);
        poleAdres.setBorder(BorderFactory.createLineBorder(Color.black));
        add(poleAdres);

        napisWczytaneDane = new JLabel("Wczytane dane:");
        napisWczytaneDane.setBounds(20, 10, 400, 20);
        add(napisWczytaneDane);

        poleSprawdzDane = new JTextArea();
        poleSprawdzDane.setLineWrap(true);
        poleSprawdzDane.setWrapStyleWord(true);
        poleSprawdzDane.setBorder(new LineBorder(Color.BLACK));
        JScrollPane jScrollPane = new JScrollPane(poleSprawdzDane);
        jScrollPane.setBounds(20, 30, 500, 120);
        add(jScrollPane);
        poleSprawdzDane.setEditable(false);

        dodaj = new JButton("Dodaj");
        dodaj.setBounds(50, 320, 85, 20);
        dodaj.setBorder(BorderFactory.createLineBorder(Color.black));
        add(dodaj);
        dodaj.addActionListener(this);

        wyloguj = new JButton("Wyloguj");
        wyloguj.setBounds(430, 320, 85, 20);
        wyloguj.setBorder(BorderFactory.createLineBorder(Color.black));
        add(wyloguj);
        wyloguj.addActionListener(this);

        wczytaj = new JButton("Wczytaj");
        wczytaj.setBounds(430, 160, 85, 20);
        wczytaj.setBorder(BorderFactory.createLineBorder(Color.black));
        add(wczytaj);
        wczytaj.addActionListener(this);

        napisUsunDane = new JLabel("Dane do usuniecia: ");
        napisUsunDane.setBounds(220, 180, 200, 20);
        add(napisUsunDane);

        poleUsunDane = new JTextArea();
        poleUsunDane.setBounds(220, 200, 150, 20);
        poleUsunDane.setBorder(new LineBorder(Color.BLACK));
        poleUsunDane.setBackground(Color.GRAY);
        poleUsunDane.setBorder(BorderFactory.createLineBorder(Color.black));
        add(poleUsunDane);

        usun = new JButton("Usuń");
        usun.setBounds(250, 240, 85, 20);
        usun.setBorder(BorderFactory.createLineBorder(Color.black));
        usun.addActionListener(this);
        add(usun);

        pomoc = new JButton("Pomoc");
        pomoc.setBounds(300, 320, 90, 20);
        pomoc.setBorder(BorderFactory.createLineBorder(Color.black));
        add(pomoc);
        pomoc.addActionListener(this);
    }

    public void zapisywanieDanych() {
        String adres1 = poleAdres.getText();
        String login1 = poleLogin.getText();
        String haslo1 = poleHaslo.getText();

        try {
            File file = new File(nazwaPlikuRoboczego);
            if (!file.exists())
                file.createNewFile();
            if (adres1.length() < 1 || login1.length() < 1 || haslo1.length() < 1) {
                JOptionPane.showMessageDialog(this, "Wszystkie pola nie zostały uzupełnione");
            } else {
                FileWriter pisz = new FileWriter(file, true);
                pisz.write(adres1 + " " + login1 + " " + haslo1 + "\n");
                JOptionPane.showMessageDialog(this, "Dane dodane do pliku!");
                pisz.close();

                poleAdres.setText("");
                poleLogin.setText("");
                poleHaslo.setText("");
            }
        } catch (IOException ioe) {
        }
    }


    public void czytaniePliku() {
        try {
            FileReader fileReader = new FileReader(nazwaPlikuRoboczego);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linia;
            String temp = "";
            String wyrazy;
            String adres;
            String login;
            String haslo;
            int licznikLinii = 0;
            while ((linia = bufferedReader.readLine()) != null) {
                if (licznikLinii == 0) {
                    temp = "Baza Danych:\n";
                    poleSprawdzDane.setText(temp);
                    licznikLinii++;
                } else {
                    wyrazy = linia;
                    String[] actualValue = wyrazy.split(" ");
                    adres = "Adres: " + actualValue[0];
                    login = "Login: " + actualValue[1];
                    haslo = "Hasło: " + actualValue[2];

                    temp += adres + "     " + login + "    " + haslo;
                    temp += "\n";
                    poleSprawdzDane.setText(temp);
                }
            }
            fileReader.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void usuwanieRekordu() {
        LinkedList<String> dane = new LinkedList<>();
        String usun1 = poleUsunDane.getText();
        String linia;
        String temp;
        FileReader fileReader;
        try {
            fileReader = new FileReader(nazwaPlikuRoboczego);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            if (usun1.length() < 1) {
                JOptionPane.showMessageDialog(this, "Uzupełnij pole!");
            } else {
                while ((linia = bufferedReader.readLine()) != null) {
                    dane.add(linia);
                }
                fileReader.close();
                bufferedReader.close();
                PrintWriter writer = new PrintWriter(nazwaPlikuRoboczego);
                writer.print("");

                FileWriter fileWriter = new FileWriter(nazwaPlikuRoboczego, true);
                fileWriter.write(hasloPliku + "\r\n");
                for (int i = 1; i < dane.size(); i++) {
                    temp = dane.get(i);
                    if (temp.equals(usun1)) {
                        JOptionPane.showMessageDialog(this, "Usunięto rekord");
                        continue;
                    } else {
                        fileWriter.write(temp + "\r\n");
                    }
                }
                fileWriter.close();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {

        Object zrodlo = e.getSource();


        if (zrodlo == wyloguj) {
            dispose();
            menuMenu = new Menu();
            menuMenu.setVisible(true);
            try {
                FileInputStream fis = new FileInputStream(nazwaPlikuRoboczego);
                FileOutputStream fos = new FileOutputStream(nazwaPliku);
                menuMenu.encrypt(key, fis, fos);
                menuMenu.usunPlik(nazwaPlikuRoboczego);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        if (zrodlo == dodaj) {
            zapisywanieDanych();
        }

        if (zrodlo == usun) {
            usuwanieRekordu();
            poleUsunDane.setText("");
        }

        if (zrodlo == wczytaj) {
            czytaniePliku();
        }
        if (zrodlo == pomoc) {
            JOptionPane.showMessageDialog(this, "Gdy klikniemy przycisk wczytaj wszystkie nasze dane pojawią się w polu wczytane dane." +
                    "\nPola Adres,Login,Hasło oraz przycisk dodaj odpowiadają za dodawanie nowych rekordów w naszym pliku." +
                    "\nW polu dane do usunięcia należy wpisać pełne nazwy adresu,loginu i hasła odzielając je spacjami a nastepnie kliknąć usun." +
                    "\nKażdorazowo po usunięciu lub dodaniu danych należy użyć przycisku wczytaj w celu sprawdzenia nowych aktualizacji." +
                    "\nPrzycisk wyloguj odpowiada za wyjście z pliku i zaszyfrowanie danych ");
        }
    }
}




