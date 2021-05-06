import {Component} from "react";
import {withRouter} from "react-router-dom";

import React from 'react';

class Register extends Component {

  constructor(props) {
    super(props);
    this.state = {value: ''};
    this.cambiarInput = this.cambiarInput.bind(this);
  }

  render() {

    return (
      <form Class="d-flex" onSubmit={event => {
        event.preventDefault()
        this.props.history.replace(`registro/`)
      }}>
        <button Class="btn btn-outline-success">Registrarme</button>
      </form>
    )
  }

  cambiarInput(event) {
    this.setState({value: event.target.value})
  }

}

  export default withRouter(Register);
  