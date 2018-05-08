package pe.soapros.asistente.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.soapros.asistente.domain.UploadFile;

@Repository(value = "fileUploadDao")
public class JPAFileUploadDao implements FileUploadDao {

	private EntityManager em = null;

	/*
	 * Sets the entity manager.
	 */
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Transactional(readOnly = false)
	public void save(UploadFile uploadFile) {
		em.merge(uploadFile);
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<UploadFile> getFiles() {
		return em.createQuery("select p from UploadFile p order by p.fecha").getResultList();

	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<UploadFile> getFileWithSoons() {

		return em.createQuery("select p from UploadFile p join fetch p.empresa order by p.empresa.nombre").getResultList();
	}

	@Override
	public UploadFile findByName(String name) {

		TypedQuery<UploadFile> query = em.createQuery("select p from UploadFile p where p.fileName = :name",
				UploadFile.class);

		return query.setParameter("name", name).getSingleResult();
	}

	@Override
	public List<UploadFile> findByEmpresaNombre(String texto) {

		TypedQuery<UploadFile> query = em.createQuery("select p from UploadFile p join fetch p.empresa where UPPER(p.empresa.nombre) like :name order by p.empresa.nombre",
				UploadFile.class);
		
		return query.setParameter("name", "%" + texto + "%").getResultList();
		
	}

	@Override
	public UploadFile findById(long id) {
	
		TypedQuery<UploadFile> query = em.createQuery("select p from UploadFile p join fetch p.empresa where p.id = :id",
				UploadFile.class);
		
		return query.setParameter("id", id).getSingleResult();
	}
}
