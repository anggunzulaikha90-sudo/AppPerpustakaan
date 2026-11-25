/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package custom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 *
 * @author ANGGUN Z
 */
public class ButtonCustom extends JButton {

    private boolean over;
    private boolean pressed;
    private boolean active;

    private Color fill;
    private Icon icon;

    private Color fillOriginal;
    private Color fillOver;
    private Color fillClick;
    private Color fillActive;

    private int strokeWidth;
    private int roundedCorner;
    private int fontSize;
    private GayaFont fontGaya;
    private Color strokeColor = new Color(255, 255, 255);

    public enum GayaFont {
        PLAIN(Font.PLAIN),
        BOLD(Font.BOLD),
        ITALIC(Font.ITALIC),
        BOLD_ITALIC(Font.BOLD | Font.ITALIC);

        private final int nilai;

        GayaFont(int nilai) {
            this.nilai = nilai;
        }

        public int getNilai() {
            return nilai;
        }
    }

    public ButtonCustom() {
        fillOriginal = new Color(35, 127, 72);
        fillOver     = new Color(255, 253, 208); // Cream muda saat hover
        fillClick    = new Color(235, 225, 195); // Cream sedikit gelap saat ditekan
        fillActive   = new Color(255, 246, 214); // Krem saat menu aktif

        strokeWidth = 2;
        roundedCorner = 40;
        icon = null;

        over = false;
        pressed = false;
        active = false;
        fill = fillOriginal;

        setOpaque(false);
        setBorder(null);
        setFocusPainted(false);
        setContentAreaFilled(false);
        super.setBackground(fillOriginal);
        setForeground(Color.WHITE);

        fontSize = 14;
        fontGaya = GayaFont.PLAIN;
        aturFont();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                over = true;
                updateFill();
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                updateFill();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                updateFill();
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                over = false;
                pressed = false;
                updateFill();
                repaint();
            }
        });
    }

    private void updateFill() {
        if (pressed) {
            fill = fillClick;
        } else if (over) {
            fill = fillOver;
        } else if (active) {
            fill = fillActive;
        } else {
            fill = fillOriginal;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        updateFill();
        repaint();
    }

    public Color getFillActive() {
        return fillActive;
    }

    public void setFillActive(Color fillActive) {
        this.fillActive = fillActive;
        updateFill();
        repaint();
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public void setIcon(Icon icon) {
        this.icon = icon;
        super.setIcon(icon);
        repaint();
    }

    public Color getFillOriginal() {
        return fillOriginal;
    }

    public void setFillOriginal(Color fillOriginal) {
        this.fillOriginal = fillOriginal;
        super.setBackground(fillOriginal);
        updateFill();
        repaint();
    }

    public Color getFillOver() {
        return fillOver;
    }

    public void setFillOver(Color fillOver) {
        this.fillOver = fillOver;
        updateFill();
        repaint();
    }

    public Color getFillClick() {
        return fillClick;
    }

    public void setFillClick(Color fillClick) {
        this.fillClick = fillClick;
        updateFill();
        repaint();
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        repaint();
    }

    public int getRoundedCorner() {
        return roundedCorner;
    }

    public void setRoundedCorner(int roundedCorner) {
        this.roundedCorner = roundedCorner;
        repaint();
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        aturFont();
    }

    public GayaFont getFontGaya() {
        return fontGaya;
    }

    public void setFontGaya(GayaFont fontGaya) {
        this.fontGaya = fontGaya;
        aturFont();
    }

    private void aturFont() {
        if (getFont() != null && fontGaya != null) {
            setFont(getFont().deriveFont(fontGaya.getNilai(), (float) fontSize));
            repaint();
        }
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        float s = strokeWidth;
        RoundRectangle2D.Float shape = new RoundRectangle2D.Float(
                s / 2f, s / 2f,
                getWidth() - s, getHeight() - s,
                roundedCorner, roundedCorner);

        g2d.setColor(fill != null ? fill : fillOriginal);
        g2d.fill(shape);

        if (strokeWidth > 0 && strokeColor != null) {
            g2d.setStroke(new BasicStroke(s));
            g2d.setColor(strokeColor);
            g2d.draw(shape);
        }

        g2d.dispose();
        super.paintComponent(g);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        this.fillOriginal = bg;
        updateFill();
        repaint();
    }
}