package projeto_jsf_gerenciador_tarefas;

import javax.persistence.TypedQuery;

import com.projeto.lazy.ModelTarefaDataLazy;
import com.projeto.model.ModelTarefa;
import com.projeto.model.ModelUsuario;
import com.projeto.util.JPAUtil;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    @org.junit.Test
    public void testApp()
    {
    	ModelTarefaDataLazy tarefaDataLazyCompletas = new ModelTarefaDataLazy();
//		String hql="SELECT tu.tarefaUsuarioId.tarefa FROM TarefaUsuario tu WHERE tu.tarefaUsuarioId.usuario = :usuario";
//    	String hql="SELECT count(1) FROM ModelTarefa t  JOIN t.convidados c Where t.dataTermino IS NOT  null AND c.id = :usuario";
		ModelUsuario user= new ModelUsuario();
		user.setId(6L);
		
		String hqlConsulta="SELECT t FROM ModelTarefa t  JOIN t.convidados c Where t.dataTermino IS NOT  null AND c.id = :usuario";
    	String hqlContar="SELECT count(1) FROM ModelTarefa t  JOIN t.convidados c Where t.dataTermino IS NOT  null AND c.id = :usuario";

		TypedQuery<ModelTarefa> query = JPAUtil.getEntityManager().createQuery(hqlConsulta,ModelTarefa.class);
		query.setParameter("usuario", 6L);
		
		
		tarefaDataLazyCompletas.setQueryBusca(query);
		
		
		TypedQuery<Long> queryContar = JPAUtil.getEntityManager().createQuery(hqlContar, Long.class);
		queryContar.setParameter("usuario", 6L); // Substitua 'userId' pelo valor do usuário desejado
		
		tarefaDataLazyCompletas.setQueryContar(queryContar);
		
		
		
		
		
//		Object singleResult = JPAUtil.getEntityManager().createQuery(hql).setParameter("usuario", user.getId()).getSingleResult();
		System.out.println(tarefaDataLazyCompletas.load(0, 3, null, null));
    		
    }
}
