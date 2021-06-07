import React, { useEffect, useState } from "react";
import NavBar from "./NavBar";
import axios from "axios";
import { useParams } from "react-router";
import "../css/Biblioteca.css";
import { Link } from "react-router-dom";

const Biblioteca = () => {
  const { id } = useParams();
  const [data, setData] = useState([]);

  useEffect(() => {
    getLibros();
  }, []);

  const getLibros = () => {
    const header = {
      authorization: sessionStorage.getItem("accessToken"),
    };
    axios
      .get(`http://localhost:8080/grupos/${id}/biblioteca`, { headers: header })
      .then((response) => {
        setData(response.data);
      });
  };

  return (
    <div className="App">
      <NavBar />
      <div className="container botonCarga">
        <Link to={`/grupos/${id}/biblioteca/cargar`}>
          <button type="button" className="tamañoBoton btn btn-primary ">
            Cargar libro
          </button>
        </Link>
      </div>
      <div className="main-content">
        {data.map((libro) => (
          <div className="card cardLibro">
            <div>Nombre: {libro.nombre}</div>
            <div>Autor: {libro.autor}</div>

            <div>
              Link de descarga: <Link>{libro.link}</Link>
            </div>
          </div>
        ))}
      </div>
      <div></div>
    </div>
  );
};

export default Biblioteca;
