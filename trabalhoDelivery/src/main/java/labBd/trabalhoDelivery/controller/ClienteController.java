package labBd.trabalhoDelivery.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import labBd.trabalhoDelivery.model.Cliente;
import labBd.trabalhoDelivery.repository.ClienteRepository;

@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    private List<Cliente> lista = new ArrayList<>();

    @GetMapping("/clientes")
    public ModelAndView listarClientes() {
        lista.clear();
        lista.addAll(clienteRepository.findAll());
        ModelAndView model = new ModelAndView("clientesForm"); 
        model.addObject("clientes", lista);
        model.addObject("cliente", new Cliente()); 
        return model;
    }

    @GetMapping("/clientes/editar/{cpf}")
    public ModelAndView editarCliente(@PathVariable("cpf") String cpf) {
        Cliente cliente = clienteRepository.findById(cpf).orElse(new Cliente());
        ModelAndView model = new ModelAndView("clientesForm");
        model.addObject("cliente", cliente);
        model.addObject("clientes", lista);
        return model;
    }

    @PostMapping("/clientes")
    public ModelAndView clienteCreate(@ModelAttribute("cliente") Cliente cliente, @RequestParam("acao") String acao) {
        if ("gravar".equals(acao)) {
            clienteRepository.save(cliente);
        } else if ("pesquisar".equals(acao)) {
            if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
                lista.clear();
                lista.addAll(clienteRepository.findAll());
            } else {
                lista.clear();
                lista.addAll(clienteRepository.findByCpf(cliente.getCpf()));
            }
        }
        ModelAndView mv = new ModelAndView("clientesForm");
        mv.addObject("clientes", lista);
        mv.addObject("cliente", new Cliente());
        return mv;
    }

    @GetMapping("/clientes/delete/{cpf}")
    public ModelAndView deleteCliente(@PathVariable String cpf) {
        clienteRepository.deleteById(cpf);
        lista.clear();
        lista.addAll(clienteRepository.findAll());
        ModelAndView mv = new ModelAndView("clientesForm");
        mv.addObject("clientes", lista);
        mv.addObject("cliente", new Cliente());
        return mv;
    }
}