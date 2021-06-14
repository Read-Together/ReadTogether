import { Link } from "react-router-dom";
import "../css/NavBar.css";
import { useState } from "react";
import { Button } from "react-bootstrap";

const NavBar = () => {

  const [termino, setTermino] = useState("");

  const handleInputChange = (event) => {
    setTermino(event.target.value);
  };


  const getNombreUsuario = () => {
    return sessionStorage.getItem("loggedUsername");
  };

  const cerrarSesion = () => {
    sessionStorage.removeItem("accessToken");
    sessionStorage.removeItem("loggedUsername");
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-light">
      <div className="container-fluid">
        <a className="navbar-brand" href="/home">
          <h1 className="animated flash">Read Together</h1>
        </a>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto me-2 mb-lg-0">
            <li className="nav-item">
              <a className="nav-link active" aria-current="page" href="/home">
                {getNombreUsuario()}
              </a>
            </li>
          </ul>
          <Button
            className="btn btn-danger cerrarSesion"
            onClick={() => cerrarSesion()}
            href="/home"
          >
            Cerrar sesión
          </Button>

          <input
            class="form-control me-2 inputBusqueda"
            type="search"
            placeholder="¿Qué comunidad querés buscar?"
            onChange={handleInputChange}
            value={termino}
            aria-label="Search"
          />
          <Link to={`/busqueda/${termino}`}>
            <button Class="btn btn-success botonBuscar" type="submit">
              Buscar
            </button>
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
