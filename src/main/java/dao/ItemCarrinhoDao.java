package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import entidades.ItemCarrinho;
import util.HibernateUtil;

public class ItemCarrinhoDao {

	public void adicionarItemCarrinho(ItemCarrinho itemCarrinho) {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			session.save(itemCarrinho);
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

	public void removerItemCarrinho(int id) {
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			transaction = session.beginTransaction();
			ItemCarrinho itemCarrinho = session.get(ItemCarrinho.class, id);
			if (itemCarrinho != null) {
				session.remove(itemCarrinho);
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public List<ItemCarrinho> listarItensCarrinho() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String hql = "FROM ItemCarrinho";
			Query<ItemCarrinho> query = session.createQuery(hql, ItemCarrinho.class);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	public ItemCarrinho getItemCarrinhoPorId(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session.get(ItemCarrinho.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
}

