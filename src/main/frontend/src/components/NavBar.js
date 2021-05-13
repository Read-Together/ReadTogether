import { Link } from "react-router-dom";
import "../css/SearchForm.css";
import { useState } from "react";

const NavBar = () => {
  const [termino, setTermino] = useState("");

  const handleInputChange = (event) => {
    setTermino(event.target.value);
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container-fluid">
        <a className="navbar-brand" href="/">
          <h1 className="animated flash">Read Together</h1>
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon" />
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto me-2 mb-lg-0">
            <li className="nav-item">
              <a className="nav-link active" aria-current="page" href="/">
                Home
              </a>
            </li>
          </ul>
          <Link to="/registrar" class="espaciado">
            <button type="button" class="btn btn-primary">
              {" "}
              Registrarme
            </button>
          </Link>
          <input
            class="form-control me-2 barraBusqueda"
            type="search"
            placeholder="¿Qué comunidad querés buscar?"
            onChange={handleInputChange}
            value={termino}
            aria-label="Search"
          />
          <Link to={`/busqueda/${termino}`}>
            <button Class="btn btn-outline-success" type="submit">
              Buscar
            </button>
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
