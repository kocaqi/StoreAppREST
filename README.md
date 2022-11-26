# StoreAppREST
This an application that is used for a store/shop, to manage orders, products, clients, and users.

There is a default user created when the program is launched, having credentials: 

{ email: "string@gmail.com",  password: "string"  }

Each user can have an "ADMIN" or "OPERATOR" role. The default user is assigned as Admin.

Users which are assigned as Admins can:
1. Create new users and assign roles to them
2. Add new roles to users or remove them
3. Update existing users'info
4. Get info of all users, or a single one, using his/her id

Also, users assigned as Admins can:
1. Create new products
2. Edit these products
3. Get info of all products, or a single one, using its id

Users assigned as either Admins or Operators can:
1. Register new Clients - every client is automatically assigned a user (the one who registered him/her)
2. Edit the info of existing clients - Admis of all clients, Operators only of their clients
3. Get info of all clients, if they are an Admin, or of their clients only, if they are Operators
4. Get the info of a single client - of any client if they are an Admin, or of one of their clients if they are an Operator

Also, users assigned as either Admins of Operators can:
1. Create new orders and assign to them a client - each order is automatically assigned to a user (the one who created it)
2. Add products to these orders - to any order if they are an Admin, or only to their orders if they are an Operator
3. Remove products from these orders - from any order if they are an Admin, or only from their orders if they are an Operator
4. Get info of all orders, if they are an Admin, or of their orders only, if they are Operators
5. Get the info of a single order - of any client if they are an Admin, or of one of their orders if they are an Operator
