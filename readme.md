
## Project description
OO lets users search a shopping list from all five top online supermarkets in Estonia -- Prisma, Rimi, Selver, Maxima, and Coop. The project will provide an overview of prices for each item in the list so that the user can make a more informed choice on where to shop, thus saving time and money.

It also provides multiple other useful features, such as saving your shopping list for future use and searching for discounts (either regular or by loyalty card), thus keeping you updated with the latest deals.

---

**Development of this project was split into three stages:**
## Stage 1
* The program will find products specified by a given keyword from all five leading Estonian supermarket chains (Coop, Rimi, Selver, Maxima & Prisma) by dynamically scraping HTML and JavaScript content from their online delivery websites to make a list of found products.
* The program can independently differentiate if a product is on sale and if the product is cheaper with said supermarkets discount card. It also saves the discount price and prediscount price for all products.

## Stage 2
* Most of the GUI is implemented using JavaFX
* Added registration and log-in support for multiple users.
* Made saving a shopping list for future usage possible.
* Made searching by a given list possible (each user has a file in which their shopping list is saved)
* Added multithread support for faster data collection.
* Organized the project with the use of enums.
* Fixed some runtime bugs and exceptions.
* Added a way to calculate the shortest bird’s-eye-view path between store locations given users’ starting coordinates

## Stage 3
* Better organizing the project hierarchy
* Finished & improved multithreading
* Fixed bugs and cleaned up code
* Implemented most non-trivial features from the project plan, [say](https://docs.google.com/document/d/1JOc7_v8bvRfY5J4m4vBAEK-nWTceQLzXzWNFVpN8ZZg/edit).
* Rewrote and cleaned up the GUI for a more dynamic experience (i.e adding event listeners)
* Added user and shopping functionality to the GUI (user can now choose from a list of items and see the price of all individual items as well as the total cost)
* Added profile viewing that contains a brief summary of the users purchasing history
* Added the ability to view history (for every shopping experience a file is saved containing information on it)
* Convenient google maps link for calculated shortest path between different stores

---

### How to run

* Run `GUI.java` from the `com.example.GUI` package.
* Create an account and log in
* Update your shopping list
* Go shopping, OO will automatically find all the cheapest products
* Get an overview of your shopping history

---
This project was created for the University of Tartu’s advanced object-oriented programming course in spring 2022 by Lauri Lüüsi and Marek Murumäe.

---
