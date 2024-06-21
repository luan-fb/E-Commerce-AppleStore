package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import entidades.Carrinho;
import entidades.Produto;
import entidades.Usuario;
import servico.CarrinhoService;
import servico.ProdutoService;

public class TelaVitrine extends JFrame {

    private Usuario usuario;
    private CarrinhoService carrinhoService;
    private List<Produto> produtos;
    private ProdutoService produtoService = new ProdutoService();

    public TelaVitrine(Usuario usuario) {
        this.usuario = usuario;
        this.carrinhoService = new CarrinhoService();
        this.produtos = produtoService.listarProdutos();

        setUndecorated(true);
        setTitle("AppleStore");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        getContentPane().add(mainPanel);

        // Barra de título personalizada
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Color.BLACK);
        titleBar.setPreferredSize(new Dimension(800, 30));

        JLabel titleLabel = new JLabel("AppleStore", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleBar.add(titleLabel, BorderLayout.CENTER);

        // Botão de fechar (X) na parte superior direita
        JButton btnClose = new JButton("X");
        styleControlButton(btnClose);
        btnClose.setPreferredSize(new Dimension(30, 30)); // Reduzir o tamanho do botão
        btnClose.addActionListener(e -> System.exit(0));
        titleBar.add(btnClose, BorderLayout.EAST);

        mainPanel.add(titleBar, BorderLayout.NORTH);

        // Painel de produtos na vitrine
        JPanel vitrinePanel = new JPanel(new GridLayout(0, 3, 10, 10));
        vitrinePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Produto produto : produtos) {
            JPanel produtoPanel = new JPanel(new BorderLayout());
            produtoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Adicionando a imagem do produto
            ImageIcon imageIcon = new ImageIcon(produto.getCaminhoImagem());
            Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JLabel lblImagem = new JLabel(new ImageIcon(image));
            produtoPanel.add(lblImagem, BorderLayout.NORTH);

            JLabel lblNome = new JLabel(produto.getModelo(), SwingConstants.CENTER);
            lblNome.setFont(new Font("Arial", Font.BOLD, 16));
            produtoPanel.add(lblNome, BorderLayout.CENTER);

            JLabel lblPreco = new JLabel("R$ " + produto.getPreco(), SwingConstants.CENTER);
            lblPreco.setFont(new Font("Arial", Font.PLAIN, 14));
            produtoPanel.add(lblPreco, BorderLayout.SOUTH);

            JButton btnAddCarrinho = new JButton("Adicionar ao Carrinho");
            btnAddCarrinho.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Adiciona o produto ao carrinho sem desconto de seminovo
                    carrinhoService.adicionarProdutoAoCarrinho(produto, usuario);
                    JOptionPane.showMessageDialog(TelaVitrine.this,
                            produto.getModelo() + " foi adicionado ao carrinho!",
                            "Produto Adicionado",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
            produtoPanel.add(btnAddCarrinho, BorderLayout.SOUTH);

            vitrinePanel.add(produtoPanel);
        }

        mainPanel.add(new JScrollPane(vitrinePanel), BorderLayout.CENTER);

        // Ícone para ir para a tela do carrinho na parte inferior
        JButton btnCart = new JButton("Carrinho");
        styleControlButton(btnCart);
        btnCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    abrirTelaCarrinho();
                } catch (NullPointerException i) {
                    JOptionPane.showMessageDialog(null, "Nenhum produto adicionado ao carrinho.");
                }
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(btnCart, BorderLayout.CENTER); // Centraliza o botão do carrinho
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void styleControlButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(120, 30));
    }

    private void abrirTelaCarrinho() {
        Carrinho carrinho = usuario.getCarrinho();
        TelaCarrinho telaCarrinho = new TelaCarrinho(carrinho, usuario);
        telaCarrinho.setVisible(true);
    }

    private List<Produto> carregarProdutos() {
        return produtoService.listarProdutos();
    }

    public CarrinhoService getCarrinhoService() {
        return carrinhoService;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
