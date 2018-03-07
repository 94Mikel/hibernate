package com.ipartek.tallerHibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ipartek.model.*;

public class App {
	public static void main(String[] args) {

		Configuration cfg = new Configuration()
				.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
				.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/taller")
				.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
				.setProperty("hibernate.connection.username", "root")
				.setProperty("hibernate.connection.password", "root")
				.setProperty("hibernate.show_sql", "true")
				.setProperty("hibernate.current_session_context_class", "thread")
				.addAnnotatedClass(Usuario.class)
				.addAnnotatedClass(Mecanico.class)
				.addAnnotatedClass(Coche.class);
				

		SessionFactory sf = cfg.buildSessionFactory();

		Session s = sf.getCurrentSession();

		s.beginTransaction();
		
		EntityManager em = s.getEntityManagerFactory().createEntityManager();
		
		//PASO 1: Crear CriteriaBuilder y CriteriaQuery
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		
		//PASO 2: Configurar la clausula FROM
		Root<Usuario> root = criteria.from(Usuario.class);
		
		//PASO 3: Configurar la clausula SELECT
		criteria.select(root);
		
		//PASO 4: Cofigurar los criterios o predicates
		Predicate nombre = builder.like(root.<String>get("nombre"), "%b%");
		
		//PASO 5: Configurar la clausula WHERE usando los predicates
		criteria.where(nombre);
		
		//PASO 4 y 5 Juntos
		//criteria.where(builder.like(root.get("titulo"), "%b%"));
		
		//PASO 6: Ejecutar la Query
		List<Usuario> usuarios = em.createQuery(criteria).getResultList();
		
		s.getTransaction().commit();
		
		for(Usuario cd : usuarios){
			System.out.println(cd.getNombre());
		}
		
	}
}
