package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import entidades.Usuario;
import exception.DadoNuloException;
import util.HibernateUtil;

public class UsuarioDao {

	public void adicionarUsuario(Usuario usuario) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.save(usuario);
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

	public void removerUsuario(int id) {
		Transaction transaction = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			Usuario usuario = session.get(Usuario.class, id);
			if (usuario != null) {
				session.remove(usuario);
				transaction.commit();
			} else {
				throw new DadoNuloException("Usuario nao encontrado");
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

	public List<Usuario> listarUsuarios() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "FROM Usuario";
			Query<Usuario> query = session.createQuery(hql, Usuario.class);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Usuario getUsuarioByEmail(String email) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "FROM Usuario u WHERE u.email = :email";
			Query<Usuario> query = session.createQuery(hql, Usuario.class);
			query.setParameter("email", email);
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Usuario getUsuarioByNome(String nome) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "FROM Usuario u WHERE u.nome = :nome";
			Query<Usuario> query = session.createQuery(hql, Usuario.class);
			query.setParameter("nome", nome);
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	public Usuario getUsuarioById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Usuario.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
