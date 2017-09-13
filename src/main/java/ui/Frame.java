package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        ImageIcon ii = new ImageIcon("/home/arvind/code/quiz-game/images/"+playerNo+".png");

        JLabel label = new JLabel(name, ii, JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setHorizontalTextPosition(JLabel.CENTER);
        //label.setBounds(10, 350, 100, 20);
        Dimension size = label.getPreferredSize();
        if(playerNo == 1){
            label.setBounds(0, 0, size.width+40, size.height+850);
        }else if(playerNo == 2){
            label.setBounds(0, 0, size.width+140, size.height+750);
        }else if(playerNo == 3) {
            label.setBounds(0, 0, size.width + 245, size.height + 550);
        }else if(playerNo == 4){
            label.setBounds(0, 0, size.width + 345, size.height + 350);
        }else if(playerNo == 5){
            label.setBounds(0, 0, size.width + 445, size.height + 150);
        }
        panel.add(label);
        map.put(playerNo, label);
        panel.revalidate();
        panel.repaint();

    }

    public void move(int player){
        JLabel label = map.get(player);
        label.setBounds(0, 0, label.getWidth()+165, label.getHeight()+65);
        panel.revalidate();
        panel.repaint();
    }

    public void makeWinner(List<String> userIds){

        int size = userIds.size();
        int imageSize = 0;
        for(int i = 0; i<userIds.size(); i++) {
            int j = i+1;
            ImageIcon ii = new ImageIcon("/home/arvind/code/quiz-game/images/winners/"+j+".gif");

            JLabel label = new JLabel(userIds.get(i).toUpperCase(), ii, JLabel.CENTER);
            label.setFont(new Font(label.getFont().getFontName(), Font.BOLD, label.getFont().getSize() + 50));
            label.setVerticalTextPosition(JLabel.TOP);
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setBounds(0, 0, (imageSize+(label.getWidth() + 1800)/size), label.getHeight() + 350);

            imageSize = imageSize + ((label.getWidth() + 1800)/size);
            panel.add(label);
            panel.revalidate();
            panel.repaint();
        }
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
