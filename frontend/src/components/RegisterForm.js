import {Component} from "react";
import {withRouter} from "react-router-dom";
import React from 'react';

class RegisterForm extends Component{

  render(){
  
    return(
  <form id= "Registro" class="row g-3">
  <div class="col-md-6">
      <label for="inputCity" class="form-label">Nombre de Usuario</label>
      <input type="text" class="form-control" id="inputCity"/>
    </div>
  <div class="col-md-6">
    <label for="inputEmail4" class="form-label">Email</label>
    <input type="email" class="form-control" id="inputEmail4"/>
  </div>
  <div class="col-md-6">
    <label for="inputPassword4" class="form-label">Contraseña</label>
    <input type="password" class="form-control" id="inputPassword4"/>
  </div>
  <div class="col-12">
    <button type="submit" class="btn btn-primary">Registrar</button>
  </div>
  </form>)
  }
  }

  export default withRouter(RegisterForm);