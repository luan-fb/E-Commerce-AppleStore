package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import entidades.Produto;
import exception.DadoNuloException;
import util.HibernateUtil;

public class ProdutoDao {

	public void adicionarProduto(Produto produto) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.save(produto);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public void removerProduto(int Id) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			Produto produto = session.get(Produto.class, Id);
			 if (produto != null) {
				 session.remove(produto);
			transaction.commit();
			 }
			 else {
				 throw new DadoNuloException("Produto nao encotrado");
			 }
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}

	public List<Produto> ListarProdutos() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Produto", Produto.class).list();
		}
	}

	public Produto getProdutoById(int id) {
		Transaction transaction = null;
		Produto produto = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			produto = session.get(Produto.class, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return produto;
	}

	public List<Produto> getProdutosByModelo(String nome) {
		Transaction transaction = null;
		List<Produto> produtos = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			produtos = session.createQuery("FROM Produto WHERE modelo = :modelo", Produto.class)
					.setParameter("modelo", nome).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return produtos;
	}
	
	public void atualizarProduto(Produto produto) {
	    Transaction transaction = null;
	    Session session = null;
	    try {
	        session = HibernateUtil.getSessionFactory().openSession();
	        transaction = session.beginTransaction();
	        session.update(produto); 
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	}
	
	public List<Produto> buscarProdutosPorFaixaPreco(double precoMin, double precoMax) {
	    Transaction transaction = null;
	    List<Produto> produtos = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        produtos = session.createQuery("FROM Produto WHERE preco BETWEEN :precoMin AND :precoMax", Produto.class)
	                .setParameter("precoMin", precoMin)
	                .setParameter("precoMax", precoMax)
	                .getResultList();
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
	    }
	    return produtos;
	}
	
	public Produto getProdutoByExistente(String modelo, String cor, int amarzenamento) {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        Query<Produto> query = session.createQuery("FROM Produto p WHERE p.modelo = :modelo AND p.cor = :cor AND p.amarzenamento = :armazenamento", Produto.class);
	        query.setParameter("modelo", modelo);
	        query.setParameter("cor", cor);
	        query.setParameter("armazenamento", amarzenamento);
	        return query.uniqueResult();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

}