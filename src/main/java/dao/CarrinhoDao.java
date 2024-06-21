package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import entidades.Carrinho;
import util.HibernateUtil;

public class CarrinhoDao {

	public void adicionarCarrinho(Carrinho carrinho) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try  {
            transaction = session.beginTransaction();
            
            // Garante que os itens do carrinho estão na mesma sessão
            session.saveOrUpdate(carrinho); 
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close(); // Fecha a sessão ao finalizar
        }
    }


    public List<Carrinho> listarCarrinhos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Carrinho";
            Query<Carrinho> query = session.createQuery(hql, Carrinho.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Carrinho getCarrinhoPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Carrinho.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void removerCarrinho(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Carrinho carrinho = session.get(Carrinho.class, id);
            if (carrinho != null) {
                session.remove(carrinho);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
}