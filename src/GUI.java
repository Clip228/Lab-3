import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;

public class GUI {
    private static final int  N = 10;
    private Double[] x;
    private Double[] y;
    private JFrame form;
    private JFileChooser fc;
    private JMenuBar mainMenu;
    private JMenu fileMenu;
    private JMenu workMenu;
    private JMenu exitMenu;
    private JMenuItem item1;
    private JMenuItem item2;
    private JMenuItem item3;
    private JMenuItem item4;
    private JMenuItem item5;
    private Label lb;
    private DefaultListModel lm;
    private JList list;
    private JComboBox cb;
    private TextField tf1;
    private TextField tf2;
    private Button bt;
    private int index;
    private int index2;
    public GUI(){
        form = new JFrame("Лабораторная работа №3");
        form.setBounds(600,200,400,400);
        form.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        form.setLayout(null);
        form.setVisible(true);
        fc = new JFileChooser("C:\\Users\\artem\\OneDrive\\Рабочий стол");
        form.getContentPane().add(fc);
        mainMenu = new JMenuBar();
        fileMenu = new JMenu("Файл");
        workMenu = new JMenu("Работа");
        exitMenu = new JMenu("Выход");
        mainMenu.add(fileMenu);
        mainMenu.add(workMenu);
        mainMenu.add(exitMenu);
        item1 = new JMenuItem("Новый");
        item2 = new JMenuItem("Загрузить");
        item3 = new JMenuItem("Сохранить");
        item4 = new JMenuItem("Вычислить");
        item5 = new JMenuItem("Выйти");
        tf1 = new TextField();
        tf2 = new TextField();
        tf1.setBounds(250,20,100,20);
        tf2.setBounds(250,60,100,20);
        form.add(tf1);
        form.add(tf2);
        bt = new Button("Изменить");
        bt.setBounds(250,100,100,20);
        form.add(bt);
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeList(index, index2);
            }
        });
        fileMenu.add(item1);
        lb = new Label();
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newArrX();
            }
        });
        fileMenu.add(item2);
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc.showOpenDialog(form);
                File fileName = fc.getSelectedFile();
                loudArrayX(fileName.getPath());
            }
        });
        fileMenu.add(item3);
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc.showOpenDialog(form);
                File fileName = fc.getSelectedFile();
                saveArrY(fileName.getPath());
            }
        });
        workMenu.add(item4);
        form.setJMenuBar(mainMenu);
        inputArray();
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calculate();
            }
        });
        lm = new DefaultListModel();
        list = new JList(lm);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(20,20,140,90);
        list.setVisibleRowCount(5);
        list.setSelectionMode(1);
        list.setSelectedIndex(-1);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                index = list.getSelectedIndex();
                tf1.setText(""+list.getSelectedValue());
            }
        });
        form.add(scrollPane);
        cb = new JComboBox();
        cb.setBounds(20,150, 140,90);
        form.add(cb);
        cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                index2 = cb.getSelectedIndex();
                tf2.setText("" + cb.getSelectedItem());
            }
        });
    }
    public void inputArray(){
        x = new Double[N];
        y = new Double[N];
        for (int i = 0;i < N;i++){
            x[i] = 1.0;
            y[i] = 1.0;
        }
    }
    public void newArrX(){
        lm.removeAllElements();
        x[0] = 1.0;
        x[1] /= 1.0 /3;
        x[2] -= 1.0/3 +1.0/5;
        for (int i = 3; i < N; i++){
            x[i] -= 1.0/3 + (-1.0/2*i) + 1;
        }
        for (int i = 0; i < x.length; i++){
            lm.addElement(x[i]);
        }

    }
    public void saveArrY(String fileName){
        String str;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            for (int i = 0; i < y.length; i++){
                str = Double.toString(y[i]);
                bw.write(str);
                bw.write("\n");
            }
            bw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loudArrayX(String fileName){
        lm.removeAllElements();
        String str;
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            for (int i = 0; ((str= br.readLine())!=null) || i < x.length; i++){
                x[i] = Double.parseDouble(str);
            }
            br.close();
            for (int i = 0; i < x.length; i++){
                lm.addElement(x[i]);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void Calculate(){
        cb.removeAllItems();
        Double t;
        for(int i = 0; i < x.length/2; i++) {
            t = x[i];
            y[i] = x[x.length-1-i];
            y[y.length-1-i] = t;
        }
        for (int i = 0; i < y.length; i++){
            cb.addItem(y[i]);
        }
    }
    public void changeList(int index, int index2){
        if (tf1.getText()!=null){
            lm.insertElementAt(tf1.getText(), index);
            lm.remove(index+1);
        }
        if (tf2.getText()!=null){
            cb.insertItemAt(tf2.getText(), index2);
            cb.removeItemAt(index2+1);
        }

    }
}