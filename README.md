MyContactsApp is a modular Java console application for managing contacts. It demonstrates object-oriented principles and classic design patterns through twelve use cases (UC‑01 to UC‑12).

Use Cases UC‑01: Registration Users can register as either FREE or PREMIUM. This use case demonstrates object creation using Factory and Builder patterns, with validation and repository persistence.

UC‑02: Login Users can log in using BasicAuth (email and password) or OAuth (email only). This use case applies the Strategy pattern to allow interchangeable authentication methods.

UC‑03: Profile Management Logged-in users can update their name, email, or password. This use case uses the Command pattern to encapsulate profile update actions.

UC‑04: Create Contact Users can create PersonContact or OrganizationContact and assign tags during creation. This use case demonstrates Factory, Builder, and Flyweight patterns, along with inheritance and set operations.

UC‑05: View Contact Details Contacts can be displayed in either basic or formatted style. This use case applies the Decorator pattern to extend display behavior dynamically.

UC‑06: Edit Contact Users can edit a contact’s name, phone, or email. The Command and Memento patterns are used to encapsulate edit actions and support undo functionality.

UC‑07: Delete Contact Users can delete contacts. Observers such as logging modules are notified automatically. This use case demonstrates the Observer pattern for event-driven updates.

UC‑08: Bulk Operations Users can perform bulk delete, bulk tag, or bulk export operations. The Composite pattern is used to treat groups of contacts and single contacts uniformly.

UC‑09: Search Contacts Users can search by name, phone, email, or tag. This use case applies a criteria-based approach, similar to the Strategy pattern, to encapsulate search logic.

UC‑10: Advanced Filtering Users can filter contacts by tag, date added, or frequency, and sort results by name, date, or frequency. This use case demonstrates composite filtering and comparator usage.

UC‑11: Manage Tags Users can create and list tags. The Flyweight pattern ensures that tags are shared instances, saving memory and enforcing consistency.

UC‑12: Apply Tags to Contacts Users can assign multiple tags to existing contacts. This use case demonstrates association handling between Contact and Tag, with observer notifications for updates.
