package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import entidades.Carrinho;
import entidades.FormaPagamento;
import entidades.Pedido;
import entidades.Usuario;
import servico.FormaPagamentoService;
import servico.PedidoService;

public class TelaPagamento extends JFrame {
    private Carrinho carrinho;
    private Usuario usuario;
    private FormaPagamentoService formaPagamentoService;
    private JPanel paymentPanel;
    private ButtonGroup group;
    private FormaPagamento selectedPaymentMethod;
    private JTextField nomeCartaoField;
    private JTextField numeroCartaoField;
    private JTextField cvvField;
    private JComboBox<Integer> parcelasComboBox;
    private JLabel qrCodeLabel;

    public TelaPagamento(Carrinho carrinho, Usuario usuario) {
        this.carrinho = carrinho;
        this.usuario = usuario;
        this.formaPagamentoService = new FormaPagamentoService();

        setUndecorated(true);
        setTitle("Pagamento");
        setSize(600, 400);
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

        JLabel titleLabel = new JLabel("Pagamento");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        titleBar.add(titleLabel, BorderLayout.CENTER);

        JButton btnClose = new JButton("X");
        styleControlButton(btnClose);
        btnClose.addActionListener(e -> dispose());
        titleBar.add(btnClose, BorderLayout.EAST);

        mainPanel.add(titleBar, BorderLayout.NORTH);

        // Painel de formas de pagamento
        paymentPanel = new JPanel(new GridBagLayout());
        paymentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        List<FormaPagamento> formasPagamento = formaPagamentoService.listarFormasPagamento();
        group = new ButtonGroup();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        for (FormaPagamento formaPagamento : formasPagamento) {
            JRadioButton radioButton = new JRadioButton(formaPagamento.getNome() + " - " + formaPagamento.getDescricao());
            radioButton.setActionCommand(String.valueOf(formaPagamento.getId()));
            group.add(radioButton);
            paymentPanel.add(radioButton, gbc);
            gbc.gridy++;

            radioButton.addActionListener(a -> {
                selectedPaymentMethod = formaPagamento;
                if (formaPagamento.getNome().equalsIgnoreCase("PIX")) {
                    ocultarCamposCartaoCredito();
                    exibirQRCodePix();
                } else {
                    esconderQRCodePix();
                    mostrarCamposCartaoCredito();
                }
            });
        }

        mainPanel.add(paymentPanel, BorderLayout.CENTER);

        // Total do carrinho
        JLabel totalLabel = new JLabel("Total: R$ " + carrinho.getTotal(), SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        mainPanel.add(totalLabel, BorderLayout.SOUTH);

        // Botão para confirmar pagamento
        JButton btnConfirmarPagamento = new JButton("Confirmar Pagamento");
        btnConfirmarPagamento.addActionListener(e -> {
            if (selectedPaymentMethod == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma forma de pagamento.");
                return;
            }

            if (selectedPaymentMethod.getNome().equalsIgnoreCase("Pix")) {
                exibirQRCodePix();
                
            } else  {
                processarPagamentoCartao();
            }
        });
        btnConfirmarPagamento.setFont(new Font("Arial", Font.BOLD, 14));
        btnConfirmarPagamento.setPreferredSize(new Dimension(0, 40));
        mainPanel.add(btnConfirmarPagamento, BorderLayout.SOUTH);
    }

    private void styleControlButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(45, 30));
    }

    private void mostrarCamposCartaoCredito() {
        if (nomeCartaoField == null) {
            nomeCartaoField = new JTextField();
            numeroCartaoField = new JTextField();
            cvvField = new JTextField();
            parcelasComboBox = new JComboBox<>();

            for (int i = 1; i <= 12; i++) {
                parcelasComboBox.addItem(i);
            }

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;

            gbc.gridy = paymentPanel.getComponentCount();
            paymentPanel.add(new JLabel("Nome no Cartão:"), gbc);
            gbc.gridy++;
            paymentPanel.add(nomeCartaoField, gbc);
            gbc.gridy++;
            paymentPanel.add(new JLabel("Número do Cartão:"), gbc);
            gbc.gridy++;
            paymentPanel.add(numeroCartaoField, gbc);
            gbc.gridy++;
            paymentPanel.add(new JLabel("CVV:"), gbc);
            gbc.gridy++;
            paymentPanel.add(cvvField, gbc);
            gbc.gridy++;
            paymentPanel.add(new JLabel("Parcelas (1-12):"), gbc);
            gbc.gridy++;
            paymentPanel.add(parcelasComboBox, gbc);
        }

        nomeCartaoField.setVisible(true);
        numeroCartaoField.setVisible(true);
        cvvField.setVisible(true);
        parcelasComboBox.setVisible(true);
        paymentPanel.revalidate();
        paymentPanel.repaint();
    }

    private void ocultarCamposCartaoCredito() {
        if (nomeCartaoField != null) {
            nomeCartaoField.setVisible(false);
            numeroCartaoField.setVisible(false);
            cvvField.setVisible(false);
            parcelasComboBox.setVisible(false);
            paymentPanel.revalidate();
            paymentPanel.repaint();
        }
    }

    private void exibirQRCodePix() {
        if (qrCodeLabel == null) {
            qrCodeLabel = new JLabel();
            qrCodeLabel.setIcon(new ImageIcon("C:\\Users\\gui\\eclipse\\jee-2023-09\\eclipse\\ProjetoAppleStore\\src\\main\\resources\\img\\qrcode.png")); 
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = paymentPanel.getComponentCount();
            paymentPanel.add(qrCodeLabel, gbc);
        }

        qrCodeLabel.setVisible(true);
        paymentPanel.revalidate();
        paymentPanel.repaint();

        // Simular espera de 60 segundos
        new Thread(() -> {
            try {
                Thread.sleep(30000);
                confirmarPedido();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void esconderQRCodePix() {
        if (qrCodeLabel != null) {
            qrCodeLabel.setVisible(false);
            paymentPanel.revalidate();
            paymentPanel.repaint();
        }
    }

    private void processarPagamentoCartao() {
        String nomeCartao = nomeCartaoField.getText();
        String numeroCartao = numeroCartaoField.getText();
        String cvv = cvvField.getText();
        int parcelas = (int) parcelasComboBox.getSelectedItem();

        BigDecimal juros = BigDecimal.valueOf(1.99);
        BigDecimal totalComJuros = calcularTotalComJuros(parcelas, juros);
        totalComJuros = totalComJuros.setScale(2, RoundingMode.HALF_EVEN);

        JOptionPane.showMessageDialog(this, "Total com juros: R$ " + totalComJuros);
      
    }

    private BigDecimal calcularTotalComJuros(int parcelas, BigDecimal juros) {
        BigDecimal total = carrinho.getTotal();
        BigDecimal totalComJuros = total;
        
        for (int i = 0; i < parcelas; i++) {
            totalComJuros = totalComJuros.add(total.multiply(juros).divide(BigDecimal.valueOf(100)));
        }
        return totalComJuros;
    }

    private void confirmarPedido() {
        PedidoService pedidoService = new PedidoService();
        Pedido pedido = new Pedido(usuario, carrinho, new Date(), carrinho.getTotal().doubleValue(), "Confirmado", selectedPaymentMethod);

        try {
            pedidoService.adicionarPedido(pedido);
            JOptionPane.showMessageDialog(this, "Pedido confirmado ");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o pedido: " + e.getMessage());
        }

        dispose();
    }
    
}
