package pe.soapros.asistente.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	public List<TipoDocumento> getEmpresaList() {

		return em.createQuery("select p from TipoDocumento p order by p.id").getResultList();
	}

	@Transactional(readOnly = false)
	public void saveTipoDocumento(TipoDocumento tipo) {
		em.merge(tipo);

	}

}
