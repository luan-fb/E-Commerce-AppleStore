package servico;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.CarrinhoDao;
import entidades.Carrinho;
import entidades.ItemCarrinho;
import entidades.Produto;
import entidades.Usuario;
import util.HibernateUtil;

public class CarrinhoService {

    private CarrinhoDao carrinhoDao = new CarrinhoDao();

    public Carrinho adicionarCarrinho(Carrinho carrinho) {
        carrinhoDao.adicionarCarrinho(carrinho);
        return carrinho;
    }

    public List<Carrinho> buscarTodosCarrinhos() {
        return carrinhoDao.listarCarrinhos();
    }

    public Carrinho buscarCarrinhoPorId(int id) {
        return carrinhoDao.getCarrinhoPorId(id);
    }

    public void deletarCarrinho(int id) {
        carrinhoDao.removerCarrinho(id);
    }

    public void adicionarProdutoAoCarrinho(Produto produto, Usuario usuario) {
        Carrinho carrinho = usuario.getCarrinho();

        // Abre uma nova sessão do Hibernate
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Inicia a transação
            transaction = session.beginTransaction();

            if (carrinho == null) {
                // Se o carrinho não existe para o usuário, cria um novo
                carrinho = new Carrinho(usuario);
                usuario.setCarrinho(carrinho);
            }

            boolean itemExists = false;

            for (ItemCarrinho item : carrinho.getItens()) {
                if (item.getProduto().equals(produto)) {
                    // Se o produto já existe no carrinho, incrementa a quantidade
                    item.setQuantidade(item.getQuantidade() + 1);
                    itemExists = true;
                    break;
                }
            }

            if (!itemExists) {
                // Se o produto não existe no carrinho, adiciona um novo ItemCarrinho
                ItemCarrinho itemCarrinho = new ItemCarrinho(produto, 1, produto.getPreco());
                carrinho.getItens().add(itemCarrinho);
            }

            // Após adicionar ou atualizar os itens, calcular o total do carrinho
            carrinho.calcularTotal();

            // Salva ou atualiza o carrinho na sessão atual
            session.saveOrUpdate(carrinho);

            // Comita a transação
            transaction.commit();
        } catch (Exception e) {
            // Em caso de erro, faz rollback da transação
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Fecha a sessão do Hibernate
            session.close();
        }
    }

    public void aplicarDescontoSeminovo(Produto semiNovo, Produto novoProduto, Usuario usuario) {
        Carrinho carrinho = usuario.getCarrinho();

        // Verifica se o produto seminovo está no carrinho
        boolean foundSemiNovo = false;
        for (ItemCarrinho item : carrinho.getItens()) {
            if (item.getProduto().equals(semiNovo)) {
                foundSemiNovo = true;
                break;
            }
        }

        if (foundSemiNovo) {
            // Aplica o desconto ao carrinho
            BigDecimal desconto = BigDecimal.valueOf(semiNovo.getPreco());
            carrinho.aplicarDesconto(desconto);

            // Atualiza o carrinho na sessão
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.saveOrUpdate(carrinho);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
    }
}