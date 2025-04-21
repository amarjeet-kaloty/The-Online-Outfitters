Steps
- Setup SQL Database in application.properties
- Implement User, Address, and Coupon entity


Entity Relationships

1. User
- One-to-Many with Address: A user can have multiple addresses.
- Many-to-Many with Coupons: Users can use multiple coupons, and a 
    coupon can be used by multiple users.
- 
