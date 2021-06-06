import axios from "axios";
import {useEffect, useState} from "react";
import {useParams} from "react-router";
import NavBar from "./NavBar";
import "../css/Comunidad.css";
import {salirDelGrupo} from "../controllers/grupoController";
import {useHistory} from "react-router-dom";


const Comunidad = () => {
  const history = useHistory();
  const {id} = useParams();
  const [data, setData] = useState({});

  useEffect(() => {
    getComunidad();
  }, []);

  const getComunidad = () => {
    const header = {
      authorization: sessionStorage.getItem("accessToken"),
    };

    axios
      .get(`http://localhost:8080/grupos/${id}`, {headers: header})
      .then((response) => {
        setData(response.data);
      });
  };

  const componentesParaMiembros = (data) => {
    if (
      data.usuarios?.some(
        (usuario) => usuario === sessionStorage.getItem("loggedUsername")
      )
    ) {
      return (
        <div class="col-sm-2 botonBiblioteca">
          <button type="button" className="btn btn-info ">
            Biblioteca
          </button>
          <div style={{height: "80%"}}>
            <div className="usuariosTitulo">Usuarios</div>
            <div>
              {data.usuarios.map((usuario) => (
                <div className="usuarios">{usuario}</div>
              ))}
            </div>
          </div>
          <button className="botonUnirse btn btn-primary "
                  onClick={() => {
                    salirDelGrupo(id)
                    history.replace(`/`)
                  }}>

            Salir
          </button>
        </div>
      );
    }
  };

  return (
    <div className="App">
      <NavBar/>

      <div class="container">
        <div class="row columnas">
          {componentesParaMiembros(data)}
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
