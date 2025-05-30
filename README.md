# Books Backend

A backend service for managing books, built with Python and Django.

## Features

- CRUD operations for books
- RESTful API endpoints
- User authentication / authorization
- Search and filter books
- Book reviews

## Getting Started

### Prerequisites

- Java
- Maven
### Installation

```bash
git clone https://github.com/raedbaff/Readers-Paradise-backend.git
cd Readers-Paradise-backend
mvn install

```

### Running the Server

```bash
nvm exec:java
```

## API Endpoints

| Method | Endpoint         | Description          |
|--------|-----------------|----------------------|
| GET    | /api/books/     | List all books       |
| POST   | /api/books/     | Create a new book    |
| GET    | /api/books/{id}/| Retrieve a book      |
| PUT    | /api/books/{id}/| Update a book        |
| DELETE | /api/books/{id}/| Delete a book        |

## Contributing

Pull requests are welcome. For major changes, please open an issue first.

## License

[MIT](LICENSE)