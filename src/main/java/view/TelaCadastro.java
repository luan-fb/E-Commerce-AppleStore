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

public class TelaCadastro extends JFrame {
    private JTextField textFieldNome;
    private JTextField textFieldEmail;
    private JPasswordField passwordFieldSenha;
    private JPasswordField passwordFieldConfirmarSenha;
    private UsuarioController usuarioController;
    private Point initialClick;

    public TelaCadastro() {
        usuarioController = new UsuarioController();

        setUndecorated(true); // Remove a decoração padrão da janela
        setTitle("Tela de Cadastro");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        getContentPane().add(panel);

        // Barra de título personalizada
        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.BLACK);
        titleBar.setPreferredSize(new Dimension(400, 30));
        titleBar.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Tela de Cadastro");
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
        btnClose.addActionListener(e -> dispose());

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

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNome.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNome.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panel.add(lblNome);

        textFieldNome = new JTextField(20);
        textFieldNome.setMaximumSize(new Dimension(300, 30));
        panel.add(textFieldNome);

        JLabel lblEmail = new JLabel("Email:");
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

        JLabel lblConfirmarSenha = new JLabel("Confirmar Senha:");
        lblConfirmarSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblConfirmarSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        lblConfirmarSenha.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        panel.add(lblConfirmarSenha);

        passwordFieldConfirmarSenha = new JPasswordField(20);
        passwordFieldConfirmarSenha.setMaximumSize(new Dimension(300, 30));
        panel.add(passwordFieldConfirmarSenha);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBackground(new Color(0, 0, 0));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = textFieldNome.getText();
                String email = textFieldEmail.getText();
                String senha = new String(passwordFieldSenha.getPassword());
                String confirmarSenha = new String(passwordFieldConfirmarSenha.getPassword());

                if (!senha.equals(confirmarSenha)) {
                    JOptionPane.showMessageDialog(null, "As senhas não coincidem", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Usuario usuario = new Usuario(nome, email, senha, "cliente");
                try {
                    usuarioController.adicionarUsuario(usuario);
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } catch (DadoNuloException | DadoInvalidoException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
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
}