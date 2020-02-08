package main;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Random;
import javax.swing.*;

import jxl.*;
import jxl.read.biff.BiffException;
import main.VoiceCompose;

import java.io.*;

/**
 * @author penseeyou
 * 本项目开源，但不允许商业使用，反卖代码
 * https://github.com/PenSeeYou/CallName-Plus
 */
public class Callsomeone {
    public static int indexc = 0;
    static boolean close = true;
    static boolean comeb = false;
    static boolean onlyoneb = false;
    //private static int onlyone;
    static int[] onlyone = null;

    public static void main(String[] args) throws IOException, BiffException {
        String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame Call = new JFrame("点名器");
        //Call.setUndecorated(false);
        //Call.setOpacity(0.5f);
        Call.setSize(170, 120);
        Call.setLocationRelativeTo(null);
        Call.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Call.setResizable(false);
        JPanel panel = new JPanel();

        call(panel, Call);

        Call.add(panel);

        Call.setAlwaysOnTop(
                !Call.isAlwaysOnTop());
        Call.setVisible(
                true);
        Call.setIconImage(
                new ImageIcon("icon/icon.png").getImage());

    }


    public static void call(JPanel panel, final JFrame Call) throws IOException, BiffException {
        //Button
        final JButton goButton = new JButton("启动随机点名");
        goButton.setBounds(2, 40, 160, 30);
        panel.add(goButton);
        threadtalk(goButton);
        //names
        Workbook Abook = Workbook.getWorkbook(new File("Names/AllNames.xls"));

        Sheet Asheet = Abook.getSheet(0);
        int Arows = Asheet.getRows();
        int Acols = Asheet.getColumns();
        int Ai;
        int Aj;
        final String[] allnames = new String[Arows];
        //循环读取数据
        for (Ai = 0; Ai < Acols; Ai++) {
            for (Aj = 0; Aj < Arows; Aj++) {
                //System.out.println("第" + j + "行，第" + i + "列为：" + sheet.getCell(i, j).getContents());
                allnames[Aj] = Asheet.getCell(Ai, Aj).getContents();
            }

        }

        Workbook Obook = Workbook.getWorkbook(new File("Names/OnlyNames.xls"));

        Sheet Osheet = Obook.getSheet(0);
        int Orows = Osheet.getRows();
        int Ocols = Osheet.getColumns();
        int Oi;
        int Oj;
        final String[] onlynames = new String[Orows];
        //循环读取数据
        for (Oi = 0; Oi < Ocols; Oi++) {
            for (Oj = 0; Oj < Orows; Oj++) {
                //System.out.println("第" + j + "行，第" + i + "列为：" + sheet.getCell(i, j).getContents());
                onlynames[Oj] = Osheet.getCell(Oi, Oj).getContents();
            }

        }

        panel.setLayout(null);

        //Button
        final JButton come = new JButton("S");
        come.setBounds(0, 0, 40, 20);
        panel.add(come);

        final JButton noname = new JButton("Q");
        noname.setBounds(0, 20, 40, 20);
        panel.add(noname);



        come.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (comeb == false) {
                    Call.setSize(170, 103);
                    come.setText("L");
                    comeb = true;
                } else {
                    Call.setSize(170, 120);
                    come.setText("S");
                    comeb = false;
                }
            }
        });

        JCheckBox jCheckBoxonly = new JCheckBox("每人一次");
        jCheckBoxonly.setBounds(5, 70, 78, 20);
        panel.add(jCheckBoxonly);


        onlyone = new int[allnames.length];
        jCheckBoxonly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (onlyoneb == true) {
                    onlyone = null;
                    onlyoneb = false;
                } else {
                    onlyone = new int[allnames.length];
                    onlyoneb = true;
                }
            }
        });

        JCheckBox stopclose = new JCheckBox("语音播报", true);
        stopclose.setBounds(80, 70, 85, 20);
        panel.add(stopclose);

        stopclose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (close == true) {
                    close = false;
                } else {
                    close = true;
                }
            }
        });

        //Name
        final JLabel name = new JLabel();
        Random num = new Random();
        final int index = num.nextInt(allnames.length);
        name.setText(allnames[index]);
        name.setBounds(40, 0, 400, 45);
        name.setFont(new Font("宋体", Font.PLAIN, 40));
        panel.add(name);

        noname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String path = "./缺课记录.txt";
                File file = new File(path);
                if(!file.exists()){
                    try {
                        file.createNewFile();
                        String nonames = name.getText().toString();
                        FileWriter writer = new FileWriter(path, true);
                        writer.write("缺课记录：");
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
                    int month = c.get(Calendar.MONTH);
                    int date = c.get(Calendar.DATE);
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);
                    String nonames = name.getText().toString();
                    FileWriter writer = new FileWriter(path, true);
                    writer.write("\r\n" + nonames +"在" + month +"月" + date +"日" + hour + "时" + minute +"分缺课一次，提问无回应");
                    writer.close();
                    JOptionPane.showMessageDialog(Call, "成功将" + nonames + "写入缺课记录\n如果需要查看历史缺课记录，请到您开启本程序的地方寻找缺课记录.txt\nBy SanEnChen（PenSeeYou）", "成功写入缺课名单", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        //Listener Button
        goButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Random num = new Random();
                indexc = num.nextInt(allnames.length);

                if (indexc == indexc) {
                    indexc = num.nextInt(allnames.length);
                }
                //VoiceCompose voiceCompose = new VoiceCompose();


                if (onlyoneb == true) {
                    while (true) {
                        if (onlyone[indexc - 1] != 1) {
                            onlyone[indexc - 1] = 1;
                            name.setText(onlynames[indexc]);
                            if (close == true)
                                threadTest(onlynames, goButton);
                            break;
                        } else {
                            indexc = num.nextInt(allnames.length);
                        }
                    }
                    //break;
                } else
                    name.setText(allnames[indexc]);
                if (close == true && onlyoneb == false)
                    threadTest(allnames, goButton);
            }
        });

    }

    public static void wipe() {

    }

    public static void threadTest(final String[] allnames, final JButton goButton) {
        new Thread() {
            @Override
            public void run() {
                try {
                    goButton.setEnabled(false);
                    goButton.setText("等待播报完毕");
                    VoiceCompose voiceCompose = new VoiceCompose();
                    voiceCompose.mainc("请" + allnames[indexc] + "同学回答问题！");
                    //Thread.sleep(3000);
                    goButton.setText("启动随机点名");
                    goButton.setEnabled(true);

                } catch (Exception i) {

                }

            }
        }.start();
        ;
    }

    public static void threadtalk(final JButton goButton) {
        new Thread() {
            @Override
            public void run() {
                try {
                    goButton.setEnabled(false);
                    goButton.setText("等待播报完毕");
                    VoiceCompose voiceCompose = new VoiceCompose();
                    voiceCompose.mainc("连接服务器成功！ By：PenSeeYou 本项目开源，但不允许商业使用，反卖代码");//未经允许，不许修改此代码，如需修改，请联系2115707702免费授权，其实偷偷修改也可以，别发布
                    //Thread.sleep(3000);
                    goButton.setText("启动随机点名");
                    goButton.setEnabled(true);

                } catch (Exception i) {

                }
            }
        }.start();
    }
}
