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

import labBd.trabalhoDelivery.model.Ingrediente;
import labBd.trabalhoDelivery.model.Prato;
import labBd.trabalhoDelivery.model.PratoIngrediente;
import labBd.trabalhoDelivery.repository.IngredienteRepository;
import labBd.trabalhoDelivery.repository.PratoIngredienteRepository;
import labBd.trabalhoDelivery.repository.PratoRepository;

@Controller
public class PratoController {

    @Autowired
    private IngredienteRepository ingredienteRepository;
    
    @Autowired
    private PratoRepository pratoRepository;

    @Autowired
    private PratoIngredienteRepository pratoIngredienteRepository;

    private List<String> listaPratosComIngredientes = new ArrayList<>();

    @GetMapping("/pratos")
    public ModelAndView listarPratos() {
        listaPratosComIngredientes.clear(); 
        listaPratosComIngredientes.addAll(pratoRepository.listarPratosComIngredientes());

        ModelAndView model = new ModelAndView("pratosForm");
        model.addObject("pratosComIngredientes", listaPratosComIngredientes);
        model.addObject("prato", new Prato()); 
        return model;
    }

    @PostMapping("/pratos")
    public ModelAndView adicionarPrato(@ModelAttribute("prato") Prato prato) {
        pratoRepository.save(prato);
        return listarPratos(); 
    }

    @GetMapping("/pratos/editar/{id}")
    public ModelAndView editarPrato(@PathVariable("id") String id) {
        Prato prato = pratoRepository.findById(id).orElse(null);
        if (prato == null) {
            return listarPratos(); 
        }
         
        List<PratoIngrediente> ingredientes = pratoIngredienteRepository.findByPrato(prato);
        
        ModelAndView model = new ModelAndView("pratosForm");
        model.addObject("prato", prato);
        model.addObject("ingredientes", ingredientes);
        return model;
    }

    @PostMapping("/pratos/editar")
    public ModelAndView atualizarPrato(@ModelAttribute("prato") Prato prato, @RequestParam List<String> ingredientes) {
        pratoRepository.save(prato);
       
        pratoIngredienteRepository.deleteByPrato(prato);
        
        for (String ingredienteNome : ingredientes) {
            Ingrediente ingrediente = ingredienteRepository.findById(ingredienteNome).orElse(null);
            if (ingrediente != null) {
                PratoIngrediente pratoIngrediente = new PratoIngrediente();
                pratoIngrediente.setPrato(prato);
                pratoIngrediente.setIngrediente(ingrediente);
                pratoIngredienteRepository.save(pratoIngrediente);
            }
        }

        return listarPratos();  
    }

    @GetMapping("/pratos/delete/{id}")
    public ModelAndView deletarPrato(@PathVariable("id") String id) {
        pratoRepository.deleteById(id);
        return listarPratos();  
    }

    @PostMapping("/pratos/ingredientes/adicionar")
    public ModelAndView adicionarIngredienteNoPrato(@RequestParam String pratoId, @RequestParam String ingredienteNome) {
        Prato prato = pratoRepository.findById(pratoId).orElse(null);
        Ingrediente ingrediente = ingredienteRepository.findById(ingredienteNome).orElse(null);
        
        if (prato != null && ingrediente != null) {
            PratoIngrediente pratoIngrediente = new PratoIngrediente();
            pratoIngrediente.setPrato(prato);
            pratoIngrediente.setIngrediente(ingrediente);
            pratoIngredienteRepository.save(pratoIngrediente);
        }

        return listarPratos();  
    }

    @GetMapping("/pratos/ingredientes/remover/{pratoId}/{ingredienteNome}")
    public ModelAndView removerIngredienteDoPrato(@PathVariable("pratoId") String pratoId, @PathVariable("ingredienteNome") String ingredienteNome) {
        Prato prato = pratoRepository.findById(pratoId).orElse(null);
        Ingrediente ingrediente = ingredienteRepository.findById(ingredienteNome).orElse(null);

        if (prato != null && ingrediente != null) {
            pratoIngredienteRepository.deleteByPratoAndIngrediente(prato, ingrediente);
        }

        return listarPratos();  
    }
}
