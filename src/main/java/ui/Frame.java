package ui;

import com.arvind.game.quiz.domain.Option;
import com.arvind.game.quiz.domain.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frame extends JFrame {

    Board panel = new Board();


    Map<Integer, JLabel> map = new HashMap<>();
    Map<Integer, JLabel> pointMap = new HashMap<>();
    JLabel questionLable = null;
    JLabel optionLabel = null;

    public Frame() {
        panel.setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(2500, 1500);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Skiing Game");
        setLayout(new GridBagLayout());
        add(panel);
        questionLable = new JLabel("Connect to wifi. In browser http://172.28.204.41:8001", JLabel.CENTER);
        questionLable.setVerticalTextPosition(JLabel.TOP);
        questionLable.setHorizontalTextPosition(JLabel.CENTER);
        Dimension size = questionLable.getPreferredSize();
        questionLable.setBounds(500, 0, size.width+800, 50);
        questionLable.setFont(new Font("arvind",Font.BOLD, 18));
        panel.add(questionLable);

        optionLabel = new JLabel("", JLabel.CENTER);
        optionLabel.setVerticalTextPosition(JLabel.TOP);
        optionLabel.setHorizontalTextPosition(JLabel.CENTER);
        Dimension optionSize = optionLabel.getPreferredSize();
        optionLabel.setBounds(1000, 50, optionSize.width+600, 100);
        optionLabel.setFont(new Font("arvind",Font.BOLD, 15));
        panel.add(optionLabel);

        initListeners();

    }

    public void addPlayer(int playerNo, String name) {

        ImageIcon ii = new ImageIcon("/home/arvind/code/quiz-game/images/"+2+".png");
        JLabel PlayerLabel = getLabel(name, ii, playerNo, 5);
        Dimension size = PlayerLabel.getPreferredSize();
        JLabel nameLabel = getLabel(name+":", null, playerNo, 6);
        JLabel pointLable = new JLabel("0");
        pointLable.setFont(new Font("arvind",Font.BOLD, 40));
        if(playerNo == 1){
            PlayerLabel.setBounds(0, 230, size.width, size.height);
            nameLabel.setBounds(50, 750, nameLabel.getPreferredSize().width, nameLabel.getPreferredSize().height);
            pointLable.setBounds(nameLabel.getPreferredSize().width+70, 750, pointLable.getPreferredSize().width, nameLabel.getPreferredSize().height);
        }else if(playerNo == 2){
            PlayerLabel.setBounds(210, 0, size.width, size.height );
            nameLabel.setBounds(50, 850, nameLabel.getPreferredSize().width, nameLabel.getPreferredSize().height);
            pointLable.setBounds(nameLabel.getPreferredSize().width+70, 850, pointLable.getPreferredSize().width, nameLabel.getPreferredSize().height);
        }else if(playerNo == 3) {
            PlayerLabel.setBounds(130, 100, size.width, size.height);
            nameLabel.setBounds(450, 750, nameLabel.getPreferredSize().width, nameLabel.getPreferredSize().height);
            pointLable.setBounds(nameLabel.getPreferredSize().width+470, 750, pointLable.getPreferredSize().width, nameLabel.getPreferredSize().height);
        }else if(playerNo == 4){
            PlayerLabel.setBounds(62, 172, size.width, size.height);
            nameLabel.setBounds(450, 850, nameLabel.getPreferredSize().width, nameLabel.getPreferredSize().height);
            pointLable.setBounds(nameLabel.getPreferredSize().width+470, 850, pointLable.getPreferredSize().width, nameLabel.getPreferredSize().height);
        }else if(playerNo == 5){
            PlayerLabel.setBounds(190, 40, size.width, size.height);
            nameLabel.setBounds(650, 750, nameLabel.getPreferredSize().width, nameLabel.getPreferredSize().height);
            pointLable.setBounds(nameLabel.getPreferredSize().width+670, 750, pointLable.getPreferredSize().width, nameLabel.getPreferredSize().height);

        }
        map.put(playerNo, PlayerLabel);
        pointMap.put(playerNo, pointLable);
        panel.add(PlayerLabel);
        panel.add(nameLabel);
        panel.add(pointLable);
        panel.revalidate();
        panel.repaint();

    }
    private JLabel getLabel(String name, ImageIcon ii, int playerNo, int size) {
        JLabel label;
        if (playerNo == 1){
            label = new JLabel("<html><font color='green' size='"+size+"'>" + name + "</font></html>", ii, JLabel.CENTER);
        } else if (playerNo == 2){
            label = new JLabel("<html><font color='blue' size='"+size+"'>" + name + "</font></html>", ii, JLabel.CENTER);
        }else if (playerNo == 3){
            label = new JLabel("<html><font color='red' size='"+size+"'>" + name + "</font></html>", ii, JLabel.CENTER);
        }else if(playerNo == 4){
            label = new JLabel("<html><font color='cyan' size='"+size+"'>" + name + "</font></html>", ii, JLabel.CENTER);
        }else{
            label = new JLabel("<html><font color='darkorange' size='"+size+"'>" + name + "</font></html>", ii, JLabel.CENTER);

        }
        label.setVerticalTextPosition(JLabel.TOP);
        label.setHorizontalTextPosition(JLabel.CENTER);
        return label;

    }

    public void pushQuestion(Question question, int qNo){

        List<Option> list = new ArrayList<>(question.getOptions());
        String htmlOptions =  "<html>"+list.get(0).getValue()+"<br>" +
                list.get(1).getValue()+"<br>" +
                list.get(2).getValue()+"<br>" +
                list.get(3).getValue()+"</html>";

        optionLabel.setText(htmlOptions);
        questionLable.setText("<html>Q"+qNo+": "+question.getTitle()+"</html>");
        questionLable.repaint();
        optionLabel.repaint();
    }

    public void pushAnswer(String answer){

        optionLabel.setText("Answer is: "+answer);
        optionLabel.revalidate();
        optionLabel.repaint();
    }


    public void move(int player){
        JLabel pointLabel = pointMap.get(player);
        int i = Integer.valueOf(pointLabel.getText());
        pointLabel.setText(i+1+"");
        pointLabel.revalidate();
        pointLabel.repaint();

        JLabel label = map.get(player);

        int intialX = label.getBounds().x;
        int intialY = label.getBounds().y;
        int x = 0;
        int y = 0;
        while(x != 124) {
            x = x+4;
            y = y+1;
            label.setBounds(intialX + x, intialY + y, label.getPreferredSize().width, label.getPreferredSize().height);
            //label.revalidate();
            label.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void makeWinner(List<String> userIds){

        optionLabel.setText("");
        questionLable.setText("Game Over");
        questionLable.repaint();
        optionLabel.repaint();

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
