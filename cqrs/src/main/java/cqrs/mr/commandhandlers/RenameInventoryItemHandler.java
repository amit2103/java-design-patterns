package cqrs.mr.commandhandlers;

import cqrs.core.Handler;
import cqrs.core.Repository;
import cqrs.mr.commands.RenameStock;
import cqrs.mr.domain.InventoryItem;

public class RenameInventoryItemHandler implements Handler<RenameStock> {
	private final Repository<InventoryItem> repository;
	
	public RenameInventoryItemHandler(Repository<InventoryItem> repository) {
		this.repository = repository;
	}

	@Override
	public void handle(RenameStock command) {
		InventoryItem item = repository.getById(InventoryItem.class, command.id);
		item.changeName(command.newName);
		repository.save(item, command.originalVersion);
	}

}
