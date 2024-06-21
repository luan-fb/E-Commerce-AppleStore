package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import entidades.Carrinho;
import entidades.ItemCarrinho;
import entidades.Produto;
import entidades.Usuario;
import servico.CarrinhoService;

public class TelaCarrinho extends JFrame {
    private Carrinho carrinho;
    private CarrinhoService carrinhoService;

    public TelaCarrinho(Carrinho carrinho, Usuario usuario) {
        this.carrinho = carrinho;
        this.carrinhoService = new CarrinhoService();

        setUndecorated(true); // Remove a decoração padrão da janela
        setTitle("Carrinho de Compras");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        getContentPane().add(mainPanel);

        // Barra de título personalizada
        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.BLACK);
        titleBar.setPreferredSize(new Dimension(600, 30));
        titleBar.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Carrinho de Compras");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        titleBar.add(titleLabel, BorderLayout.CENTER);

        JButton btnClose = new JButton("X");
        styleControlButton(btnClose);
        btnClose.addActionListener(e -> dispose());
        titleBar.add(btnClose, BorderLayout.EAST);

        mainPanel.add(titleBar, BorderLayout.NORTH);

        // Painel de produtos no carrinho
        JPanel cartPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        cartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adicionando itens ao carrinho
        List<ItemCarrinho> itensCarrinho = carrinho.getItens();
        for (ItemCarrinho itemCarrinho : itensCarrinho) {
            Produto produto = itemCarrinho.getProduto();
            int quantidade = itemCarrinho.getQuantidade();
            BigDecimal valorItem = BigDecimal.valueOf(produto.getPreco() * quantidade);

            JPanel produtoPanel = new JPanel(new BorderLayout());
            produtoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel lblNome = new JLabel(produto.getModelo(), SwingConstants.CENTER);
            lblNome.setFont(new Font("Arial", Font.BOLD, 16));
            produtoPanel.add(lblNome, BorderLayout.CENTER);

            JLabel lblQuantidade = new JLabel("Quantidade: " + quantidade, SwingConstants.CENTER);
            lblQuantidade.setFont(new Font("Arial", Font.PLAIN, 14));
            produtoPanel.add(lblQuantidade, BorderLayout.WEST);

            JLabel lblPreco = new JLabel("R$ " + produto.getPreco() + " x " + quantidade + " = R$ " + valorItem, SwingConstants.CENTER);
            lblPreco.setFont(new Font("Arial", Font.PLAIN, 14));
            produtoPanel.add(lblPreco, BorderLayout.EAST);

            cartPanel.add(produtoPanel);
        }

        mainPanel.add(new JScrollPane(cartPanel), BorderLayout.CENTER);

        // Painel inferior com o total e botão de pagamento
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Total do carrinho
        JLabel totalLabel = new JLabel("Total: R$ " + carrinho.getTotal(), SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        bottomPanel.add(totalLabel, BorderLayout.NORTH);

        // Botão para prosseguir para tela de pagamento
        JButton btnPagamento = new JButton("Prosseguir para Pagamento");
        btnPagamento.addActionListener(e -> {
            // Abrir a tela de pagamento passando o carrinho e o usuário
            new TelaPagamento(carrinho, usuario).setVisible(true);
            dispose(); // Fechar a tela de carrinho ao prosseguir para pagamento
        });
        btnPagamento.setFont(new Font("Arial", Font.BOLD, 14));
        btnPagamento.setPreferredSize(new Dimension(0, 40));
        bottomPanel.add(btnPagamento, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
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