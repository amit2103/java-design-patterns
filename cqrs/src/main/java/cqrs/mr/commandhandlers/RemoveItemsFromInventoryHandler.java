package cqrs.mr.commandhandlers;

import cqrs.core.Handler;
import cqrs.core.Repository;
import cqrs.mr.commands.RemoveStockfromPortfolio;
import cqrs.mr.domain.InventoryItem;

public class RemoveItemsFromInventoryHandler implements Handler<RemoveStockfromPortfolio>{
	private final Repository<InventoryItem> repository;
	
	public RemoveItemsFromInventoryHandler(Repository<InventoryItem> repository) {
		this.repository = repository;
	}

	@Override
	public void handle(RemoveStockfromPortfolio message) {
		InventoryItem item = repository.getById(InventoryItem.class, message.id);
		item.remove(message.count);
		repository.save(item, message.originalVersion);
	}

}
