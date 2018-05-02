package pe.soapros.asistente.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.soapros.asistente.domain.Empresa;

@Repository(value = "empresaDao")
public class JPAEmpresaDao implements EmpresaDao {

	private EntityManager em = null;

	/*
	 * Sets the entity manager.
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Empresa> getEmpresaList() {

		return em.createQuery("select p from Empresa p order by p.id").getResultList();
	}

	@Transactional(readOnly = false)
	public void saveEmpresa(Empresa emp) {
		em.merge(emp);
	}

	public Empresa buscarById(long id) {
		
		System.out.println("JPA Empresa: " + id);

		TypedQuery<Empresa> query = em.createQuery("select em from Empresa em where em.id = :id", Empresa.class);

		return query.setParameter("id", id).getSingleResult();

	}

	@Override
	public List<Empresa> buscarByName(String texto) {
		
		TypedQuery<Empresa> query = em.createQuery("select em from Empresa em where em.name like :name", Empresa.class);
		
		return query.setParameter("name", "%" + texto + "%").getResultList();
	}

}
