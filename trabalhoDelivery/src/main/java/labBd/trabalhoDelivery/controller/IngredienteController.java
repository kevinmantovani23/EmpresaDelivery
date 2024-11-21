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
import labBd.trabalhoDelivery.repository.IngredienteRepository;

@Controller
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    private List<Ingrediente> listaIngredientes = new ArrayList<>();

    @GetMapping("/ingredientes")
    public ModelAndView listarIngredientes() {
        ModelAndView model = new ModelAndView("ingredientesForm");
        model.addObject("ingredientes", listaIngredientes);
        model.addObject("ingrediente", new Ingrediente());
        return model;
    }

    @GetMapping("/ingredientes/editar/{nome}")
    public ModelAndView editarIngrediente(@PathVariable("nome") String nome) {
        Ingrediente ingrediente = ingredienteRepository.findById(nome).orElse(null);
        ModelAndView model = new ModelAndView("ingredientesForm");
        model.addObject("ingrediente", ingrediente);
        model.addObject("ingredientes", listaIngredientes);
        return model;
    }

    @PostMapping("/ingredientes")
    public ModelAndView ingredienteCreate(@ModelAttribute("ingrediente") Ingrediente ingrediente, 
            @RequestParam("acao") String acao) {
        if ("gravar".equals(acao)) {
            ingredienteRepository.save(ingrediente);
        } else if ("pesquisar".equals(acao)) {
            if (ingrediente.getNome() == null || ingrediente.getNome().isBlank()) {
                listaIngredientes.clear();
                listaIngredientes.addAll(ingredienteRepository.findAll());
            } else {
                listaIngredientes.clear();
                listaIngredientes.add(ingredienteRepository.findById(ingrediente.getNome()).orElse(null));
            }
        }
        ModelAndView model = new ModelAndView("ingredientesForm");
        model.addObject("ingredientes", listaIngredientes);
        model.addObject("ingrediente", new Ingrediente());
        return model;
    }

    @GetMapping("/ingredientes/delete/{nome}")
    public ModelAndView deleteIngrediente(@PathVariable("nome") String nome) {
        ingredienteRepository.deleteById(nome);
        listaIngredientes.clear();
        listaIngredientes.addAll(ingredienteRepository.findAll());
        ModelAndView model = new ModelAndView("ingredientesForm");
        model.addObject("ingredientes", listaIngredientes);
        model.addObject("ingrediente", new Ingrediente());
        return model;
    }
}