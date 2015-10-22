package cqrs.mr.commandhandlers;

import cqrs.core.Handler;
import cqrs.core.Repository;
import cqrs.mr.commands.AddStocktoPortFolio;
import cqrs.mr.domain.InventoryItem;

public class CheckInItemsToInventoryHandler implements Handler<AddStocktoPortFolio> {
	private final Repository<InventoryItem> repository;

	public CheckInItemsToInventoryHandler(Repository<InventoryItem> repository) {
		this.repository = repository;
	}
	
	@Override
	public void handle(AddStocktoPortFolio message) {
		InventoryItem item = repository.getById(InventoryItem.class, message.id);
		item.checkIn(message.count);
		repository.save(item,message.originalVersion);
	}
}
