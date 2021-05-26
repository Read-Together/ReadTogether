import "./App.css";
import { BrowserRouter as Router, Switch, useParams, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import Register from "./components/Register";
import { FormularioCrearComunidad } from "./components/FormularioCrearComunidad";
import { Ingresar } from "./components/Ingresar";
import { PrivateRoute } from "./components/PrivateRoute";
import PublicRoute from "./components/PublicRoute";
import Home from "./components/Home";
import NavBar from "./components/NavBar";
import "./css/Resultados.css";
import Comunidad from "./components/Comunidad";

const axios = require("axios").default;

function Resultados() {
  const { termino } = useParams();
  const [resultados, setResultados] = useState([]);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/grupos?busqueda=${termino}`)
      .then((resultados) => {
        setResultados(resultados.data);
      });
  }, [termino]);

  return (
    <div>
      <NavBar />
      <div>
        {resultados.map((resultado) => (
          <div className="card cardComunidadEncontrado">
            <div className="nombreDeComunidad">
              <Link to={`/busqueda/${resultado.id}`} link="none">
                <div>{resultado.nombre}</div>
              </Link>
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
        <PrivateRoute path="/comunidad/:idDelGrupo" component={Comunidad} />
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
