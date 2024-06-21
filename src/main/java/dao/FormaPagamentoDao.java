package dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import entidades.FormaPagamento;

public class FormaPagamentoDao {

    public void adicionarFormaPagamento(FormaPagamento formaPagamento) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(formaPagamento);
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

    public void removerFormaPagamento(int id) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            FormaPagamento formaPagamento = session.get(FormaPagamento.class, id);
            if (formaPagamento != null) {
                session.remove(formaPagamento);
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

    public List<FormaPagamento> listarFormasPagamento() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM FormaPagamento";
            Query<FormaPagamento> query = session.createQuery(hql, FormaPagamento.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    public FormaPagamento getFormaPagamentoPorId(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(FormaPagamento.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}