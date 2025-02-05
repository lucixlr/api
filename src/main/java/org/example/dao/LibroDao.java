package org.example.dao;

import org.example.entidades.Libro;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class LibroDao implements LibroDAOInterface {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public List<Libro> devolverTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Libro", Libro.class).list();
        }
    }

    @Override
    public Libro buscarPorId(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Libro.class, id);
        }
    }

    @Override
    public Libro create(Libro libro) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(libro);
            transaction.commit();
            return libro;
        }
    }

    @Override
    public Libro actualizar(Libro libro) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Libro libroExistente = session.get(Libro.class, libro.getId());
            if (libroExistente != null) {
                session.update(libro);
                transaction.commit();
                return libro;
            }
            return null;
        }
    }

    @Override
    public boolean deleteById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Libro libro = session.get(Libro.class, id);
            if (libro != null) {
                session.delete(libro);
                transaction.commit();
                return true;
            }
            return false;
        }
    }
}

