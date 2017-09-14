package ui;

import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.*;

public class Board extends JPanel {

    private final int B_WIDTH = 2400;
    private final int B_HEIGHT = 1400;
    private final int INITIAL_X = 0;
    private final int INITIAL_Y = 0;

    Image track;
    private int x, y;

    public Board() {

        initBoard();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("/home/arvind/code/quiz-game/images/track1.png");
        track = ii.getImage();
    }

    private void initBoard() {

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);


        loadImage();

        x = INITIAL_X;
        y = INITIAL_Y;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawTrack(g);
        drawStar(g);
    }

    private void drawTrack(Graphics g) {
        ImageIcon ii = new ImageIcon("C:\\Users\\klm75203\\Desktop\\star.png");
        Image star = ii.getImage();
        g.drawImage(star, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }
    private void drawStar(Graphics g) {

        g.drawImage(track, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }


    public void updatePanelSize() {

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        float monitorWidth = gd.getDisplayMode().getWidth();
        float monitorHeight = gd.getDisplayMode().getHeight();

        // Aspect ratio of the monitor in decimal form.
        float monitorRatio = monitorWidth / monitorHeight;

        JComponent parent = (JComponent) getParent();
        float width = parent.getWidth();
        float height = parent.getHeight();

        width = Math.min(width, height * monitorRatio);
        height = width / monitorRatio;

        setPreferredSize(new Dimension((int)width - (16 * 15), (int)height - (9 * 15)));
    }
}