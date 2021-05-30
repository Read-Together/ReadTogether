import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import NavBar from "./NavBar";
import "../css/Comunidad.css";

const Comunidad = () => {
  const { id } = useParams();
  const [data, setData] = useState({});
  const usuarios = data.usuarios;

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

  const estaEnElGrupo = (data) => {
    if (
      data.usuarios?.some(
        (usuario) =>
          usuario.userName === sessionStorage.getItem("loggedUsername")
      )
    ) {
      return (
        <div class="col-sm-2 botonBiblioteca">
          <button type="button" className="btn btn-info ">
            Biblioteca
          </button>
          <div>
            <div className="usuariosTitulo">Usuarios</div>
            <div>
              {usuarios?.map((usuario) => (
                <div className="usuarios">{usuario.userName}</div>
              ))}
            </div>
          </div>
        </div>
      );
    }
  };

  return (
    <div className="App">
      <NavBar />

      <div class="container">
        <div class="row columnas">
          {estaEnElGrupo(data)}
          <div class="col-sm-9 container">
            <div class="row">
              <div class="col-13  titulo">
                <p>{data.nombre}</p>
              </div>
              <div className="card cardDescripcion descripcion">
                <p>{data.descripcion}</p>
              </div>
              <div className="card cardComentarios"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Comunidad;
