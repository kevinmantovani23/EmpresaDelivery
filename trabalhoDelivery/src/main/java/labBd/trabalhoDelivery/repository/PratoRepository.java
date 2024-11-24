package labBd.trabalhoDelivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import labBd.trabalhoDelivery.model.Prato;

public interface PratoRepository  extends JpaRepository<Prato, String> {

	@Query(value = "SELECT * FROM dbo.fn_pratoIngrediente(:pratoNome)", nativeQuery = true)
    List<Prato> cursorPratoIngrediente(String pratoNome);
	
	Prato findByNome(String nome);
}
