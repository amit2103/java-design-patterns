package cqrs.mr.commandhandlers;

import cqrs.core.Handler;
import cqrs.core.Repository;
import cqrs.mr.commands.UnlistStockFromExchange;
import cqrs.mr.domain.InventoryItem;

public class DeactivateInventoryItemHandler implements Handler<UnlistStockFromExchange> {
	private final Repository<InventoryItem> repository;
	
	public DeactivateInventoryItemHandler(Repository<InventoryItem> repository) {
		this.repository = repository;
	}

	@Override
	public void handle(UnlistStockFromExchange command) {
		InventoryItem item = repository.getById(InventoryItem.class,command.id);
		item.deactivate();
		repository.save(item, command.originalVersion);
	}
}
