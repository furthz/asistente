package pe.soapros.asistente.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.soapros.asistente.domain.TipoDocumento;

@Repository(value = "tipoDocumentoDao")
public class JPATipoDocumentoDao implements TipoDocumentoDao {

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
	public List<TipoDocumento> getTiposDocumentos() {

		return em.createQuery("select p from TipoDocumento p order by p.id").getResultList();
	}

	@Transactional(readOnly = false)
	public void saveTipoDocumento(TipoDocumento tipo) {
		em.merge(tipo);

	}

	public List<TipoDocumento> getTiposDocumentosById(Long id) {

		System.out.println("JPA Empresa: " + id);

		TypedQuery<TipoDocumento> query = em.createQuery("select em from TipoDocumento em join fetch em.archivo where em.archivo.id = :id order by em.id", TipoDocumento.class);

		return query.setParameter("id", id).getResultList();

		
	}

}
