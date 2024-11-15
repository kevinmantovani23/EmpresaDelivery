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
public class ItemID {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
    @ManyToOne
    @JoinColumn(name = "pratoCodigo", nullable = false)
	private Prato prato;
	
	
	@Id
    @ManyToOne
    @JoinColumn(name = "porcaoNome", nullable = false)
	private Porcao porcao;

}
