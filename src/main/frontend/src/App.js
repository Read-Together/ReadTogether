import './App.css';
import {BrowserRouter as Router, Route, Switch, useParams} from 'react-router-dom'
import SearchForm from "./components/SearchForm";
import {useState} from "react";

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
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false"
                    aria-label="Toggle navigation">
              <span className="navbar-toggler-icon"/>
            </button>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
              <ul className="navbar-nav me-auto me-2 mb-lg-0">
                <li className="nav-item">
                  <a className="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="#">Link</a>
                </li>
                <li className="nav-item dropdown">
                  <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                     data-bs-toggle="dropdown"
                     aria-expanded="false">
                    Dropdown
                  </a>
                  <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li><a className="dropdown-item" href="#">Action</a></li>
                    <li><a className="dropdown-item" href="#">Another action</a></li>
                    <li>
                      <hr className="dropdown-divider"/>
                    </li>
                    <li><a className="dropdown-item" href="#">Something else here</a></li>
                  </ul>
                </li>
                <li className="nav-item">
                  <a className="nav-link disabled" href="#" tabIndex="-1" aria-disabled="true">Disabled</a>
                </li>
              </ul>
              <SearchForm/>
            </div>
          </div>
        </nav>
      </div>
      <Switch>
        <Route path="/busqueda/:termino" component={Resultados}>
        </Route>
        <Route path="/">
          <Home/>
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
