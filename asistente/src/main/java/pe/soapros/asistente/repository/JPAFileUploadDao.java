package pe.soapros.asistente.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

		return em.createQuery("select p from UploadFile p join fetch p.empresa order by p.fecha").getResultList();
	}
}
