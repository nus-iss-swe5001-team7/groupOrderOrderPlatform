db = db.getSiblingDB("restaurant");

db.restaurants.deleteMany({});

db.menus.deleteMany({});

db.createCollection("restaurants");

db.createCollection("menus");
