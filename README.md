# food-ordering-system
A console-based Food Ordering Application, built in Java, adhering to SOLID principles, design patterns, and the Controller-Service-Repository-Model architecture for readable, scalable, maintainable and testable code. Designed to simplify order assignment to restaurant and demonstrate clean coding practices.

Features
1. Unboarding of a new restaurant. Allows different restaurants to have different price for the same item.
2. Rating has ability to set max orders they can process simultaneously.
3. Updating of its menu
4. Rating of restaurants
3. Auto assigment of orders to restaurants based on Assignment strategies: (1) LowestCost (2) Highest ratings
4. Restaurant can mark order as completed
5. Registering a new user
5. Customer can place order by entering items, their quantity, selection strategies and whether they allow order to split
6. **_Supports order splitting and placing on multiple restaurants_** i.e. if single restaurants can service all the items, then system will automatically split the orders into parts ensuring they get minimum cost or best rating depending on what they chose.