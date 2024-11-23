package labBd.trabalhoDelivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pratoIngrediente")
@Data
@IdClass(PratoIngredienteID.class)
public class PratoIngrediente {

	@Id
    @ManyToOne
    @JoinColumn(name = "pratoCodigo", nullable = false)
    private Prato prato;

    @Id
    @ManyToOne
    @JoinColumn(name = "ingredienteNome", nullable = false)
    private Ingrediente ingrediente;
    
    
}
