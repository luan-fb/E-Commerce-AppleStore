package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import entidades.Produto;
import entidades.Usuario;
import exception.DadoInvalidoException;
import exception.DadoNuloException;
import servico.ProdutoService;
import servico.UsuarioService;

public class TelaAdmin extends JFrame {
    private UsuarioService usuarioService;
    private ProdutoService produtoService;

    private JTabbedPane tabbedPane;

    public TelaAdmin() {
        usuarioService = new UsuarioService();
        produtoService = new ProdutoService();

        setUndecorated(true);
        setTitle("Administração");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        getContentPane().add(mainPanel);

        // Barra de título personalizada
        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.BLACK);
        titleBar.setPreferredSize(new Dimension(800, 30));
        titleBar.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Administração");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        titleBar.add(titleLabel, BorderLayout.CENTER);

        JButton btnClose = new JButton("X");
        styleControlButton(btnClose);
        btnClose.addActionListener(e -> dispose());
        titleBar.add(btnClose, BorderLayout.EAST);

        mainPanel.add(titleBar, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Usuários", createUsuariosPanel());
        tabbedPane.addTab("Produtos", createProdutosPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createUsuariosPanel() {
        JPanel usuariosPanel = new JPanel(new BorderLayout());
        usuariosPanel.setBackground(Color.WHITE);

        // Botões de ações
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton btnAdicionarUsuario = new JButton("Adicionar Usuário");
        styleActionButton(btnAdicionarUsuario);
        btnAdicionarUsuario.addActionListener(e -> adicionarUsuario());

        JButton btnRemoverUsuario = new JButton("Remover Usuário");
        styleActionButton(btnRemoverUsuario);
        btnRemoverUsuario.addActionListener(e -> removerUsuario());

        JButton btnAtualizarUsuario = new JButton("Atualizar Usuário");
        styleActionButton(btnAtualizarUsuario);
        btnAtualizarUsuario.addActionListener(e -> atualizarUsuario());

        buttonPanel.add(btnAdicionarUsuario);
        buttonPanel.add(btnRemoverUsuario);
        buttonPanel.add(btnAtualizarUsuario);

        usuariosPanel.add(buttonPanel, BorderLayout.NORTH);

        // Lista de usuários
        JList<Usuario> listaUsuarios = new JList<>();
        atualizarListaUsuarios(listaUsuarios);
        JScrollPane scrollPane = new JScrollPane(listaUsuarios);
        usuariosPanel.add(scrollPane, BorderLayout.CENTER);

        return usuariosPanel;
    }

    private JPanel createProdutosPanel() {
        JPanel produtosPanel = new JPanel(new BorderLayout());
        produtosPanel.setBackground(Color.WHITE);

        // Botões de ações
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        JButton btnAdicionarProduto = new JButton("Adicionar Produto");
        styleActionButton(btnAdicionarProduto);
        btnAdicionarProduto.addActionListener(e -> {
			try {
				adicionarProduto();
			} catch (DadoInvalidoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DadoNuloException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        JButton btnRemoverProduto = new JButton("Remover Produto");
        styleActionButton(btnRemoverProduto);
        btnRemoverProduto.addActionListener(e -> {
			try {
				removerProduto();
			} catch (DadoNuloException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        JButton btnAtualizarProduto = new JButton("Atualizar Produto");
        styleActionButton(btnAtualizarProduto);
        btnAtualizarProduto.addActionListener(e -> {
			try {
				atualizarProduto();
			} catch (DadoInvalidoException | DadoNuloException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        buttonPanel.add(btnAdicionarProduto);
        buttonPanel.add(btnRemoverProduto);
        buttonPanel.add(btnAtualizarProduto);

        produtosPanel.add(buttonPanel, BorderLayout.NORTH);

        // Lista de produtos
        JList<Produto> listaProdutos = new JList<>();
        atualizarListaProdutos(listaProdutos);
        JScrollPane scrollPane = new JScrollPane(listaProdutos);
        produtosPanel.add(scrollPane, BorderLayout.CENTER);

        return produtosPanel;
    }

    private void adicionarUsuario() {
        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        Object[] message = {
            "Nome:", nomeField,
            "Email:", emailField,
            "Senha:", senhaField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Adicionar Usuário", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Usuario usuario = new Usuario();
            usuario.setNome(nomeField.getText());
            usuario.setEmail(emailField.getText());
            usuario.setSenha(new String(senhaField.getPassword()));
            try {
                usuarioService.adicionarUsuario(usuario);
                JOptionPane.showMessageDialog(this, "Usuário adicionado com sucesso");
                atualizarListaUsuarios(null);
            } catch (DadoNuloException | DadoInvalidoException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerUsuario() {
        String email = JOptionPane.showInputDialog(this, "Email do usuário a ser removido:");
        try {
            Usuario usuario = usuarioService.getUsuarioByEmail(email);
            if (usuario != null) {
                usuarioService.removerUsuario(usuario.getId());
                JOptionPane.showMessageDialog(this, "Usuário removido com sucesso");
                atualizarListaUsuarios(null);
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado");
            }
        } catch (DadoNuloException | DadoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarUsuario() {
        String email = JOptionPane.showInputDialog(this, "Email do usuário a ser atualizado:");
        try {
            Usuario usuario = usuarioService.getUsuarioByEmail(email);
            if (usuario != null) {
                JTextField nomeField = new JTextField(usuario.getNome());
                JPasswordField senhaField = new JPasswordField(usuario.getSenha());

                Object[] message = {
                    "Nome:", nomeField,
                    "Senha:", senhaField
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Atualizar Usuário", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    usuario.setNome(nomeField.getText());
                    usuario.setSenha(new String(senhaField.getPassword()));
                    usuarioService.adicionarUsuario(usuario); // Assumindo que o método adiciona ou atualiza o usuário
                    JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso");
                    atualizarListaUsuarios(null);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado");
            }
        } catch (DadoNuloException | DadoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarProduto() throws DadoInvalidoException, DadoNuloException {
        JTextField modeloField = new JTextField();
        JTextField precoField = new JTextField();
        JTextField armazenamentoField = new JTextField();
        JTextField descricaoField = new JTextField();
        JTextField estoqueField = new JTextField();
        JTextField corField = new JTextField();

        Object[] message = {
            "Modelo:", modeloField,
            "Preço:", precoField,
            "Armazenamento:", armazenamentoField,
            "Descrição:", descricaoField,
            "Estoque:", estoqueField,
            "Cor:", corField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Adicionar Produto", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Produto produto = new Produto();
            produto.setModelo(modeloField.getText());
            produto.setPreco(Double.parseDouble(precoField.getText()));
            produto.setAmarzenamento(Integer.parseInt(armazenamentoField.getText()));
            produto.setDescricao(descricaoField.getText());
            produto.setEstoque(Integer.parseInt(estoqueField.getText()));
            produto.setCor(corField.getText());
            produtoService.adicionarProduto(produto);
			JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso");
			atualizarListaProdutos(null);
        }
    }

    private void removerProduto() throws DadoNuloException {
        String idStr = JOptionPane.showInputDialog(this, "ID do produto a ser removido:");
        try {
            int id = Integer.parseInt(idStr);
            Produto produto = produtoService.getProdutoById(id);
            if (produto != null) {
                produtoService.removerProduto(id);
                JOptionPane.showMessageDialog(this, "Produto removido com sucesso");
                atualizarListaProdutos(null);
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarProduto() throws DadoInvalidoException, DadoNuloException {
        String idStr = JOptionPane.showInputDialog(this, "ID do produto a ser atualizado:");
        try {
            int id = Integer.parseInt(idStr);
            Produto produtoAtualizar = produtoService.getProdutoById(id);
            if (produtoAtualizar != null) {
                JTextField modeloField = new JTextField(produtoAtualizar.getModelo());
                JTextField precoField = new JTextField(String.valueOf(produtoAtualizar.getPreco()));
                JTextField armazenamentoField = new JTextField(String.valueOf(produtoAtualizar.getAmarzenamento()));
                JTextField descricaoField = new JTextField(produtoAtualizar.getDescricao());
                JTextField estoqueField = new JTextField(String.valueOf(produtoAtualizar.getEstoque()));
                JTextField corField = new JTextField(produtoAtualizar.getCor());

                Object[] message = {
                    "Modelo:", modeloField,
                    "Preço:", precoField,
                    "Armazenamento:", armazenamentoField,
                    "Descrição:", descricaoField,
                    "Estoque:", estoqueField,
                    "Cor:", corField
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Atualizar Produto", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    produtoAtualizar.setModelo(modeloField.getText());
                    produtoAtualizar.setPreco(Double.parseDouble(precoField.getText()));
                    produtoAtualizar.setAmarzenamento(Integer.parseInt(armazenamentoField.getText()));
                    produtoAtualizar.setDescricao(descricaoField.getText());
                    produtoAtualizar.setEstoque(Integer.parseInt(estoqueField.getText()));
                    produtoAtualizar.setCor(corField.getText());
                    produtoService.atualizarProduto(produtoAtualizar);
                    JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso");
                    atualizarListaProdutos(null);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarListaUsuarios(JList<Usuario> listaUsuarios) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        DefaultListModel<Usuario> model = new DefaultListModel<>();
        for (Usuario usuario : usuarios) {
            model.addElement(usuario);
        }
        listaUsuarios.setModel(model);
    }

    private void atualizarListaProdutos(JList<Produto> listaProdutos) {
        List<Produto> produtos = produtoService.listarProdutos();
        DefaultListModel<Produto> model = new DefaultListModel<>();
        for (Produto produto : produtos) {
            model.addElement(produto);
        }
        listaProdutos.setModel(model);
    }

    private void styleActionButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void styleControlButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    
}
