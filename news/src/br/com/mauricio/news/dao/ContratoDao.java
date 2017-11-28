package br.com.mauricio.news.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.mauricio.news.model.CCusto;
import br.com.mauricio.news.model.Contrato;
import br.com.mauricio.news.model.Login;
import br.com.mauricio.news.util.DateUtil;

public class ContratoDao {
    private EntityManager manager;
    private CriteriaBuilder cb;
    
    public ContratoDao(EntityManager manager){
        this.manager = manager;
        cb = this.manager.getCriteriaBuilder();
    }
        
    public List<Contrato> listaContratosAtivos(boolean ativo) {
        CriteriaQuery<Contrato> query = cb.createQuery(Contrato.class);
        Root<Contrato> root = query.from(Contrato.class);        
        Path<Boolean> path = root.get("ativo");        
        Predicate predicate = cb.equal(path, ativo);
        Order id = cb.desc(root.get("id"));
        query.where(predicate).orderBy(id);
        TypedQuery<Contrato> typedQuery = manager.createQuery(query);        
        return typedQuery.getResultList();    
    }

    public List<Contrato> listaContratosByCCusto(List<String> custos,boolean ativo) {
        CriteriaQuery<Contrato> query = cb.createQuery(Contrato.class);
        Root<Contrato> root = query.from(Contrato.class);        
        Path<Boolean> path1 = root.get("ativo");
        Path<String> pathCc = root.<Login>get("usuario").<CCusto>get("custo").<String>get("codigo");        
        Predicate predicate1 = cb.equal(path1, ativo);
        Predicate predicate2 = cb.isTrue(pathCc.in(custos));        
        query.where(predicate1,predicate2);
        TypedQuery<Contrato> typedQuery = manager.createQuery(query);        
        return typedQuery.getResultList();
    }
    
    public List<String> emailsCadastrados(String campo){
        CriteriaQuery<Contrato> query = cb.createQuery(Contrato.class);  
        Root<Contrato> root = query.from(Contrato.class); 
        query.where(cb.notEqual(root.get(campo),""),cb.isNotNull(root.get(campo)));

        TypedQuery<Contrato> typedQuery = manager.createQuery(query);        
        List<Contrato> cs = typedQuery.getResultList();            
        Set<String> emails = cs.stream().filter(c -> !c.getEmailsAviso().isEmpty())
            .map(c -> c.getEmailsAviso().split(",")).flatMap(Arrays::stream).collect(Collectors.toSet());
        return new ArrayList<String>(emails);    
    }

    
    @SuppressWarnings("unchecked")
    public List<Contrato> listPrimeiroAviso() {
        Boolean ativo=true;
        String sql=" from contrato where DATEADD(DAY, -1*(diasAviso), fim) = :hoje and ativo= :ativo";
        Date hoje = DateUtil.hoje();
        return manager.createQuery(sql).setParameter("ativo", ativo).setParameter("hoje", hoje).getResultList();    
    }
    
    @SuppressWarnings("unchecked")
    public List<Contrato> listAvisoIGPM() {
        Boolean ativo=true;
        String sql=" from contrato where avigpm= :hoje and ativo= :ativo";
        Date hoje = DateUtil.hoje();
        return manager.createQuery(sql).setParameter("ativo", ativo).setParameter("hoje", hoje).getResultList();    
    }    
    
    
}