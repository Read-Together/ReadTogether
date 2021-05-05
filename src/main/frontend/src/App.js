import './App.css';
import {BrowserRouter as Router, Route, Switch, useParams} from 'react-router-dom'
import {useState} from "react";
import NavBar from "./components/NavBar"

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
        <NavBar/>
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
