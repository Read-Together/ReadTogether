import './App.css';
import {BrowserRouter as Router, Route, Switch, useParams} from 'react-router-dom'
import SearchForm from "./components/SearchForm";
import {useState} from "react";
import Register from './components/Register';
import "./css/Register.css"
import { Link } from 'react-router-dom';

const axios = require('axios').default;

function Home() {
  return null;
}

function Resultados() {
  const { termino } = useParams();
  const [resultados, setResultados] = useState([])

  axios.get(`http://localhost:8080/grupos?busqueda=${termino}`)
    .then(resultados => {
      setResultados(resultados.data)
    });
  
  return(
    <p>{resultados.map(resultado => resultado.nombre)}</p>
  )

}


function App() {
  return (
    <Router>
      <div className="App">
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <div className="container-fluid">
            <a className="navbar-brand" href="#"><h1 className="animated flash">Read Together</h1></a>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
              <ul className="navbar-nav me-auto me-2 mb-lg-0">
                <li className="nav-item">
                  <Link to= "/">
                  <a className="nav-link active" aria-current="page">Home</a>
                  </Link>
                </li>
              </ul>
              <Link to="/registrar" class="espaciado">
              <button type="button" class="btn btn-primary"> Registrarme</button>
              </Link>
                <SearchForm/>
            </div>
          </div>
        </nav>
      </div>
      <Switch>
        <Route path="/registrar" component={Register}></Route>
        <Route path="/busqueda/:termino" component={Resultados} ></Route>
        <Route path="/">

          <Home/>
        </Route>
      </Switch>
    </Router>
   
  );
}

export default App;
