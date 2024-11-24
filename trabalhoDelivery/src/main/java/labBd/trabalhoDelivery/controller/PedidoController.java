package labBd.trabalhoDelivery.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import labBd.trabalhoDelivery.model.Ingrediente;
import labBd.trabalhoDelivery.model.Item;
import labBd.trabalhoDelivery.model.Pedido;
import labBd.trabalhoDelivery.model.Porcao;
import labBd.trabalhoDelivery.model.Prato;
import labBd.trabalhoDelivery.model.PratoIngrediente;
import labBd.trabalhoDelivery.repository.ClienteRepository;
import labBd.trabalhoDelivery.repository.IngredienteRepository;
import labBd.trabalhoDelivery.repository.ItemRepository;
import labBd.trabalhoDelivery.repository.PedidoRepository;
import labBd.trabalhoDelivery.repository.PorcaoRepository;
import labBd.trabalhoDelivery.repository.PratoIngredienteRepository;
import labBd.trabalhoDelivery.repository.PratoRepository;

public class PedidoController {
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PratoRepository pratoRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PorcaoRepository porcaoRepository;

	private List<Prato> listaPratos = new ArrayList<>();
	private List<Pedido> listaPedidos = new ArrayList<>();
	private List<Porcao> listaPorcoes = new ArrayList<>();

	@GetMapping("/pedidos")
	public ModelAndView realizarPedido() {
		listaPedidos.addAll(pedidoRepository.findAll());
		listaPratos.clear();
		listaPorcoes.clear();
		listaPorcoes.addAll(porcaoRepository.findAll());
		listaPratos.addAll(pratoRepository.cursorPratoIngrediente("."));
		ModelAndView model = new ModelAndView("pedidosForm");
		model.addObject("listaPratos", listaPratos);
		model.addObject("listaPedidos", listaPedidos);
		model.addObject("listaPorcoes", listaPorcoes);
		model.addObject("pedido", new Pedido());
		return model;
	}	

	@PostMapping("/pedidos")
	public ModelAndView adicionarPrato(@ModelAttribute("prato") Pedido pedido, @RequestParam("acao") String acao,
			@RequestParam(value = "pratosNome", required = false) List<String> pratosNomes,
			@RequestParam(value = "porcoesNome", required = false) List<String> porcoesNomes,
			@RequestParam(value = "cpf", required = false) String cpf){
		
		if ("gravar".equals(acao)) {
			pedido.setCliente(clienteRepository.findById(cpf).get());
			pedido.setDataRealizacao(LocalDate.now());
			pedidoRepository.save(pedido);
			int i = 0;
			for (String prato : pratosNomes) {
				Porcao por = porcaoRepository.findById(porcoesNomes.get(i)).get();
				Item item = new Item();
				item.setPorcao(por);
				item.setPedido(pedido);
				item.setPrato(pratoRepository.findByNome(prato));
				itemRepository.save(item);
				i++;
			}
		} 
		
		ModelAndView model = new ModelAndView("pedidosForm");
		model.addObject("listaPratos", listaPratos);
		model.addObject("listaPedidos", listaPedidos);
		model.addObject("listaPorcoes", listaPorcoes);
		model.addObject("pedido", new Pedido());
		return model;
	}

	@GetMapping("/pedidos/editar/{id}")
	public ModelAndView editarPedido(@PathVariable("id") long id) {
		Pedido pedido = pedidoRepository.findById(id).orElse(null);
		ModelAndView model = new ModelAndView("pedidosForm");
		model.addObject("listaPratos", listaPratos);
		model.addObject("listaPedidos", listaPedidos);
		model.addObject("listaPorcoes", listaPorcoes);
		model.addObject("pedido", pedido);
		return model;
	}

	@GetMapping("/pedidos/lista/delete/{id}")
	public ModelAndView deletePedido(@PathVariable("id") long id) {
		pedidoRepository.deleteById(id);
		listaPratos.clear();
		listaPratos.addAll(pratoRepository.cursorPratoIngrediente("."));
		ModelAndView model = new ModelAndView("pedidosForm");
		model.addObject("listaPratos", listaPratos);
		model.addObject("listaPedidos", listaPedidos);
		model.addObject("listaPorcoes", listaPorcoes);
		model.addObject("prato", new Prato());
		return model;
	}
}
