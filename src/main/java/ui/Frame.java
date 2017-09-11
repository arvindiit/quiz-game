package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

public class Frame extends JFrame {

    Board panel = new Board();


    Map<Integer, JLabel> map = new HashMap<>();

    public Frame() {
        panel.setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(2500, 1500);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Frame");
        setLayout(new GridBagLayout());
        add(panel);
        initListeners();

    }

    public void addPlayer(int playerNo, String name) {

        ImageIcon ii = new ImageIcon("C:\\Users\\klm75203\\Desktop\\images\\"+playerNo+".gif");

        JLabel label = new JLabel(name, ii, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        //label.setBounds(10, 350, 100, 20);
        Dimension size = label.getPreferredSize();
        if(playerNo == 1){
            label.setBounds(0, 0, size.width, size.height+670);
            //label.setLocation(10, 350);
        }else if(playerNo == 2){
            label.setBounds(0, 0, size.width+90, size.height+620);
            //label.setLocation(20, 450);
        }
        panel.add(label);
        map.put(playerNo, label);
        panel.revalidate();
        panel.repaint();

    }

    public void move(int player){
        JLabel label = map.get(player);
        label.setBounds(0, 0, label.getWidth()+120, label.getHeight()+100);
        panel.revalidate();
        panel.repaint();
    }



    public void initListeners() {

        /** When the window is resized, the panel size is updated. */
        addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                panel.updatePanelSize();
            }

            @Override
            public void componentHidden(ComponentEvent evt) {}

            @Override
            public void componentShown(ComponentEvent evt) {}

            @Override
            public void componentMoved(ComponentEvent evt) {}
        });
    }
}
