import javax.swing.JFrame;

public class Main{
    public static void main(String[] args){

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("My Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        // causes this window to be sized to fit the preferred size and layouts of GamePanel
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}

