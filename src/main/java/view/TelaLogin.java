package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controlador.UsuarioController;
import entidades.Usuario;
import exception.DadoInvalidoException;
import exception.DadoNuloException;

public class TelaLogin extends JFrame {
    private JTextField textFieldEmail;
    private JPasswordField passwordFieldSenha;
    private UsuarioController usuarioController;
    private Point initialClick;

    public TelaLogin() {
        usuarioController = new UsuarioController();
        
        setUndecorated(true); 
        setTitle("Tela de Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img/LogoAppleStore.png");
        setIconImage(icon.getImage());
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        getContentPane().add(panel);

        
        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.BLACK);
        titleBar.setPreferredSize(new Dimension(400, 30));
        titleBar.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Tela de Login");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        titleBar.add(titleLabel, BorderLayout.WEST);

        JPanel controlButtons = new JPanel();
        controlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        controlButtons.setOpaque(false);

        JButton btnMinimize = new JButton("-");
        styleControlButton(btnMinimize);
        btnMinimize.addActionListener(e -> setState(Frame.ICONIFIED));

        JButton btnClose = new JButton("X");
        styleControlButton(btnClose);
        btnClose.addActionListener(e -> System.exit(0));

        controlButtons.add(btnMinimize);
        controlButtons.add(btnClose);
        titleBar.add(controlButtons, BorderLayout.EAST);

        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        titleBar.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                int X = thisX + xMoved;
                int Y = thisY + yMoved;

                setLocation(X, Y);
            }
        });

        getContentPane().add(titleBar, BorderLayout.NORTH);

        JLabel lblEmail = new JLabel("Email ou telefone:");
        lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        lblEmail.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panel.add(lblEmail);

        textFieldEmail = new JTextField(20);
        textFieldEmail.setMaximumSize(new Dimension(300, 30));
        panel.add(textFieldEmail);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSenha.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panel.add(lblSenha);

        passwordFieldSenha = new JPasswordField(20);
        passwordFieldSenha.setMaximumSize(new Dimension(300, 30));
        panel.add(passwordFieldSenha);

     
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBackground(new Color(0, 0, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = textFieldEmail.getText();
                String senha = new String(passwordFieldSenha.getPassword());
                try {
                    usuarioController.loginUsuario(email, senha);
                    Usuario usuario = usuarioController.getUsuarioLogado();
              if(usuarioController.isAdmin()) {
            	  TelaAdmin telaAdmin = new TelaAdmin();
            	  telaAdmin.setVisible(true);
            	  dispose();
              }
              else {
                    TelaVitrine telaVitrine = new TelaVitrine(usuario);
                    telaVitrine.setVisible(true);
                    dispose(); }
                } catch (DadoNuloException | DadoInvalidoException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(btnLogin);

        JLabel lblEsqueceuSenha = new JLabel("Esqueceu a senha?");
        lblEsqueceuSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEsqueceuSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEsqueceuSenha.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        
        lblEsqueceuSenha.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblEsqueceuSenha.setForeground(Color.BLUE);
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblEsqueceuSenha.setForeground(Color.BLACK);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
            	System.out.println("a");
            }
        });

        panel.add(lblEsqueceuSenha);

        JButton btnCadastrar = new JButton("Criar nova conta");
        btnCadastrar.setBackground(new Color(0, 0, 0));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TelaCadastro telaCadastro = new TelaCadastro();
                telaCadastro.setVisible(true);
            }
        });
        panel.add(btnCadastrar);
    }

    private void styleControlButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(45, 30));
    }

    public static void main(String[] args) {
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.setVisible(true);
    }
}