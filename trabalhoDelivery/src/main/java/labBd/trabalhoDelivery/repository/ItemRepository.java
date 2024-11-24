package labBd.trabalhoDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import labBd.trabalhoDelivery.model.Item;
import labBd.trabalhoDelivery.model.ItemID; 

public interface ItemRepository extends JpaRepository<Item, ItemID> {

}
