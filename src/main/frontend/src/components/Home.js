import React, { useEffect, useState } from "react";
import NavBar from "./NavBar";
import { LeftMenu } from "./LeftMenu";
import axios from "axios";
import { Link } from "react-router-dom";
import "../css/Home.css"

const Home = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    getComunidades();
  }, []);

  const getComunidades = () => {
    const header = {
      authorization: sessionStorage.getItem("accessToken"),
    };

    const usuario = sessionStorage.getItem("loggedUsername");

    axios
      .get(`http://localhost:8080/home/${usuario}`, { headers: header })
      .then((response) => {
        setData(response.data);
      });
  };

  return (
    <div className="App">
      <NavBar />
      <div className="body-container">
        <LeftMenu />

        <div className="main-content">
          <div className="bienvenida">¡Bienvenido!</div>

            <div className="subtitulo">
              <p>¡Animate a formar parte de esta gran comunidad de lectura! </p>
              <p>Tendrás la posibilidad crear comunidades y/o unirte a las ya generadas por nuestros usuarios </p>
              </div>
          <br/>
          <div className="listadoDeGrupos">Tus comunidades: </div>
          {data.map((grupo) => (
            <div className="card cardComunidadesHome">
              <Link to={`/grupos/${grupo.id}`}>
                <div className="tituloHome">{grupo.nombre}</div>
              </Link>
              <div>{grupo.descripcion}</div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Home;
