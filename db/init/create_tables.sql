CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    pdf_name VARCHAR(255) NOT NULL,
    file_path TEXT NOT NULL,
    total_pages INT,
    uploaded_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE progress (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    book_id INT NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    current_page INT DEFAULT 1,
    updated_at TIMESTAMP DEFAULT NOW(),
    UNIQUE (user_id, book_id)
);
