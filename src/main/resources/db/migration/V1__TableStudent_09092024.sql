CREATE TABLE student(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL UNIQUE,
    course VARCHAR(150) NOT NULL UNIQUE,
    dob date not null,
    created_at date
);