package labBd.trabalhoDelivery.model;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PratoIngredienteID {
	
	private static final long serialVersionUID = 1L;
	 

	private Prato prato;
	
	private Ingrediente ingrediente;
}
