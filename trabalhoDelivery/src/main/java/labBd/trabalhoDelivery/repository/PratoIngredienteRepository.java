package labBd.trabalhoDelivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import labBd.trabalhoDelivery.model.Ingrediente;
import labBd.trabalhoDelivery.model.Prato;
import labBd.trabalhoDelivery.model.PratoIngrediente;
import labBd.trabalhoDelivery.model.PratoIngredienteID;

public interface PratoIngredienteRepository extends JpaRepository<PratoIngrediente, PratoIngredienteID> {

    void deleteByPrato(Prato prato);
    void deleteByPratoAndIngrediente(Prato prato, Ingrediente ingrediente);
    
    List<PratoIngrediente> findByPrato(Prato prato);
}
