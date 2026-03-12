import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

export default function BooksList() {
  const [books, setBooks] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const fileInputRef = useRef(null);

  const handleUpload = async (file) => {
    try{
      const formData = new FormData();
      formData.append("file", file);
      await api.post("/books/upload", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },});
      navigate("/books")
    }catch(err){
      setError("Error");
    }
  }
  const handleClick = () => {
    fileInputRef.current.click();
  }

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    handleUpload(file);
    console.log(file);
  };

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
        <input
        type="file"
        accept="application/pdf"
        ref={fileInputRef}
        style={{ display: "none" }}
        onChange={handleFileChange}
        />
        <button onClick={handleClick}>upload</button>
      

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
