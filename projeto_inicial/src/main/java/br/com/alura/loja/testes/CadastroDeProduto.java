package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {
		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		Produto produto = produtoDao.buscarPorId(1L);
		System.out.println(produto.getNome());
		
		List<Produto> produtos = produtoDao.buscarTodos();
		produtos.forEach(prod -> System.out.println(prod.getNome()));
		
		List<Produto> produtosPorNome = produtoDao.buscarPorNome("Xiomi Redmi");
		produtosPorNome.forEach(prod -> System.out.println(prod.getNome()));
		
		List<Produto> produtosPorCategoria = produtoDao.buscarPorCategoria("CELULARES");
		produtosPorCategoria.forEach(prod -> System.out.println(prod.getNome()));
	}
	
	public static void cadastrarProduto() {
		
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("Xiomi Redmi", "Muito legal", new BigDecimal("800"), celulares );
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);

		em.getTransaction().begin();
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		em.getTransaction().commit();
		em.close();
		
	}

}
