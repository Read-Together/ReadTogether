import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import NavBar from "./NavBar";
import "../css/Comunidad.css";

const Comunidad = () => {
  const { id } = useParams();
  const [data, setData] = useState({});

  useEffect(() => {
    getComunidad();
  }, []);

  const getComunidad = () => {
    const header = {
      authorization: sessionStorage.getItem("accessToken"),
    };

    axios
      .get(`http://localhost:8080/grupos/${id}`, { headers: header })
      .then((response) => {
        setData(response.data);
      });
  };

  return (
    <div>
      <NavBar />
      <div className="card cardGrupo">
        <div className="card cardTitulo titulo">
          <p>{data.nombre}</p>
        </div>
        <div className="card cardDescripcion descripcion">
          <p>{data.descripcion}</p>
        </div>
        <div className="cardComentarios">
        <button type="button" className="btn btn-info botonBiblioteca">
            Biblioteca
          </button>
        </div>
        
      </div>
    </div>
  );
};

export default Comunidad;
