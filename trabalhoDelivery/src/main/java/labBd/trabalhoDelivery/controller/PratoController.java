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
import labBd.trabalhoDelivery.model.Porcao;
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

    private List<Prato> listaPratos = new ArrayList<>();
    private List<Ingrediente> listaIngrediente = new ArrayList<>();

    @GetMapping("/pratos")
    public ModelAndView listarPratos() {
    	listaIngrediente.clear();
    	listaIngrediente.addAll(ingredienteRepository.findAll());
        ModelAndView model = new ModelAndView("pratosForm");
        model.addObject("pratosComIngredientes", listaPratos);
        model.addObject("listaIngrediente", listaIngrediente);
        model.addObject("prato", new Prato()); 
        return model;
    }

    @PostMapping("/pratos")
    public ModelAndView adicionarPrato(@ModelAttribute("prato") Prato prato,
        @RequestParam("acao") String acao, @RequestParam(value ="ingredientesNome", required = false) List<String> ingredienteNomes) {
            if ("gravar".equals(acao)) {
                pratoRepository.save(prato);
                for(String ingrediente : ingredienteNomes) {
                	PratoIngrediente pi = new PratoIngrediente();
                	pi.setIngrediente(ingredienteRepository.getById(ingrediente));
                	pi.setPrato(prato);
                	pratoIngredienteRepository.save(pi);
                }
            } else if ("pesquisar".equals(acao)) {
                listaPratos.clear();
                if (prato.getNome() == null || prato.getNome().isBlank()) {
                    listaPratos.addAll(pratoRepository.cursorPratoIngrediente("."));
                } else {
                    listaPratos.addAll(pratoRepository.cursorPratoIngrediente(prato.getNome()));
                }
            }
            ModelAndView model = new ModelAndView("pratosForm");
            model.addObject("pratosComIngredientes", listaPratos);
            model.addObject("listaIngrediente", listaIngrediente);
            model.addObject("prato", new Prato()); 
            return model;
    }
    
    @GetMapping("/pratos/editar/{id}")
    public ModelAndView editarPrato(@PathVariable("id") String id) {
        Prato prato = pratoRepository.findById(id).orElse(null);
        ModelAndView model = new ModelAndView("pratosForm");
        model.addObject("pratosComIngredientes", listaPratos);
        model.addObject("listaIngrediente", listaIngrediente);
        model.addObject("prato", prato);
        return model;
    }
    
    
    @GetMapping("/pratos/delete/{id}")
    public ModelAndView deletePrato(@PathVariable("id") String id) {
        pratoRepository.deleteById(id);
        listaPratos.clear();
        listaPratos.addAll(pratoRepository.cursorPratoIngrediente("."));
        ModelAndView model = new ModelAndView("pratosForm");
        model.addObject("pratosComIngredientes", listaPratos);
        model.addObject("listaIngrediente", listaIngrediente);
        model.addObject("prato", new Prato()); 
        return model;
    }
}
