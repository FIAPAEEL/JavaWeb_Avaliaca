package br.com.fiap.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.fiap.entity.Curso;
import br.com.fiap.entity.Disciplina;
import br.com.fiap.entity.Escola;
import br.com.fiap.dao.JpaUtil;

public class GenericDao<T> implements Dao<T> {

	private final Class<T> classe;
	protected EntityManager em;

	public GenericDao(Class<T> classe) {
		this.classe = classe;
	}

	@Override
	public void adicionar(T entidade) {
		em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(entidade);
		em.getTransaction().commit();
		em.close();

	}
	
	@Override
	public void cadastrar(T entidade) {
		em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(entidade);
		em.getTransaction().commit();
		em.close();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listar() {
		em = JpaUtil.getEntityManager();
		return em.createQuery("From " + classe.getSimpleName()).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> listarPorId(int id) {
		em = JpaUtil.getEntityManager();
		return em.createQuery("Select t From " + classe.getSimpleName()+" t Where t.id = :id").setParameter("id", id).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T buscar(int id) {
		em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		T entidade = (T) em.createQuery("Select t From " + classe.getSimpleName()+" t Where t.id = :id").setParameter("id", id).getSingleResult();
		em.getTransaction().commit();
		em.close();

		return entidade;
	}
	
	@SuppressWarnings("unchecked")
	public T fazerLogin(String usuario, String senha ){
		em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		System.out.println("Select t From "+ classe.getSimpleName()+" t Where t.login = :usuario and t.senha = :senha");
		Query query =  em.createQuery("Select t From "+ classe.getSimpleName()+" t Where t.login = :usuario and t.senha = :senha");		
		query.setParameter("usuario", usuario);
		query.setParameter("senha", senha);
		em.getTransaction().commit();
		T entidade = (T) query.getSingleResult();
		em.close();
		
		return entidade;
	}
	
	public Escola listarCursosPorEscola(int id){
		em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("Select e From Escola e Where e.id = :id");
		query.setParameter("id", id);
		em.getTransaction().commit();
		
		Escola escola = (Escola) query.getSingleResult();
		
		return escola;		
	}
	
	public Curso listarDisciplinasPorEscola(int id){
		em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("Select c From Curso c Where c.id = :id");
		query.setParameter("id", id);
		em.getTransaction().commit();
		
		Curso curso = (Curso) query.getSingleResult();
		
		return curso;		
	}
	
	@SuppressWarnings("unchecked")
	public List<Disciplina> buscraDisciplinaPorCursoEAluno(int id){
		em = JpaUtil.getEntityManager();
		em.getTransaction().begin();
		Query query =  em.createQuery("Select d From Disciplina d Where d.curso.id = :id");		
		query.setParameter("id", id);
		em.getTransaction().commit();
		List<Disciplina> disciplinas = query.getResultList();
		em.close();
		
		return disciplinas;
	}
	
}
