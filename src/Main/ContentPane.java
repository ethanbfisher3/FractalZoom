package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import GameState.GameStateManager;

public class ContentPane extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

  private static ContentPane instance;

  private GameStateManager gsm;

  public static final int WIDTH = 1920;
  public static final int HEIGHT = 1080;

  // image
  private BufferedImage image;
  private Graphics2D g;

  public ContentPane(GameStateManager gsm) {
    super();

    instance = this;

    this.gsm = gsm;
    setPreferredSize(GameVariables.START_SCREEN_SIZE);
    setFocusable(true);
    setLayout(null);
    requestFocus();

    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    g = (Graphics2D) image.getGraphics();

    addKeyListener(this);
    addMouseListener(this);
    addMouseMotionListener(this);
    addMouseWheelListener(this);
  }

  public void draw() {
    g.setColor(Color.white);
    g.fillRect(0, 0, WIDTH, HEIGHT);
    gsm.draw(g);

    Graphics g2 = getGraphics();
    g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    g2.dispose();
  }

  public Point screenToImagePoint(Point p) {
    int x = Mathf.lerp(0, WIDTH, (float) p.x / getWidth());
    int y = Mathf.lerp(0, HEIGHT, (float) p.y / getHeight());
    return new Point(x, y);
  }

  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    gsm.mouseWheelMoved(e.getWheelRotation());
  }

  @Override
  public void mouseDragged(MouseEvent e) {

  }

  @Override
  public void mouseMoved(MouseEvent e) {
    gsm.mouseMoved(screenToImagePoint(e.getPoint()));
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
      gsm.mouseClick(screenToImagePoint(e.getPoint()));
  }

  @Override
  public void mousePressed(MouseEvent e) {
    gsm.mousePressed();
    Input.mouseDown();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    gsm.mouseReleased();
    Input.mouseUp();
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    Input.keyPressed(e.getKeyCode());
    gsm.keyPressed(e.getKeyCode());
  }

  @Override
  public void keyReleased(KeyEvent e) {
    Input.keyReleased(e.getKeyCode());
  }

  public static ContentPane getInstance() {
    return instance;
  }

  @Override
  public Point getMousePosition() {
    Point p = super.getMousePosition();
    SwingUtilities.convertPointFromScreen(p, this);
    return screenToImagePoint(p);
  }
}
