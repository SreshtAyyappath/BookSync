import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

export default function BooksList() {
  const [books, setBooks] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    api
      .get("/books")
      .then((res) => {
        setBooks(res.data);
      })
      .catch(() => {
        setError("Failed to load books");
      });
  }, []);

  return (
    <div>
      <h2>Your PDFs</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {books.length === 0 && !error && <p>No PDFs uploaded</p>}

      <ul>
        {books.map((book) => (
          <li
            key={book.id}
            style={{ cursor: "pointer", marginBottom: "10px" }}
            onClick={() => navigate(`/books/${book.id}`)}
          >
            📄 <strong>{book.pdfName}</strong>
            <br />
            <small>Uploaded at: {new Date(book.uploadedAt).toLocaleString()}</small>
          </li>
        ))}
      </ul>
    </div>
  );
}
