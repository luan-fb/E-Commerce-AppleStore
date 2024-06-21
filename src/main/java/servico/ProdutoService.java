package servico;

import java.util.List;

import javax.swing.JOptionPane;

import dao.ProdutoDao;
import entidades.Produto;
import exception.DadoNuloException;
import exception.DadoInvalidoException;

public class ProdutoService {
           
	ProdutoDao produtoDao = new ProdutoDao();

    public void adicionarProduto(Produto produto) {
        try {
            validarProduto(produto);

            Produto produtoExistente = produtoDao.getProdutoByExistente(produto.getModelo(), produto.getCor(), produto.getAmarzenamento());
            if (produtoExistente != null) {
                produtoExistente.setEstoque(produtoExistente.getEstoque() + produto.getEstoque());
                produtoDao.atualizarProduto(produtoExistente);
                JOptionPane.showMessageDialog(null, "Produto já cadastrado, estoque atualizado com sucesso");
            } else {
                produtoDao.adicionarProduto(produto);
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
            }
        } catch (DadoNuloException | DadoInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removerProduto(int id) {
        try {
            Produto produto = produtoDao.getProdutoById(id);
            if (produto == null) {
                throw new DadoNuloException("Produto não encontrado");
            }
            produtoDao.removerProduto(id);
            JOptionPane.showMessageDialog(null, "Produto removido com sucesso");
        } catch (DadoNuloException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarProduto(Produto produto) {
        try {
            validarProduto(produto);

            Produto produtoExistente = produtoDao.getProdutoById(produto.getId());
            if (produtoExistente == null) {
                throw new DadoNuloException("Produto não encontrado");
            }

            produtoDao.atualizarProduto(produto);
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso");
        } catch (DadoNuloException | DadoInvalidoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Produto getProdutoById(int id) {
        return produtoDao.getProdutoById(id);
    }

    public List<Produto> listarProdutos() {
        return produtoDao.ListarProdutos();
    }

    public List<Produto> buscarProdutosPorFaixaPreco(double precoMin, double precoMax) {
        return produtoDao.buscarProdutosPorFaixaPreco(precoMin, precoMax);
    }

    private void validarProduto(Produto produto) throws DadoNuloException, DadoInvalidoException {
        if (produto == null) {
            throw new DadoNuloException("O produto não pode ser nulo");
        }
        if (produto.getModelo() == null || produto.getModelo().trim().isEmpty()) {
            throw new DadoNuloException("O nome do produto é obrigatório");
        }
        if (produto.getPreco() <= 0) {
            throw new DadoInvalidoException("O preço do produto deve ser maior que zero");
        }
        if (produto.getAmarzenamento() <= 0) {
            throw new DadoInvalidoException("O valor do armazenamento deve ser positivo");
        }
        if (produto.getDescricao() == null || produto.getDescricao().trim().isEmpty()) {
            throw new DadoNuloException("A descrição não pode ser vazia.");
        }
        if (produto.getEstoque() <= 0) {
            throw new DadoInvalidoException("A quantidade de estoque não pode ser negativa.");
        }
        if (produto.getCor() == null || produto.getCor().trim().isEmpty() || !produto.getCor().matches("[a-zA-Z]+")) {
            throw new DadoNuloException("A cor deve ser uma string não vazia contendo apenas letras.");
        }
    }
}

