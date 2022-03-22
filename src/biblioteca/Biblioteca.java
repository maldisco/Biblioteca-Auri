package biblioteca;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Biblioteca {
    
    public static void main(String[] args) {
        AuriFrame auri = new AuriFrame();   // criação da frame
    }
    
}

class AuriFrame extends JFrame {
        
    AuriFrame(){
        this.setTitle("Auri");  // nome da aplicação
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // fechar ao clicar no X
        this.setResizable(false);   // tornar não redimensionável
        this.setSize(800,800);  // tamanho da frame
        this.setVisible(true);  // fazer a frame visível
            
        ImageIcon lua = new ImageIcon("resources/auri-logo.png");  // criar um imageicon
        this.setIconImage(lua.getImage());       // muda4r icone da frame
        this.getContentPane().setBackground(new Color(0x123456));   // cor do background  
    }
}