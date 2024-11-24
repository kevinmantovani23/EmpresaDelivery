package labBd.trabalhoDelivery.model;
 
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
	
	
	 private Pedido pedido; 
	 

	private Prato prato;
	

	private Porcao porcao;

}
