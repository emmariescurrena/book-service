CREATE TABLE IF NOT EXISTS books (
    id VARCHAR(100) PRIMARY KEY,
    title VARCHAR(1000) NOT NULL,
    description TEXT,
    published_year INTEGER,
    cover_id INTEGER,
    average_rating DOUBLE PRECISION DEFAULT 0.0,
    rating_count INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS genres (
    name VARCHAR(1000) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS authors (
    id VARCHAR(100) PRIMARY KEY,
    name VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS books_genres (
    book_id VARCHAR NOT NULL,
    genre_name VARCHAR NOT NULL,
    PRIMARY KEY (book_id, genre_name),
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (genre_name) REFERENCES genres (name) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS books_authors (
    book_id VARCHAR NOT NULL,
    author_id VARCHAR NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE
);