package labBd.trabalhoDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import labBd.trabalhoDelivery.model.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, String> {

}
