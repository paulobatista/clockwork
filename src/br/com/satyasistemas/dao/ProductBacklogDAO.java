package br.com.satyasistemas.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.satyasistemas.dao.entity.ProductBacklog;

public class ProductBacklogDAO implements DAO<ProductBacklog>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager entityManager;
	
	public ProductBacklogDAO() {
		super();
		this.entityManager = DatabaseUtil.getEmf().createEntityManager();
	}

	@Override
	public void save(ProductBacklog backlog) {
		if (backlog.getNome() == null || backlog.getDemonstracao() == null
				|| backlog.getEstimativa() < 1 || backlog.getImportancia() < 1
				|| backlog.getSolicitante() == null
				|| backlog.getNome().equals("")
				|| backlog.getDemonstracao().equals("")
				|| backlog.getStatus() == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Erro ao cadastrar",
							"Dados inválidos, favor verificar os campos"));
		} else {
			beginTransaction();
			if (backlog.getId() > 0) {
				entityManager.merge(backlog);
			} else {
				entityManager.persist(backlog);
			}
			closeTransaction();
		}
	}

	@Override
	public ProductBacklog findById(int id) {		
		return null;
	}
	
	@Override
	public void delete(ProductBacklog backlog) {
		beginTransaction();
		entityManager.remove(backlog);
		closeTransaction();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBacklog> list() {
		Query query = entityManager.createNamedQuery("ProductBacklog.findAll");
		return query.getResultList();
	}

	@Override
	public List<ProductBacklog> pageList() {
		return null;
	}
	
	private void beginTransaction(){
		entityManager.getTransaction().begin();
	}
	
	private void closeTransaction(){
		entityManager.getTransaction().commit();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
