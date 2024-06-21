package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import entidades.Pedido;
import util.HibernateUtil;


	public class PedidoDao {

		public void adicionarPedido(Pedido pedido) {
			Transaction transaction = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				transaction = session.beginTransaction();
				session.save(pedido);
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

		public void removerPedido(int id) {
			Transaction transaction = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				transaction = session.beginTransaction();
				Pedido pedido = session.get(Pedido.class, id);
				if (pedido != null) {
					session.remove(pedido);
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

		public List<Pedido> listarPedidos() {
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				String hql = "FROM Pedido";
				Query<Pedido> query = session.createQuery(hql, Pedido.class);
				return query.list();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				session.close();
			}
		}

		public Pedido getPedidoPorId(int id) {
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				return session.get(Pedido.class, id);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				session.close();
			}
		}
	
	}
