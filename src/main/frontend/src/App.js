import './App.css';
import {BrowserRouter as Router, Route, Switch, useParams} from 'react-router-dom'
import {useEffect, useState} from "react";
import NavBar from "./components/NavBar"
import {LeftMenu} from "./components/LeftMenu";
import Register from './components/Register';
import {FormularioCrearComunidad} from "./components/FormularioCrearComunidad";

const axios = require('axios').default;

function Home() {
  return null;
}

function Resultados() {
  const {termino} = useParams();
  const [resultados, setResultados] = useState([])

  useEffect (() => {
     axios.get(`http://localhost:8080/grupos?busqueda=${termino}`)
    .then(resultados => {
      setResultados(resultados.data)
    });
  }, [termino]);

 

  return (
    <p>{resultados.map(resultado => resultado.nombre)}</p>
  )

}


function App() {
  return (
    <Router>
      <div className="App">
        <NavBar/>
        <div className="body-container">
          <LeftMenu/>
          <div className="main-content">
            <Switch>
              <Route path="/registrar" component={Register}/>
              <Route path="/busqueda/:termino" component={Resultados}/>
              <Route path="/crear_comunidad" component={FormularioCrearComunidad}/>
              <Route path="/">
                <Home/>
              </Route>
            </Switch>  
          </div>
        </div>
      </div>
    </Router>
  );
}

export default App;
