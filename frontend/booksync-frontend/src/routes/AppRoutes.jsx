import { BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import Login from "../auth/Login";
import Register from "../auth/Register";
import BooksList from "../books/BooksList";
import BookDetails from "../books/BookDetails";


export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login"/>} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/books" element={<BooksList />} />
        <Route path="/books/:id" element={<BookDetails />} />
        {/* <Route path="/books/upload" element={<BookUpload />} /> */}
      </Routes>
    </BrowserRouter>
  );
}
