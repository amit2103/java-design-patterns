package com.iluwatar.cqrs;

import java.util.UUID;

import cqrs.core.Repository;
import cqrs.core.bus.FakeBus;
import cqrs.core.eventstore.InMemoryEventStore;
import cqrs.mr.commandhandlers.CheckInItemsToInventoryHandler;
import cqrs.mr.commandhandlers.CreateInventoryItemHandler;
import cqrs.mr.commandhandlers.DeactivateInventoryItemHandler;
import cqrs.mr.commandhandlers.RemoveItemsFromInventoryHandler;
import cqrs.mr.commandhandlers.RenameInventoryItemHandler;
import cqrs.mr.commands.AddStocktoPortFolio;
import cqrs.mr.commands.CreateStock;
import cqrs.mr.commands.UnlistStockFromExchange;
import cqrs.mr.commands.RemoveStockfromPortfolio;
import cqrs.mr.commands.RenameStock;
import cqrs.mr.domain.InventoryItem;
import cqrs.mr.domain.RepositoryImpl;
import cqrs.mr.events.InventoryItemCreated;
import cqrs.mr.events.InventoryItemDeactivated;
import cqrs.mr.events.InventoryItemRenamed;
import cqrs.mr.events.ItemsCheckedInToInventory;
import cqrs.mr.events.ItemsRemovedFromInventory;
import cqrs.mr.readModel.ListView;
import cqrs.mr.readModel.ReadModelFacade;
import cqrs.mr.readModel.detailsview.DetailsView;

public class App {
  
  public static void main(String[] args) {
    FakeBus bus = new FakeBus();

    InMemoryEventStore storage = new InMemoryEventStore(bus);
    
    //--command handlers
    Repository<InventoryItem> repo = new RepositoryImpl<InventoryItem>(storage);
    bus.registerHandler(CreateStock.class,new CreateInventoryItemHandler(repo));
    bus.registerHandler(AddStocktoPortFolio.class,new CheckInItemsToInventoryHandler(repo));
    bus.registerHandler(UnlistStockFromExchange.class,new DeactivateInventoryItemHandler(repo));
    bus.registerHandler(RemoveStockfromPortfolio.class,new RemoveItemsFromInventoryHandler(repo));
    bus.registerHandler(RenameStock.class,new RenameInventoryItemHandler(repo));
    
    //--event handlers: details view
    DetailsView detailsView = new DetailsView();
    bus.registerHandler(InventoryItemCreated.class,detailsView.createInventoryItemCreatedHandler());
    bus.registerHandler(InventoryItemDeactivated.class, detailsView.createInventoryItemDeactivatedHandler());
    bus.registerHandler(InventoryItemRenamed.class, detailsView.createInventoryItemRenamedHandler());
    bus.registerHandler(ItemsCheckedInToInventory.class, detailsView.createItemsCheckedInToInventoryHandler());
    bus.registerHandler(ItemsRemovedFromInventory.class, detailsView.createItemsRemovedFromInventoryHandler());

    //--event handlers: list view
    ListView listView = new ListView();
    bus.registerHandler(InventoryItemCreated.class, listView.createInventoryItemCreatedHandler());
    bus.registerHandler(InventoryItemRenamed.class, listView.createInventoryItemRenamedHandler());
    bus.registerHandler(InventoryItemDeactivated.class, listView.createInventoryItemDeactivatedHandler());


    ReadModelFacade readmodel = new ReadModelFacade();
    String name= "test";
    bus.send(new CreateStock(UUID.randomUUID(), name));
  }

}
