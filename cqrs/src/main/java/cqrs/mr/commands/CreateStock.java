package cqrs.mr.commands;

import java.util.UUID;

import cqrs.core.Command;

public class CreateStock extends Command {

	public UUID id;
	public String name;

	public CreateStock(UUID id, String name) {
		this.id = id;
		this.name = name;
	}

}
