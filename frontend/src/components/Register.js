import axios from "axios";
import { useHistory} from "react-router-dom";
import React, { useState } from "react";
import "../css/Register.css";
import {Alert} from "react-bootstrap";

const Register = () => {
  const history = useHistory();
  const[registrado, setRegistrado] = useState(false);
  const[noRegistrado, setNoRegistrado] = useState(false);
  const [data, setData] = useState({
    userName: "",
    email: "",
    password: "",
  });

  const handleInputChange = (event) => {
    setData({
      ...data,
      [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = (event) => {
    setNoRegistrado(false)
    setRegistrado(false)
    event.preventDefault();
    axios
      .post('http://localhost:8080/registrar', data)
      .then( () =>{
        setRegistrado(true)
        setTimeout(() => {
          history.push("/")
        }, 3000)
      })
      .catch((error) => {
        setRegistrado(false)
        setNoRegistrado(true)
      });
      
  };

  return (
    <div class="cardRegistrate">
      <form id="Registro" onSubmit={handleSubmit} class="row g-3">
      <Alert variant="danger" show={noRegistrado}>¡Ups! Ocurrió un error </Alert>
      <Alert variant="success" show={registrado}>¡Genial! Te has registrado {data.userName} </Alert>
      
        <div>
          <label for="inputCity" class="form-label">
            Nombre de Usuario
          </label>
          <input
            type="text"
            name="userName"
            value={data.username}
            placeholder="Nombre de usuario"
            onChange={handleInputChange}
            class="form-control"
            id="inputCity"
          />
        </div>
        <div>
          <label for="inputEmail4" class="form-label">
            Email
          </label>
          <input
            type="text"
            name="email"
            class="form-control"
            id="inputEmail4"
            value={data.email}
            placeholder="Correo electrónico"
            onChange={handleInputChange}
          />
        </div>
        <div>
          <label for="inputPassword4" class="form-label">
            Contraseña
          </label>
          <input
            type="password"
            name="password"
            class="form-control"
            id="inputPassword4"
            value={data.password}
            placeholder="Contraseña"
            onChange={handleInputChange}
          />
        </div>
        <div>
          <button type="submit" 
          disabled={
            !(data.email && data.password && data.userName)
          }
          class="btn btn-primary">
            Registrar
          </button>
        </div>
      </form>
    </div>
  );
};

export default Register;
