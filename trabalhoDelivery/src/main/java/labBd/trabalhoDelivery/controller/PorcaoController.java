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

import labBd.trabalhoDelivery.model.Porcao;
import labBd.trabalhoDelivery.repository.PorcaoRepository;

@Controller
public class PorcaoController {

    @Autowired
    private PorcaoRepository porcaoRepository;

    private List<Porcao> listaPorcoes = new ArrayList<>();

    @GetMapping("/porcoes")
    public ModelAndView listarPorcoes() {
        ModelAndView model = new ModelAndView("porcoesForm");
        model.addObject("porcoes", listaPorcoes);
        model.addObject("porcao", new Porcao());
        return model;
    }

    @GetMapping("/porcoes/editar/{nome}")
    public ModelAndView editarPorcao(@PathVariable("nome") String nome) {
        Porcao porcao = porcaoRepository.findById(nome).orElse(null);
        ModelAndView model = new ModelAndView("porcoesForm");
        model.addObject("porcao", porcao);
        model.addObject("porcoes", listaPorcoes);
        return model;
    }

    @PostMapping("/porcoes")
    public ModelAndView porcaoCreate(@ModelAttribute("porcao") Porcao porcao, 
            @RequestParam("acao") String acao) {
        if ("gravar".equals(acao)) {
            porcaoRepository.save(porcao);
        } else if ("pesquisar".equals(acao)) {
            listaPorcoes.clear();
            if (porcao.getNome() == null || porcao.getNome().isBlank()) {
                listaPorcoes.addAll(porcaoRepository.findAll());
            } else {
                listaPorcoes.add(porcaoRepository.findById(porcao.getNome()).orElse(null));
            }
        }
        ModelAndView model = new ModelAndView("porcoesForm");
        model.addObject("porcoes", listaPorcoes);
        model.addObject("porcao", new Porcao());
        return model;
    }

    @GetMapping("/porcoes/delete/{nome}")
    public ModelAndView deletePorcao(@PathVariable("nome") String nome) {
        porcaoRepository.deleteById(nome);
        listaPorcoes.clear();
        listaPorcoes.addAll(porcaoRepository.findAll());
        ModelAndView model = new ModelAndView("porcoesForm");
        model.addObject("porcoes", listaPorcoes);
        model.addObject("porcao", new Porcao());
        return model;
    }
}