import "./App.css";
import axios from "axios";
import {
  BrowserRouter as Router,
  Link,
  Switch,
  useHistory,
  useParams,
} from "react-router-dom";
import React, { useEffect, useState } from "react";
import Register from "./components/Register";
import { FormularioCrearComunidad } from "./components/FormularioCrearComunidad";
import { Ingresar } from "./components/Ingresar";
import { PrivateRoute } from "./components/PrivateRoute";
import PublicRoute from "./components/PublicRoute";
import Home from "./components/Home";
import NavBar from "./components/NavBar";
import "./css/Resultados.css";
import Comunidad from "./components/Comunidad";
import { salirDelGrupo } from "./controllers/grupoController";
import Biblioteca from "./components/Biblioteca";
import FormularioCargarLibro from "./components/FormularioCargarLibro";

function Resultados() {
  const { termino } = useParams();
  const [resultados, setResultados] = useState([]);
  const history = useHistory();

  const unirmeAGrupo = (resultado) => {
    const header = { authorization: sessionStorage.getItem("accessToken") };
    const data = { userName: sessionStorage.getItem("loggedUsername") };
    axios
      .post(`http://localhost:8080/grupos/${resultado.id}/registrar`, data, {
        headers: header,
      })
      .then(() => {
        history.push(`/grupos/${resultado.id}`);
      })
      .catch((error) => console.log(error));
  };

  const estaEnElGrupo = (resultado) => {
    return resultado.usuarios.some(
      (usuario) => usuario === sessionStorage.getItem("loggedUsername")
    );
  };

  useEffect(() => {
    axios
      .get(`http://localhost:8080/grupos?busqueda=${termino}`)
      .then((resultados) => {
        setResultados(resultados.data);
      });
  }, [termino]);

  function cambiarSuscripción(resultado) {
    if (estaEnElGrupo(resultado)) {
      salirDelGrupo(resultado.id);
      history.push(`/`);
    } else {
      unirmeAGrupo(resultado);
    }
  }

  return (
    <div>
      <NavBar />
      <div>
        {resultados.map((resultado) => (
          <div className="card cardComunidadEncontrado">
            <div className="nombreDeComunidad d-grid gap-2 d-md-flex ">
              <Link to={`/grupos/${resultado.id}`}>
                <div>{resultado.nombre}</div>
              </Link>
              <div className="espaciadoBoton">
                {estaEnElGrupo(resultado) ?
                <button
                  type="button"
                  onClick={() => {
                    cambiarSuscripción(resultado);
                  }}
                  className="botonUnirse btn btn-danger "
                >
                  Salir

                </button>
                 :
                <button
                  type="button"
                  onClick={() => {
                    cambiarSuscripción(resultado);
                  }}
                  className="botonUnirse btn btn-primary "
                >
                  Unirse
                  </button>

                }
              </div>
            </div>
            <div className="descripcionDeComunidad">
              {resultado.descripcion}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

function App() {
  return (
    <Router>
      <Switch>
        <PublicRoute path="/registrar" component={Register} />
        <PublicRoute path="/ingresar" component={Ingresar} />
        <PrivateRoute path="/busqueda/:termino" component={Resultados} />
        <PrivateRoute
          path="/grupos/:id/biblioteca/cargar"
          component={FormularioCargarLibro}
        />
        <PrivateRoute path="/grupos/:id/biblioteca" component={Biblioteca} />
        <PrivateRoute path="/grupos/:id" component={Comunidad} />
        <PrivateRoute path="/home" component={Home} />
        <PrivateRoute
          path="/crear_comunidad"
          component={FormularioCrearComunidad}
        />
        <PrivateRoute path="/" component={Home} />
      </Switch>
    </Router>
  );
}

export default App;
