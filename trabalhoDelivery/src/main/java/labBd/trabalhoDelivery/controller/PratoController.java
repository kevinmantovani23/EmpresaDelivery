package labBd.trabalhoDelivery.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import labBd.trabalhoDelivery.model.Ingrediente;
import labBd.trabalhoDelivery.model.Prato;
import labBd.trabalhoDelivery.model.PratoIngrediente;
import labBd.trabalhoDelivery.repository.IngredienteRepository;

@Controller
public class PratoController {

    @Autowired
    private PratoRepository pratoRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private PratoIngredienteRepository pratoIngredienteRepository;

    private List<Prato> listaPratos = new ArrayList<>();
    private List<Ingrediente> listaIngredientes = new ArrayList<>();

    @GetMapping("/pratos")
    public ModelAndView listarPratos() {
        ModelAndView model = new ModelAndView("pratosForm");
        listaPratos.clear();
        listaPratos.addAll(pratoRepository.findAll());
        model.addObject("pratos", listaPratos);
        model.addObject("prato", new Prato());
        return model;
    }

    @GetMapping("/pratos/editar/{id}")
    public ModelAndView editarPrato(@PathVariable("id") String id) {
        Prato prato = pratoRepository.findById(id).orElse(null);
        listaIngredientes.clear();
        listaIngredientes.addAll(ingredienteRepository.findAll());
        ModelAndView model = new ModelAndView("pratos");
        model.addObject("prato", prato);
        model.addObject("pratos", listaPratos);
        model.addObject("ingredientes", listaIngredientes);
        return model;
    }

    @PostMapping("/pratos")
    public ModelAndView pratoCreate(@ModelAttribute("prato") Prato prato, 
                                    @RequestParam("acao") String acao, 
                                    @RequestParam(name = "ingredientes", required = false) List<String> ingredientesIds) {
        
        if ("gravar".equals(acao)) {
            pratoRepository.save(prato);
            if (ingredientesIds != null && !ingredientesIds.isEmpty()) {
                for (String ingredienteId : ingredientesIds) {
                    Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId).orElse(null);
                    if (ingrediente != null) {
                        PratoIngrediente pratoIngrediente = new PratoIngrediente();
                        pratoIngrediente.setPrato(prato);
                        pratoIngrediente.setIngrediente(ingrediente);
                        pratoIngredienteRepository.save(pratoIngrediente);
                    }
                }
            }
        } else if ("pesquisar".equals(acao)) {
            listaPratos.clear();
            if (prato.getNome() == null || prato.getNome().isBlank()) {
                listaPratos.addAll(pratoRepository.findAll());
            } else {// Lembrar de listar por UDF COM CURSOR
                listaPratos.addAll(pratoRepository.findByNomeContaining(prato.getNome()));
            }
        }
        ModelAndView model = new ModelAndView("pratos");
        model.addObject("pratos", listaPratos);
        model.addObject("prato", new Prato());
        return model;
    }

    @GetMapping("/pratos/delete/{id}")
    public ModelAndView deletePrato(@PathVariable("id") String id) {
        pratoRepository.deleteById(id);
        listaPratos.clear();
        listaPratos.addAll(pratoRepository.findAll());
        ModelAndView model = new ModelAndView("pratos");
        model.addObject("pratos", listaPratos);
        model.addObject("prato", new Prato());
        return model;
    }

    @GetMapping("/pratos/ingredientes/{id}")
    public ModelAndView listarIngredientesDoPrato(@PathVariable("id") String id) {
        // Lembrar de listar por UDF COM CURSOR
        List<PratoIngrediente> pratoIngredientes = pratoIngredienteRepository.findByPratoId(id);
        ModelAndView model = new ModelAndView("pratoIngredientes");
        model.addObject("pratoIngredientes", pratoIngredientes);
        return model;
    }
}