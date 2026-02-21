import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/axios";

export default function BookDetails() {
  const { id } = useParams();
  const [book, setBook] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    api
      .get(`/books/${id}`)
      .then((res) => {
        setBook(res.data);
      })
      .catch(() => {
        setError("Failed to load book details");
      });
  }, [id]);

  const openPdf = () => {
    const token = localStorage.getItem("token");

    // Open PDF in new tab with auth
    window.open(
      `http://localhost:8080/books/${id}/file?token=${token}`,
      "_blank"
    );
  };

  if (error) {
    return <p style={{ color: "red" }}>{error}</p>;
  }

  if (!book) {
    return <p>Loading book details...</p>;
  }

  return (
    <div>
      <h2>{book.pdfName}</h2>

      <p>
        <strong>Uploaded:</strong>{" "}
        {new Date(book.uploadedAt).toLocaleString()}
      </p>

      {book.totalPages && (
        <p>
          <strong>Total pages:</strong> {book.totalPages}
        </p>
      )}

      <button onClick={openPdf}>📖 Open PDF</button>
    </div>
  );
}
