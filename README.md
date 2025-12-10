# Aggies Lost and Found (ALFapp)

> Android MVP app that connects students who lose items with students who find them, replacing paper flyers with a real-time, searchable lost-and-found system.

---

## Overview

Aggies Lost and Found (ALFapp) is a mobile application designed for North Carolina A&T State University students to report, search, and recover lost items on campus.  
The app uses Firebase as a cloud back end and follows the Model–View–Presenter (MVP) architecture, guided by full UML analysis and design artefacts (use cases, class diagrams, sequence diagrams, ERD, and object–relational diagrams).  [oai_citation:1‡ALFapp-2.pdf](sediment://file_00000000d50871f8a476d5e36e0daeff)  

The project was developed in an Agile setting for COMP 710 (Spring 2023), with iterative refinements to both the requirements and the implementation.

---

## Key Features

- **Report lost items**  
  Owners can create ads for lost items such as electronics, wallets, keys, and cards, including descriptions and images.

- **Search found items**  
  Finders can browse reported lost items by category and match items they have found to existing ads.

- **Finder–owner contact**  
  In-app flows allow finders to leave contact information for owners, enabling safe coordination to return items.  [oai_citation:2‡ALFapp-2.pdf](sediment://file_00000000d50871f8a476d5e36e0daeff)  

- **Monitor item status**  
  Owners (and campus security) can monitor the status of their posted ads and see when a finder has reported a match.

- **Decision logic for claims**  
  A decision table and tree (see page 6 of the report) capture the business rules for when an owner is allowed to claim a found item.  [oai_citation:3‡ALFapp-2.pdf](sediment://file_00000000d50871f8a476d5e36e0daeff)  

---

## Architecture and Design

- **Platform:** Android (Java, XML layouts)  
- **Backend:** Firebase (NoSQL database for owners, finders, and item ads)  
- **Pattern:** MVP  
  - **Model classes:** `Id_Bank_Cards`, `Wallet_Bags`, `Electronics`, `Finder`, `Owner`  
  - **Views:** XML activities and fragments (login, sign-up, post/delete ad, search, item details, finder info)  
  - **Presenters / Activities:** `Login`, `SignUp`, `MainActivity`, `UserProfile`, `ActivityPostOrDelAd`, `FindUpdateItem`, `FindItemUpdateList`, `FindersInfo`, etc.  [oai_citation:4‡ALFapp-2.pdf](sediment://file_00000000d50871f8a476d5e36e0daeff)  

Design is driven by:

- **Use case diagrams and narratives** for:
  - Lost Item Reporting  
  - Lost Item Searching  
  - Alerting Owners of Found Items  
  - Monitoring Lost Items  [oai_citation:5‡ALFapp-2.pdf](sediment://file_00000000d50871f8a476d5e36e0daeff)  
- **Class diagram** with generalization, aggregation, and composition relationships between `Person`, `Owner`, `Finder`, `Product`, and `Item` (see page 8).  [oai_citation:6‡ALFapp-2.pdf](sediment://file_00000000d50871f8a476d5e36e0daeff)  
- **Sequence diagrams** modeling core flows such as posting ads, finder updates, and owner–finder interactions (pages 11–12).  [oai_citation:7‡ALFapp-2.pdf](sediment://file_00000000d50871f8a476d5e36e0daeff)  
- **Entity–Relationship and object–relational diagrams** describing the Firebase data model for persons, items, and finder/owner relationships (pages 16–17).  [oai_citation:8‡ALFapp-2.pdf](sediment://file_00000000d50871f8a476d5e36e0daeff)  

---

## Core User Flows

1. **Lost Item Reporting (Owner)**  
   - Login → “Check or Create Ad”  
   - Enter item details and upload image  
   - Save to Firebase and receive confirmation

2. **Lost Item Searching (Finder)**  
   - Open app → “View Ad”  
   - Choose category (electronics, wallets, keys, etc.)  
   - Browse list, match item, contact owner or leave info  [oai_citation:9‡ALFapp-2.pdf](sediment://file_00000000d50871f8a476d5e36e0daeff)  

3. **Alerting Owner of Found Item (Finder)**  
   - Use “Alert Owner” option  
   - Provide found item details and contact info  
   - Owner is notified and can reach out to coordinate retrieval

4. **Monitoring Lost Item (Owner / Campus Security)**  
   - View “My Lost Items”  
   - Track alerts from finders  
   - Mark items as returned when recovered  [oai_citation:10‡ALFapp-2.pdf](sediment://file_00000000d50871f8a476d5e36e0daeff)  

---

## Screenshots

> screenshots from the `screenshots/` 

- Login and registration screens  
- Post or delete lost item ad  
- Finder search and results list  
- Item and owner–finder details view  

```text
/screenshots
  login.png
  signup.png
  post_ad.png
  search_items.png
  item_details.png
