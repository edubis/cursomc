package com.curso;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.curso.domain.Categoria;
import com.curso.domain.Cidade;
import com.curso.domain.Cliente;
import com.curso.domain.Endereco;
import com.curso.domain.Estado;
import com.curso.domain.ItemPedido;
import com.curso.domain.Pagamento;
import com.curso.domain.PagamentoComBoleto;
import com.curso.domain.PagamentoComCartao;
import com.curso.domain.Pedido;
import com.curso.domain.Produto;
import com.curso.domain.enums.EstadoPagamento;
import com.curso.domain.enums.TipoCliente;
import com.curso.repositories.CategoriaRepository;
import com.curso.repositories.CidadeRepository;
import com.curso.repositories.ClienteRepository;
import com.curso.repositories.EnderecoRepository;
import com.curso.repositories.EstadoRepository;
import com.curso.repositories.ItemPedidoRepository;
import com.curso.repositories.PagamentoRepository;
import com.curso.repositories.PedidoRepository;
import com.curso.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	//cria dependencia
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	// popula o banco ao iniciar serviço do springboot
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");

		Produto p1 = new Produto(null, "Notebook", 900.00);
		Produto p2 = new Produto(null, "Garfo", 7.50);
		Produto p3 = new Produto(null, "Faca", 7.50);

		// armazena valores na classe categoriaRepository, devido ela ser responsável
		// pelo banco de categoria

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		// associações de categorias/produtos
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		// pega as setações e guarda o array na classe
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		// colocando dados na classe endereço e cidade do BD
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "aaaaa", "aaaa@gmail.com", "123123123", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("23145156","21451467"));

		Endereco e1 = new Endereco(null, "Rua aaaa aaaa", "123", "a123", "abcabc", "123123123", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua bbbb bbbb", "321", "b123", "asdasd", "123321321", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"),  cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1 , e2);
		
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2)); 
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
		
	}

}
